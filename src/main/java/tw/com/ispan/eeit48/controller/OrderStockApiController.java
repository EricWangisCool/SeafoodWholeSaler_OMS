package tw.com.ispan.eeit48.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.service.ProductService;

@RestController
@RequestMapping(path = { "/views/orderstock" })
public class OrderStockApiController {
	@Autowired
	ProductService productService;

	// 回傳使用者所有商品資訊, 包括已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
	@PostMapping
	public String orderstockInformation(HttpSession session) throws Exception {
		// 從session裡撈出使用者id
		if (session.getAttribute("user") != null) {
			AccountsBean user = (AccountsBean) session.getAttribute("user");
			int userId = user.getAccountid();

			// 如果使用者存在, 就製作JSON陣列等等裝他的產品資料
			if (userId > 0) {
				JSONArray list = new JSONArray();

				// 依照userId, 找出他所有產品資訊, 並將單個產品資訊尋訪出來
				List<ProductBean> productBeans = productService.findProductByOwnerid(userId);
				for (ProductBean productBean : productBeans) {
					// 運用尋訪出的單個產品資訊, 找出productId, 用來找底下三個數量, 和供應商相關資訊
					int productId = productBean.getProductid();
					// 1. 被訂購量
					int OrderedQty = productService.findOrderedQtyByProductId(productId);
					// 2. 可出現貨數
					int stockown = productService.findStockOwnByProductId(productId);
					// 3. 已叫現貨數
					int callshipping = productService.findCallshippingByProductId(productId);
					// 4. 供應商相關資訊
					JSONObject supplierJsonObject = productService.findSupplierObjectByOwnerProductId(productId);

					// 將尋訪出的單個產品資訊轉成JSON物件後, 放入已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
					JSONObject jsonProductBean = productBean.toJsonObject();
					// 原本功能為"已定未出(noshipping)", 後來改成"被訂購量(OrderedQty)",
					// 因為前端沒有時間更改key所以維持"Noshipping"
					jsonProductBean.put("noshipping", OrderedQty);
					jsonProductBean.put("stockown", stockown);
					jsonProductBean.put("callshipping", callshipping);
					// 供應商相關資訊
					if (supplierJsonObject != null) {
						jsonProductBean.put("supplierid", supplierJsonObject.get("supplierid"));
						jsonProductBean.put("suppliercompanyname", supplierJsonObject.get("suppliercompanyname"));
						jsonProductBean.put("supplierproductid", supplierJsonObject.get("supplierproductid"));
						jsonProductBean.put("supplierproductname", supplierJsonObject.get("supplierproductname"));
					} else {
						jsonProductBean.put("supplierid", "null");
						jsonProductBean.put("suppliercompanyname", "null");
						jsonProductBean.put("supplierproductid", "null");
						jsonProductBean.put("supplierproductname", "null");
					}
					// JSON物件放入JSON陣列
					list.put(jsonProductBean);
				}
				// 回傳JSON陣列字串給前端
				return list.toString();
			} else {
				System.out.println("no product for this User");
				return "NG";
			}
		} else {
			System.out.println("UserId is null");
			return "NG";
		}
	}
}
