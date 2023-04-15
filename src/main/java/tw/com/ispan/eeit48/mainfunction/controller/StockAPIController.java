package tw.com.ispan.eeit48.mainfunction.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.model.ProductBean;
import tw.com.ispan.eeit48.mainfunction.service.StockService;
import tw.com.ispan.eeit48.mainfunction.model.SupplierProductForOwnerProductBean;
import tw.com.ispan.eeit48.mainfunction.service.AuthService;

@RestController
@RequestMapping(path = {"/views/addstock"})
public class StockAPIController {
    @Autowired
    StockService stockService;
    @Autowired
    AuthService authService;
    int userId;

    @PostMapping(path = {"/insert"})
    public String insertNewProduct(@RequestBody String body) {
        userId = authService.getCurrentUserId();
        JSONObject bodyObj = new JSONObject(body);
        String result = "NG";

        if (userId > 0) {
            // 設定庫存資訊
            ProductBean productBean = new ProductBean();
            productBean.setOwnerid(userId);
            productBean = stockService.setProductBean(bodyObj, productBean);

            // 設定庫存補貨廠商
            SupplierProductForOwnerProductBean supplierProductForOwnerProductBean = new SupplierProductForOwnerProductBean();
            supplierProductForOwnerProductBean.setSupplierid((Integer) bodyObj.get("supplierid"));
            supplierProductForOwnerProductBean.setSupplierproductid((Integer) bodyObj.get("supplierproductid"));

            // 將庫存補貨廠商 & 庫存補貨廠商進行保存
            result = stockService.insertNewStock(userId, productBean, supplierProductForOwnerProductBean);
        }
        return result;
    }

    @PostMapping(path = {"/update"})
    public String updateProduct(@RequestBody String body) {
        JSONObject bodyObj = new JSONObject(body);
        return stockService.updateStock(bodyObj);
    }

    @PostMapping(path = {"/delete"})
    public String deleteProduct(@RequestBody ProductBean productBean) {
        int productId = productBean.getProductid();
        String result = "NG";
        if (productId > 0) {
            result = stockService.deleteStock(productId);
        }
        return result;
    }
}
