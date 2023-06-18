package tw.com.ispan.eeit48.mainfunction.service;

import java.text.SimpleDateFormat;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainfunction.model.*;
import tw.com.ispan.eeit48.mainfunction.repository.AccountsRepository;
import tw.com.ispan.eeit48.mainfunction.repository.OrderDetailsRepositrory;
import tw.com.ispan.eeit48.mainfunction.repository.OrdersRepository;
import tw.com.ispan.eeit48.mainfunction.repository.ProductRepository;
import tw.com.ispan.eeit48.mainfunction.repository.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.mainfunction.repository.View_product_order_orderdetailsRepository;

import static tw.com.ispan.eeit48.mainfunction.service.AuthService.getCurrentUserId;

@Service
@Transactional
public class OrderSellService {
    @Autowired
    private View_product_order_orderdetailsRepository view_product_order_orderdetailsRepository;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;
    @Autowired
    private OrderDetailsRepositrory orderDetailsRepositrory;
    @Autowired
    private OrderBuyService orderBuyService;
    @Autowired
    private SystemNoticeMessageService messageService;
    @Autowired
    private EmailService emailService;

    public String ShowAll() {
        int userId = getCurrentUserId();

        JSONArray jsonArray = new JSONArray();
        List<JSONObject> sellingOrdersJsonList = new ArrayList();

        // 拿到帳號所有交易狀態為2~7的售出訂單
        List<View_ProductOrder_OrderDetails_Bean> beans = view_product_order_orderdetailsRepository
                .findAllByOwneridAndOrderstatusBetween(userId, 2, 7);

        if (beans != null && !beans.isEmpty()) {
            for (View_ProductOrder_OrderDetails_Bean bean : beans) {
                if (bean != null) {
                    JSONObject beanJsonObject = bean.toJsonObject();
                    // 算出獲利(profit)及總售價(total)
                    beanJsonObject.put("total", bean.getOrderqty() * bean.getUnitsellprice());
                    beanJsonObject.put("profit", bean.getOrderqty() * (bean.getUnitsellprice() - bean.getUnitcost()));
                    // 依產品ID找可出現貨
                    beanJsonObject.put("cansell", productService.findStockOwnByProductId(bean.getProductid()));
                    // 放入orderDateSerial，用來排序由大到小
                    beanJsonObject.put("orderDateSerial", (int) Long
                            .parseLong(bean.getOrdertime().replace(":", "").replace("-", "").replace(" ", "")));
                    // 找出買家聯絡資訊
                    AccountsBean buyer = accountsRepository.findOneByAccountid(bean.getBuyerid());
                    beanJsonObject.put("contactperson", buyer.getContactperson());
                    beanJsonObject.put("address", buyer.getAddress());
                    beanJsonObject.put("companyphone", buyer.getCompanyphone());
                    beanJsonObject.put("taxid", buyer.getTaxid());

                    // 把前端無用資訊清掉
                    beanJsonObject.remove("unitcost");
                    beanJsonObject.remove("minsellqty");
                    beanJsonObject.remove("autoorderconfirmfunctionstatus");
                    beanJsonObject.remove("unitdealprice");
                    beanJsonObject.remove("productdesc");
                    beanJsonObject.remove("onshelf");
                    beanJsonObject.remove("stockqty");
                    beanJsonObject.remove("warningqty");
                    beanJsonObject.remove("ownerid");
                    beanJsonObject.remove("safeqty");
                    beanJsonObject.remove("autoorderfunction");

                    // 把所有交易紀錄放進去
                    sellingOrdersJsonList.add(beanJsonObject);
                }
            }
            // 依照orderDateSerial，由最大到最小排列
            Collections.sort(sellingOrdersJsonList, new SortComparator("orderDateSerial", "int", "desc"));
            for (int i = 0; i < sellingOrdersJsonList.size(); i++) {
                jsonArray.put(sellingOrdersJsonList.get(i));
            }
        }
        return jsonArray.toString();
    }

