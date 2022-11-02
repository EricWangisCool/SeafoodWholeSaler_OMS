package tw.com.ispan.eeit48.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.OrderDetailsBean;
import tw.com.ispan.eeit48.domain.OrdersBean;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.domain.View_companyfollowinglist_accountsBean;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.repository.OrderDetailsRepositrory;
import tw.com.ispan.eeit48.repository.ProductRepository;
import tw.com.ispan.eeit48.repository.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.service.AccountsService;
import tw.com.ispan.eeit48.service.OrdersService;
import tw.com.ispan.eeit48.service.ProductService;

@RestController
@RequestMapping(path = { "/views/goods" })
public class GoodsApiController {
	// GoodsApiController用的屬性
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;

	// 為了建構OrderBuyApiController才Autowired的屬性
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private OrderDetailsRepositrory orderDetailsRepositrory;
	@Autowired
	private ProductRepository productRepository;

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
				Integer supplierProductId = productBean.getProductid();
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
				JSONObject productBeanJsonObject = productBean.toJsonObject();
				productBeanJsonObject.put("owneroutstock", owneroutstock);
				list.put(productBeanJsonObject);
			}
			return list.toString();
		}
		return null;
	}

	// 買方按下[新增商品]建立叫貨單, 訂單暫存未送出(orderStatus = 1)
	@PostMapping(path = ("/insert"))
	public String insertOrder(HttpSession session, @RequestBody String body) throws Exception {
		AccountsBean user = (AccountsBean) session.getAttribute("user");
		int userId = user.getAccountid();
		// 如果使用者存在就開始新增作業
		if (userId > 0 && body != null) {
			JSONObject bodyJsonObject = new JSONObject(body);
			OrdersBean newOrdersBean = new OrdersBean();
			List<OrderDetailsBean> newOrderDetailsBeans = new ArrayList<OrderDetailsBean>();
			String NewBookingOrderId = ordersService.createNewBookingOrderId(userId);

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

			if (ordersService.saveNewOrdersBean(newOrdersBean) != null) {
				if (ordersService.saveNewOrderDetailsBean(newOrderDetailsBeans) == true) {
					// 如果orders && orderdetails都成功叫貨的話, 回傳OK
					return "OK";
				}
			} else {
				return "NG";
			}
		}
		return "UserId not found in session";
	}

	// Deprecated - 回傳登入帳戶所有「供應廠商資訊」=> 此功能由阿封那裡的/views/cooperate替代
	// @PostMapping
	public String user_SuppplierInformation(HttpSession session) throws Exception {
		// 從session裡撈出使用者id
		if (session.getAttribute("user") != null) {
			AccountsBean user = (AccountsBean) session.getAttribute("user");
			int userId = user.getAccountid();
			// 如果使用者存在, 就製作JSON陣列等等裝他的產品資料
			if (userId > 0) {
				JSONArray list = new JSONArray();
				List<View_companyfollowinglist_accountsBean> suppliersData = accountsService
						.findSupplierDataByUserId(userId);
				for (View_companyfollowinglist_accountsBean supplier : suppliersData) {
					JSONObject supplierJsonObject = supplier.toJsonObject();
					list.put(supplierJsonObject);
				}
				System.out.println(list.toString());
				return list.toString();
			} else {
				System.out.println("UserId is null");
			}
			return null;
		}
		return null;
	}
}
