package tw.com.ispan.eeit48.mainfunction.controller;

import java.util.ArrayList;
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
import tw.com.ispan.eeit48.mainfunction.model.table.Product;
import tw.com.ispan.eeit48.mainfunction.service.OrderBuyService;
import tw.com.ispan.eeit48.mainfunction.service.ProductService;
import tw.com.ispan.eeit48.mainfunction.model.table.Account;
import tw.com.ispan.eeit48.mainfunction.model.table.Order;
import tw.com.ispan.eeit48.mainfunction.repository.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.mainfunction.service.View_companyfollowinglist_accountsService;
import static tw.com.ispan.eeit48.common.util.CommonUtil.convertObjectToMap;
import static tw.com.ispan.eeit48.mainfunction.service.AuthService.getCurrentUserId;

@RestController
@RequestMapping(path = {"/views/goods"})
public class GoodsApiController {
    @Autowired
    private OrderBuyService orderBuyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;
    @Autowired
    private View_companyfollowinglist_accountsService view_companyfollowinglist_accountsService;

    // 前端發送供應商ID, 後端回傳該供應商的產品資訊
    @PostMapping
    public String returnProductDataBySupplierId(@RequestBody Account account) {
        int supplierId = account.getAccountId();
        List<Product> products = productService.findProductByOwnerid(supplierId);
        if (supplierId > 0 && products != null) {
            JSONArray list = new JSONArray();
            // 尋訪出供應商單筆商品資訊
            for (Product productBean : products) {
                // 從單筆供應商productId, 查看是否為自己productId的補貨對象, 是的話回傳缺貨數(outStock), 不是的話回傳0
                Integer supplierProductId = productBean.getProductId();
                int owneroutstock = 0;
                if (supplierProductId != null) {
                    // 從供應商單一productId, 找到對應自己productIds(可多個產品), 然後將它們的缺貨數加總(outStock)
                    List<Integer> ownerProductIds = supplierProductForOwnerProductRepository
                            .findProductIdBySupplierProductId(supplierProductId);
                    if (!ownerProductIds.isEmpty()) {
                        for (Integer ownerProductId : ownerProductIds) {
                            if (ownerProductId > 0) {
                                owneroutstock += productService.findOutStockByProductId(ownerProductId);
                            }
                        }
                    }
                }
                Map<String, Object> product =  convertObjectToMap(productBean);
                product.put("ownerOutStock", owneroutstock);
                product.putIfAbsent("reservedQty", "null");
                product.putIfAbsent("productPic", "null");
                list.put(product);
            }
            return list.toString();
        }
        return null;
    }

    // 買方按下[新增商品]建立叫貨單, 訂單暫存未送出(orderStatus = 1)
    @PostMapping(path = ("/insert"))
    public String insertOrder(@RequestBody String body) {
        int userId = getCurrentUserId();

        if (body != null) {
            JSONObject bodyJsonObject = new JSONObject(body);
            Order newOrder = new Order();
            List<OrderDetail> newOrderDetails = new ArrayList<OrderDetail>();
            String NewBookingOrderId = orderBuyService.createNewBookingOrderId(userId);

            newOrder.setOrderId(NewBookingOrderId);
            newOrder.setBuyerId(userId);
            newOrder.setSellerId((Integer) bodyJsonObject.get("sellerId"));
            newOrder.setOrderStatus(1);
            newOrder.setPaymentTerms("銀行轉帳<br>月結(TT30)");

            JSONArray products = new JSONArray(bodyJsonObject.getJSONArray("product"));
            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                OrderDetail bean = new OrderDetail();
                bean.setOrderId(NewBookingOrderId);
                bean.setSellerProductId((Integer) product.get("sellerProductId"));
                bean.setOrderQty((Integer) product.get("orderQty"));
                bean.setUnitDealPrice((Integer) product.get("unitDealPrice"));
                newOrderDetails.add(bean);
            }

            if (orderBuyService.saveNewOrdersBean(newOrder) != null) {
                if (orderBuyService.saveNewOrderDetailsBean(newOrderDetails)) {
                    // 如果order && orderDetail都成功叫貨的話, 回傳OK
                    return "OK";
                }
            } else {
                return "NG";
            }
        }
        return "NG";
    }
}