    public String Update(OrdersBean ordersBean) {
        int sellerId = getCurrentUserId();

        Integer orderStatus = ordersBean.getOrderstatus();
        List<Integer> allowedStatuses = Arrays.asList(3, 4, 5, 7);

        try {
            if (allowedStatuses.contains(orderStatus)) {
                Integer buyerId = ordersBean.getBuyerid();
                String orderId = ordersBean.getOrderid();
                String sellerCompanyName = accountsRepository.findCompanynameByAccountid(sellerId);
                String buyerEmail = accountsRepository.findEmailByAccountid(buyerId);

                // 通知買家用屬性，包括寄信(emailService)，以及系統內訊息提示(messageService)
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String subject = "";
                String text = sellerCompanyName + "，於".concat(sdf.format(date)) + "已將訂單編號<" + orderId + ">狀態調整為=> ";

                ordersBean.setSellerid(sellerId);
                switch (orderStatus) {
                    case 3 -> {
                        ordersBean.setAcceptordertime(date);
                        subject = "叫貨/接單成功";
                        // 當賣家接單後，系統監控賣家所有開啟自動叫貨的productId是否有達到叫貨條件，有的話就自動叫貨
                        List<View_ProductOrder_OrderDetails_Bean> viewBeans = view_product_order_orderdetailsRepository
                                .findAllByOrderidAndOwnerid(orderId, sellerId);

                        viewBeans.forEach(product -> checkAutoOrderProduct(product.getProductid(), sellerId));
                    }
                    case 4 -> {
                        ordersBean.setExporttime(date);
                        subject = "已出貨";
                    }
                    case 5 -> {
                        ordersBean.setArriveordertime(date);
                        subject = "已送達指定地址";
                    }
                    case 7 -> {
                        ordersBean.setCancelordertime(date);
                        subject = "已取消";
                    }
                }
                ordersRepository.save(ordersBean);
                emailService.sendMail(buyerEmail, subject, "賣家".concat(text) + subject);
                messageService.saveNewMessage("叫貨管理通知: 賣家".concat(text) + "\n".concat(subject), buyerId);
                messageService.saveNewMessage("叫貨管理通知: 貴司".concat(text) + "\n".concat(subject), sellerId);
                return "OK";
            } else {
                return "No relative process for given order status, please check!";
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    // 當賣家接單後，系統監控賣家所有開啟自動叫貨的productId是否有達到叫貨條件，有的話就自動叫貨
    public void checkAutoOrderProduct(int productId, int accountId) {
        List<ProductBean> autoBeans = productRepository.findAllByAutoOrderFunctionAndOwnerId("Y", accountId);
        if (autoBeans != null && !autoBeans.isEmpty()) {
            for (ProductBean autoBean : autoBeans) {
                // 有開啟自動叫貨的productId
                int autoProductId = autoBean.getProductId();
                // 警示庫存數
                int warningQty = autoBean.getWarningQty();
                // 安全庫存數
                int safeQty = autoBean.getSafeQty();
                // 可出現貨數
                int stockOwn = productService.findStockOwnByProductId(autoProductId);
                // 已叫現貨數
                int callShipping = productService.findCallshippingByProductId(autoProductId);

                // 如果監控到(可出現貨 + 已叫現貨數)<警示庫存，就向上游廠商叫貨
                if ((stockOwn + callShipping) < warningQty) {
                    // 缺貨數
                    int lackQty = safeQty - (stockOwn + callShipping);
                    // 上游資訊
                    int supplierId =
                            supplierProductForOwnerProductRepository.findOneByProductid(autoProductId).getSupplierid();
                    int supplierProductId =
                            supplierProductForOwnerProductRepository.findOneByProductid(autoProductId).getSupplierproductid();
                    int supplierProductUnitPrice =
                            productRepository.findOneByProductId(supplierProductId).getUnitSellPrice();

                    // 新增一筆新order
                    Date now = new Date();
                    OrdersBean ob = new OrdersBean();
                    String newOrderId = orderBuyService.createNewBookingOrderId(accountId);
                    ob.setOrderid(newOrderId);
                    ob.setOrderstatus(2);
                    ob.setPaymentterms("月結");
                    ob.setOrdertime(now);
                    ob.setBuyerid(accountId);
                    ob.setSellerid(supplierId);
                    ordersRepository.save(ob);

                    OrderDetailsBean odb = new OrderDetailsBean();
                    odb.setOrderid(newOrderId);
                    odb.setOrderqty(lackQty);
                    odb.setSellerproductid(supplierProductId);
                    odb.setUnitdealprice(supplierProductUnitPrice);
                    orderDetailsRepositrory.save(odb);

                    // 自動叫貨的賣家
                    AccountsBean autoSeller = accountsRepository.findOneByAccountid(supplierId);
                    String autoSellerEmail = autoSeller.getEmail();
                    String autoSellerCompanyName = autoSeller.getCompanyname();

                    // 自動叫貨的買家
                    AccountsBean autoBuyer = accountsRepository.findOneByAccountid(accountId);
                    String autoBuyerEmail = autoBuyer.getEmail();
                    String autoBuyerCompanyName = autoBuyer.getCompanyname();

                    // 寄信和儲存訊息給買家和賣家
                    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowSimpleDate = sdFormat.format(now);
                    try {
                        emailService.sendMail(autoSellerEmail, "傳送新訂單",
                                String.format("買家%s已送出訂單編號%s，請盡快通過訂單，謝謝", autoBuyerCompanyName, newOrderId));

                        emailService.sendMail(autoBuyerEmail, "傳送新訂單",
                                String.format("您%s已自動送出一筆編號:%s新訂單，訂購商品為%s，訂購數量:%d", autoBuyerCompanyName, newOrderId, "秋刀魚", lackQty));

                        messageService.saveNewMessage("接單管理通知" + autoBuyerCompanyName + "已送出訂單編號: " + newOrderId + "請盡快通過訂單" + nowSimpleDate,
                                supplierId);

                        messageService.saveNewMessage(
                                "叫貨管理通知您已向" + autoSellerCompanyName + "送出一筆訂單, 訂單編號:" + newOrderId + "訂購商品為:" + supplierProductId + "訂購數量:" + lackQty + nowSimpleDate,
                                accountId);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }
        }
    }
}
