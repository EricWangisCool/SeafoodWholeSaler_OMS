package tw.com.ispan.eeit48.mainfunction.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.model.table.OrderDetail;
import tw.com.ispan.eeit48.mainfunction.service.OrderBuyService;
import tw.com.ispan.eeit48.mainfunction.model.table.Order;
import tw.com.ispan.eeit48.mainfunction.repository.AccountsRepository;
import tw.com.ispan.eeit48.mainfunction.repository.OrderDetailsRepositrory;
import tw.com.ispan.eeit48.mainfunction.repository.OrdersRepository;
import tw.com.ispan.eeit48.mainfunction.repository.ProductRepository;
import tw.com.ispan.eeit48.mainfunction.repository.View_product_order_orderdetailsRepository;

import static tw.com.ispan.eeit48.common.util.CommonUtil.convertObjectToMap;

@RestController
@RequestMapping(path = {"/views/orderBuy"})
public class OrderBuyApiController {
    @Autowired
    private OrderBuyService orderBuyService;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private OrderDetailsRepositrory orderDetailsRepositrory;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private View_product_order_orderdetailsRepository view_product_order_orderdetailsRepository;

    // 回傳登入帳戶「所有叫貨單資訊_allBuyList」
    @PostMapping
    public String allBuyListData() throws Exception {
        try {
            JSONArray list = new JSONArray();
            // 將使用者所有訂單資訊撈出來
            List<JSONObject> bookingOrders = orderBuyService.findUserOrders();
            for (JSONObject bookingOrder : bookingOrders) {
                // 單個訂單_供應商名稱
                String supplierCompanyName = accountsRepository
                        .findCompanynameByAccountid(bookingOrder.getInt("sellerId"));
                // 單個訂單_所有商品總金額
                String bookingOrderid = bookingOrder.getString("orderId");
                int totalPriceOfOrderId = orderBuyService.findTotalAmountByOrderId(bookingOrderid);
                // 單個訂單_所有商品名稱
                List<String> productNameSpecs = view_product_order_orderdetailsRepository
                        .findProductnamespecByOrderid(bookingOrderid);
                String totalProductNameSpec = "";
                for (String productNameSpec : productNameSpecs) {
                    totalProductNameSpec += productNameSpec + "<BR>";
                }
                // 把資料放入物件
                bookingOrder.put("companyName", supplierCompanyName);
                bookingOrder.put("total", totalPriceOfOrderId);
                bookingOrder.put("productNameSpec", totalProductNameSpec);
                list.put(bookingOrder);
            }
            return list.toString();
        } catch (Exception e) {
            return "NG";
        }
    }

    // 回傳登入帳戶「單筆叫貨單細項資訊_allBuyListDetail」
    @PostMapping(path = "/childTable")
    public String allBuyListDetailData(@RequestBody Order order) throws Exception {
        JSONArray list = new JSONArray();
        // 依照orderId, 找出該訂單的所有產品資訊
        List<OrderDetail> orderDetails = orderDetailsRepositrory.findAllByOrderid(order.getOrderId());
        // 依照該訂單所有產品資訊, 尋訪單個產品資訊
        for (OrderDetail orderDetail : orderDetails) {
            // 產品名稱
             String productNameSpec = productRepository.findProductNameSpecByProductId(orderDetail.getSellerProductId());
            // 產品小計
            int orderQty = orderDetail.getOrderQty() == null ? 0 : orderDetail.getOrderQty();
            int unitDealPrice = orderDetail.getUnitDealPrice() == null ? 0 : orderDetail.getUnitDealPrice();
            int totalPricePerProduct = orderQty * unitDealPrice;

            // 轉化成JSONObject
            Map<String, Object> orderDetailMap = convertObjectToMap(orderDetail);
            orderDetailMap.put("productNameSpec", productNameSpec);
            orderDetailMap.put("detailTotal", totalPricePerProduct);
            list.put(orderDetailMap);
        }
        return list.toString();
    }

    // 修改叫貨單(後端需求資訊: orderId, orderStatus)
    @PostMapping(path = ("/update"))
    public String insertOrder(@RequestBody String body) throws Exception {
        try {
            if (body != null) {
                // 解析requestBody
                JSONObject bodyJsonObject = new JSONObject(body);

                // 找出舊ordersBean, 開始進行新資料修改
                String orderId = (String) bodyJsonObject.get("orderId");
                Order order = ordersRepository.findOneByOrderid(orderId);
                int orderStatus = (Integer) bodyJsonObject.get("orderStatus");
                // 依照orderStatus執行相應處理
                switch (orderStatus) {
                    // 買方按下[送出叫貨單], 訂單送出待買家確認
                    case 2 -> order.setOrderTime(new Date());

                    // 賣方按下[確認訂單], 訂單成立待出貨
                    case 3 -> order.setAcceptOrderTime(new Date());

                    // 賣方按下[全配及出貨], 訂單出貨中
                    // 目前報告規劃為: 賣方按下[全配及出貨]的同時前端setTimerTask,
                    // 在30秒後呼叫"/views/orderbuy/update"將訂單狀態改成5
                    case 4 -> {
                        order.setExportTime(new Date());
                        order.setDeliveryOrderId("EX" + orderId);
                        if (bodyJsonObject.has("deliveryorderremark")) {
                            order.setDeliveryOrderRemark((String) bodyJsonObject.get("deliveryorderremark"));
                        }
                    }
                    // 賣方物流送達買方, 待買方收貨入庫
                    case 5 -> order.setArriveOrderTime(new Date());

                    // 買方案下[確認入庫], 入庫和訂單同時完成
                    case 6 -> {
                        if (orderBuyService.productsInStockByOrderId(orderId)
                                && orderBuyService.productsOutStockByOrderId(orderId)) {
                            order.setCompleteOrderTime(new Date());
                            break;
                        }
                        return "NG";
                    }
                    // 買方或賣方按下[取消訂單], 訂單不成立
                    case 7 -> order.setCancelOrderTime(new Date());

                    // 買家將暫存訂單刪除
                    case 8 -> {
                        if (orderBuyService.deleteOrdersBean(order)) {
                            return "OK";
                        } else {
                            return "NG";
                        }
                    }
                }
                // 如果訂單狀態為2~7, 就將newOrdersBean資料修改進DB
                if (orderStatus > 1 && orderStatus < 8) {
                    order.setOrderStatus(orderStatus);
                    if (orderBuyService.updateOrdersBean(order) != null) {
                        return "OK";
                    } else {
                        return "NG";
                    }
                } else {
                    return "NG";
                }
            } else {
                return "NG";
            }
        } catch (Exception e) {
            return "NG";
        }
    }
}
