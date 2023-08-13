package tw.com.ispan.eeit48.mainFunction.service;

import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainFunction.model.table.Account;
import tw.com.ispan.eeit48.mainFunction.model.table.OrderDetail;
import tw.com.ispan.eeit48.mainFunction.model.table.Order;
import tw.com.ispan.eeit48.mainFunction.model.table.Product;
import tw.com.ispan.eeit48.mainFunction.model.view.Product_Order_OrderDetail;
import tw.com.ispan.eeit48.mainFunction.repository.table.AccountRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.OrderDetailRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.OrderRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.ProductRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.mainFunction.repository.view.Product_Order_OrderDetailRepository;
import static tw.com.ispan.eeit48.common.util.CommonUtil.convertObjectToMap;
import static tw.com.ispan.eeit48.mainFunction.service.AuthService.getCurrentUserId;

@Service
@Transactional
public class OrderSellService {
    @Autowired
    private Product_Order_OrderDetailRepository productOrderOrderDetailRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private NewsService messageService;
    @Autowired
    private EmailService emailService;

    // 拿到帳號所有交易狀態為2~7的售出訂單
    public List<Map<String, Object>> getUserSellingOrdersInfo() {
        List<Product_Order_OrderDetail> ordersInfo = productOrderOrderDetailRepository
                .findAllByOwnerIdAndOrderStatusBetweenOrderByCreateTimeDesc(getCurrentUserId(), 2, 7);

        List<Map<String, Object>> list = new ArrayList<>();
        if (ordersInfo != null && !ordersInfo.isEmpty()) {
            for (Product_Order_OrderDetail orderInfo : ordersInfo) {
                if (orderInfo != null) {
                    Map<String, Object> orderInfoMap = convertObjectToMap(orderInfo);
                    orderInfoMap.put("total", orderInfo.getOrderQty() * orderInfo.getUnitSellPrice()); // 總售價
                    orderInfoMap.put("profit", orderInfo.getOrderQty() * (orderInfo.getUnitSellPrice() - orderInfo.getUnitCost()));
                    orderInfoMap.put("sellableQty", productService.findSellableQtyByProductId(orderInfo.getProductId()));

                    Optional<Account> buyerOptional = accountRepository.findOneByAccountId(orderInfo.getBuyerId());
                    Account buyer = buyerOptional.orElseThrow(() -> new RuntimeException("Buyer account not found in system"));

                    orderInfoMap.put("contactPerson", buyer.getContactPerson());
                    orderInfoMap.put("address", buyer.getAddress());
                    orderInfoMap.put("companyPhone", buyer.getCompanyPhone());
                    orderInfoMap.put("taxId", buyer.getTaxId());

                    // 把前端無用資訊清掉
                    orderInfoMap.remove("unitCost");
                    orderInfoMap.remove("minSellQty");
                    orderInfoMap.remove("autoOrderConfirmFunctionStatus");
                    orderInfoMap.remove("unitDealPrice");
                    orderInfoMap.remove("productDesc");
                    orderInfoMap.remove("onShelf");
                    orderInfoMap.remove("stockQty");
                    orderInfoMap.remove("warningQty");
                    orderInfoMap.remove("ownerId");
                    orderInfoMap.remove("safeQty");
                    orderInfoMap.remove("autoOrderFunction");
                    list.add(orderInfoMap);
                }
            }
        }
        return list;
    }

    public void update(Order order) throws Exception {
        int userId = getCurrentUserId();
        Integer orderStatus = order.getOrderStatus();
        List<Integer> allowedStatus = Arrays.asList(3, 4, 5, 7);

        if (allowedStatus.contains(orderStatus)) {
            Integer buyerId = order.getBuyerId();
            String orderId = order.getOrderId();
            String userCompanyName = accountRepository.findCompanyNameByAccountId(userId);
            String buyerEmail = accountRepository.findOneByAccountId(buyerId).get().getEmail();

            // For email
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String subject = "";
            String text = userCompanyName + "，於".concat(sdf.format(date)) + "已將訂單編號<" + orderId + ">狀態調整為=> ";

            order.setSellerId(userId);
            switch (orderStatus) {
                case 3 -> {
                    order.setAcceptOrderTime(date);
                    subject = "叫貨/接單成功";
                    // 當賣家接單後，系統監控賣家所有開啟自動叫貨的productId是否有達到叫貨條件，有的話就自動叫貨
                    List<Product_Order_OrderDetail> sellerOrdersInfo = productOrderOrderDetailRepository
                            .findAllByOrderIdAndOwnerId(orderId, userId);

                    sellerOrdersInfo.forEach(sellerOrderInfo -> checkAutoOrderProduct(sellerOrderInfo.getProductId(), userId));
                }
                case 4 -> {
                    order.setExportTime(date);
                    subject = "已出貨";
                }
                case 5 -> {
                    order.setArriveOrderTime(date);
                    subject = "已送達指定地址";
                }
                case 7 -> {
                    order.setCancelOrderTime(date);
                    subject = "已取消";
                }
            }
            orderRepository.save(order);
            emailService.sendMail(buyerEmail, subject, "賣家".concat(text) + subject);
            messageService.saveNewMessage("叫貨管理通知: 賣家".concat(text) + "\n".concat(subject), buyerId);
            messageService.saveNewMessage("叫貨管理通知: 貴司".concat(text) + "\n".concat(subject), userId);
        } else {
            throw new Exception("No relative process for given order status, please check!");
        }
    }

