package tw.com.ispan.eeit48.controller;

import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.domain.SupplierProductForOwnerProductBean;
import tw.com.ispan.eeit48.service.StockService;

@RestController
@RequestMapping(path = { "/views/addstock" })
public class StockAPIController {
	@Autowired
	StockService stockService;

	@PostMapping(path = { "/insert" })
	public String insertNewProduct(@RequestBody String body, HttpSession session) {
		// 解析Request & Session
		AccountsBean user = (AccountsBean) session.getAttribute("user");
		Integer userAccountId = user.getAccountid();
		JSONObject bodyObj = new JSONObject(body);
		String result = "NG";

		if (userAccountId != null) {
			// 設定庫存資訊
			ProductBean productBean = new ProductBean();
			productBean.setOwnerid(userAccountId);
			productBean = stockService.setProductBean(bodyObj, productBean);

			// 設定庫存補貨廠商
			SupplierProductForOwnerProductBean supplierProductForOwnerProductBean = new SupplierProductForOwnerProductBean();
			supplierProductForOwnerProductBean.setSupplierid((Integer) bodyObj.get("supplierid"));
			supplierProductForOwnerProductBean.setSupplierproductid((Integer) bodyObj.get("supplierproductid"));

			// 將庫存補貨廠商 & 庫存補貨廠商進行保存
			result = stockService.insertNewStock(userAccountId, productBean, supplierProductForOwnerProductBean);
		}
		return result;
	}

	@PostMapping(path = { "/update" })
	public String updateProduct(@RequestBody String body, HttpSession session) {
		// 解析Request & Session
		AccountsBean user = (AccountsBean) session.getAttribute("user");
		JSONObject bodyObj = new JSONObject(body);
		Integer userAccountId = user.getAccountid();
		String result = "NG";

		if (userAccountId != null) {
			result = stockService.updateStock(bodyObj);
		}
		return result;
	}

	@PostMapping(path = { "/delete" })
	public String deleteProduct(@RequestBody ProductBean productBean, HttpSession session) {
		// 解析Request & Session
		AccountsBean user = (AccountsBean) session.getAttribute("user");
		Integer userAccountId = user.getAccountid(), productId = productBean.getProductid();
		String result = "NG";
		if (userAccountId != null && productId != null) {
			result = stockService.deleteStock(productId);
		}
		return result;
	}
}
