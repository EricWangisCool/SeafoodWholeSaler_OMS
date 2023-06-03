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
import tw.com.ispan.eeit48.mainfunction.model.OrderDetailsBean;
import tw.com.ispan.eeit48.mainfunction.model.ProductBean;
import tw.com.ispan.eeit48.mainfunction.service.OrderBuyService;
import tw.com.ispan.eeit48.mainfunction.service.ProductService;
import tw.com.ispan.eeit48.mainfunction.model.AccountsBean;
import tw.com.ispan.eeit48.mainfunction.model.OrdersBean;
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
    public String returnProductDataBySupplierId(@RequestBody AccountsBean accountsBean) {
        int supplierId = accountsBean.getAccountid();
        List<ProductBean> productBeans = productService.findProductByOwnerid(supplierId);
        if (supplierId > 0 && productBeans != null) {
            JSONArray list = new JSONArray();
            // 尋訪出供應商單筆商品資訊
            for (ProductBean productBean : productBeans) {
                // 從單筆供應商productId, 查看是否為自己productId的補貨對象, 是的話回傳缺貨數(outstock), 不是的話回傳0
                Integer supplierProductId = productBean.getProductId();
                int owneroutstock = 0;
                if (supplierProductId != null) {
                    // 從供應商單一producitId, 找到對應自己productIds(可多個產品), 然後將它們的缺貨數加總(outstock)
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
                product.put("owneroutstock", owneroutstock);
                product.putIfAbsent("reservedqty", "null");
                product.putIfAbsent("productpic", "null");
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
            OrdersBean newOrdersBean = new OrdersBean();
            List<OrderDetailsBean> newOrderDetailsBeans = new ArrayList<OrderDetailsBean>();
            String NewBookingOrderId = orderBuyService.createNewBookingOrderId(userId);

            newOrdersBean.setOrderid(NewBookingOrderId);
            newOrdersBean.setBuyerid(userId);
            newOrdersBean.setSellerid((Integer) bodyJsonObject.get("sellerid"));
            newOrdersBean.setOrderstatus(1);
            newOrdersBean.setPaymentterms("銀行轉帳<br>月結(TT30)");

            JSONArray products = new JSONArray(bodyJsonObject.getJSONArray("products"));
            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                OrderDetailsBean bean = new OrderDetailsBean();
                bean.setOrderid(NewBookingOrderId);
                bean.setSellerproductid((Integer) product.get("sellerproductid"));
                bean.setOrderqty((Integer) product.get("orderqty"));
                bean.setUnitdealprice((Integer) product.get("unitdealprice"));
                newOrderDetailsBeans.add(bean);
            }

            if (orderBuyService.saveNewOrdersBean(newOrdersBean) != null) {
                if (orderBuyService.saveNewOrderDetailsBean(newOrderDetailsBeans)) {
                    // 如果orders && orderdetails都成功叫貨的話, 回傳OK
                    return "OK";
                }
            } else {
                return "NG";
            }
        }
        return "NG";
    }
}