    // 當賣家接單後，系統監控賣家所有開啟自動叫貨的productId是否有達到叫貨條件((可出現貨 + 已叫現貨數) < 警示庫存)，有的話就自動叫貨
    public void checkAutoOrderProduct(String sellerProductId, int accountId) {
        Optional<Product> product = productRepository.findOneByProductIdAndAutoOrderFunction(sellerProductId, "Y");
        if (product.isPresent()) {
            Product autoBuyProduct = product.get();
            String autoProductId = autoBuyProduct.getProductId();
            int warningQty = autoBuyProduct.getWarningQty();
            int safeQty = autoBuyProduct.getSafeQty();
            int sellableQty = productService.findSellableQtyByProductId(autoProductId);
            int requestedQty = productService.findRequestedQtyByProductId(autoProductId);

            if ((sellableQty + requestedQty) < warningQty) {
                int lackQty = safeQty - (sellableQty + requestedQty);
                Integer supplierId =
                        supplierProductForOwnerProductRepository.findOneByProductId(autoProductId).getSupplierId();
                if (supplierId == null) return;

                String supplierProductId =
                        supplierProductForOwnerProductRepository.findOneByProductId(autoProductId).getSupplierProductId();
                int supplierProductUnitPrice =
                        productRepository.findOneByProductId(supplierProductId).getUnitSellPrice();

                // 新增一筆新order
                Date today = new Date();
                Order order = new Order();
                String newOrderId = createNewOrderId(accountId);
                order.setOrderId(newOrderId);
                order.setOrderStatus(2);
                order.setPaymentTerms("月結");
                order.setCreateTime(today);
                order.setOrderTime(today);
                order.setBuyerId(accountId);
                order.setSellerId(supplierId);
                orderRepository.save(order);

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(newOrderId);
                orderDetail.setOrderQty(lackQty);
                orderDetail.setSellerProductId(supplierProductId);
                orderDetail.setUnitDealPrice(supplierProductUnitPrice);
                orderDetailRepository.save(orderDetail);

                // 自動叫貨的賣家
                Account autoSeller = accountRepository.findOneByAccountId(supplierId).get();
                String autoSellerEmail = autoSeller.getEmail();
                String autoSellerCompanyName = autoSeller.getCompanyName();

                // 自動叫貨的買家
                Account autoBuyer = accountRepository.findOneByAccountId(accountId).get();
                String autoBuyerEmail = autoBuyer.getEmail();
                String autoBuyerCompanyName = autoBuyer.getCompanyName();

                // 寄信和儲存訊息給買家和賣家
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowSimpleDate = sdFormat.format(today);
                emailService.sendMail(autoSellerEmail, "傳送新訂單",
                        String.format("買家%s已送出訂單編號%s，請盡快通過訂單，謝謝", autoBuyerCompanyName, newOrderId));

                emailService.sendMail(autoBuyerEmail, "傳送新訂單",
                        String.format("您%s已自動送出一筆編號:%s新訂單，訂購商品為%s，訂購數量:%d", autoBuyerCompanyName, newOrderId, "秋刀魚", lackQty));

                messageService.saveNewMessage("接單管理通知" + autoBuyerCompanyName + "已送出訂單編號: " + newOrderId + "請盡快通過訂單" + nowSimpleDate,
                        supplierId);

                messageService.saveNewMessage(
                        "叫貨管理通知您已向" + autoSellerCompanyName + "送出一筆訂單, 訂單編號:" + newOrderId + "訂購商品為:" + supplierProductId + "訂購數量:" + lackQty + nowSimpleDate,
                        accountId);
            }
        }
    }

    // 製作新訂單編號, 編號邏輯是: 使用者ID + "D" + 今天日期 + 四位數流水號
    public String createNewOrderId(int userId) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);

        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);

        Optional<String> todayLastOrderId =
                orderRepository.findLastOrderIdByBuyerIdAndOrderTimeBetween(userId, todayStart.getTime(), todayEnd.getTime());

        int serialNumber;
        if (todayLastOrderId.isPresent()) {
            String orderId = todayLastOrderId.get();
            int firstSerialNumberIndex = orderId.indexOf("D") + 9;
            int lastSerialNumberIndex = firstSerialNumberIndex + 4;
            serialNumber = Integer.parseInt(
                    orderId.substring(firstSerialNumberIndex, lastSerialNumberIndex)
            ) + 1;
        } else {
            serialNumber = 1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String simpleToday = sdf.format(new Date());
        return String.format("%dD%s%04d", userId, simpleToday, serialNumber);
    }
}
