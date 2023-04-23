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
import static tw.com.ispan.eeit48.mainfunction.service.AuthService.getCurrentUserId;

@RestController
@RequestMapping(path = {"/views/addstock"})
public class StockAPIController {
    @Autowired
    StockService stockService;

    @PostMapping(path = {"/insert"})
    public String insertNewProduct(@RequestBody String body) {
        try {
            int userId = getCurrentUserId();
            JSONObject bodyObj = new JSONObject(body);

            // 設定庫存資訊
            ProductBean bean = new ProductBean();
            bean.setOwnerid(userId);

            // 設定庫存補貨廠商
            SupplierProductForOwnerProductBean supplierProductForOwnerProductBean = new SupplierProductForOwnerProductBean();
            supplierProductForOwnerProductBean.setSupplierid((Integer) bodyObj.get("supplierid"));
            supplierProductForOwnerProductBean.setSupplierproductid((Integer) bodyObj.get("supplierproductid"));

            // 將庫存補貨廠商 & 庫存補貨廠商進行保存
            return stockService.insertNewStock(userId, stockService.setProductBeanFromJson(bean, bodyObj), supplierProductForOwnerProductBean);
        } catch (Exception e) {
            return "NG";
        }
    }

    @PostMapping(path = {"/update"})
    public String updateProduct(@RequestBody String body) {
        return stockService.updateStock(new JSONObject(body));
    }

    @PostMapping(path = {"/delete"})
    public String deleteProduct(@RequestBody ProductBean productBean) {
        int productId = productBean.getProductid();
        if (productId > 0) {
            stockService.deleteStock(productId);
            return "OK";
        }
        return "NG";
    }
}
