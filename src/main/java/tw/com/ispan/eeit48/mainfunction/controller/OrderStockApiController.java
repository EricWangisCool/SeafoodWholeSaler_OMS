package tw.com.ispan.eeit48.mainfunction.controller;

import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.model.table.Product;
import tw.com.ispan.eeit48.mainfunction.service.ProductService;
import static tw.com.ispan.eeit48.common.util.CommonUtil.convertObjectToMap;
import static tw.com.ispan.eeit48.mainfunction.service.AuthService.getCurrentUserId;

@RestController
@RequestMapping(path = {"/views/orderStock"})
public class OrderStockApiController {
    @Autowired
    ProductService productService;

    @PostMapping
    public String orderStockInformation() throws Exception {
        // 回傳使用者所有商品資訊, 包括已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
        try {
            JSONArray list = new JSONArray();
            List<Product> products = productService.findProductByOwnerid(getCurrentUserId());
            for (Product productBean : products) {
                // 運用尋訪出的單個產品資訊, 找出productId, 用來找底下三個數量, 和供應商相關資訊
                int productId = productBean.getProductId();
                // 1. 被訂購量
                int OrderedQty = productService.findOrderedQtyByProductId(productId);
                // 2. 可出現貨數
                int stockOwn = productService.findStockOwnByProductId(productId);
                // 3. 已叫現貨數
                int callShipping = productService.findCallshippingByProductId(productId);
                // 4. 供應商相關資訊
                JSONObject supplierJsonObject = productService.findSupplierObjectByOwnerProductId(productId);

                // 將尋訪出的單個產品資訊轉成JSON物件後, 放入已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
                Map<String, Object> product =  convertObjectToMap(productBean);
                // 原本功能為"已定未出(noShipping)", 後來改成"被訂購量(OrderedQty)",
                // 因為前端沒有時間更改key所以維持"Noshipping"
                product.put("noShipping", OrderedQty);
                product.put("stockOwn", stockOwn);
                product.put("callShipping", callShipping);
                // 供應商相關資訊
                if (supplierJsonObject != null) {
                    product.put("supplierId", supplierJsonObject.get("supplierId"));
                    product.put("supplierCompanyName", supplierJsonObject.get("supplierCompanyName"));
                    product.put("supplierProductId", supplierJsonObject.get("supplierProductId"));
                    product.put("supplierProductName", supplierJsonObject.get("supplierProductName"));
                } else {
                    product.put("supplierId", "null");
                    product.put("supplierCompanyName", "null");
                    product.put("supplierProductId", "null");
                    product.put("supplierProductName", "null");
                }

                product.putIfAbsent("reservedQty", "null");
                product.putIfAbsent("productPic", "null");
                // JSON物件放入JSON陣列
                list.put(product);
            }
            // 回傳JSON陣列字串給前端
            return list.toString();
        } catch (Exception e){
            return "NG";
        }
    }
}
