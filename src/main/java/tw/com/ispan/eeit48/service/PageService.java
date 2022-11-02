package tw.com.ispan.eeit48.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.domain.OrdersBean;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.domain.SortComparator;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.repository.OrdersRepository;
import tw.com.ispan.eeit48.repository.View_product_order_orderdetailsRepository;

@Service
@Transactional
public class PageService {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private View_product_order_orderdetailsRepository view_product_order_orderdetailsRepository;

	// 回傳緊急庫存清單, 判斷條件:
	// [("可出現貨" + "已叫現貨") < "警戒庫存"]  &&  依照缺貨數["安全庫存" - ("可出現貨" + "已叫現貨")]由大到小排列
	public JSONArray returnPageList(int userId) {
		JSONArray list = new JSONArray();
		List<JSONObject> urgentOwnerProducts = new ArrayList<>();
		List<ProductBean> ownerProducts = productService.findProductByOwnerid(userId);
		if (ownerProducts != null) {
			for (ProductBean ownerProduct : ownerProducts) {
				int ownerProductId = ownerProduct.getProductid();
				// 可出現貨數(stockown)
				int stockown = productService.findStockOwnByProductId(ownerProductId);
				// 已叫現貨數(callshipping)
				int callshipping = productService.findCallshippingByProductId(ownerProductId);
				// "可出現貨 + 已叫現貨" (stockownandcallshipping)
				int stockownandcallshipping = stockown + callshipping;
				// 警戒庫存數(warningQty)
				int warningQty = ownerProduct.getWarningqty();

				// 如果 ( "可出現貨" + "已叫現貨") < "警戒庫存", 就放入緊急庫存清單
				if (stockownandcallshipping < warningQty) {
					// "缺貨數"(outstock) = "安全庫存數"（safeQty) -  ("可出現貨" + "已叫現貨")(stockownandcallshipping)
					int safeQty = ownerProduct.getSafeqty();
					int outstock = safeQty - stockownandcallshipping;

					// 前端頁面需要值
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("productnamespec", ownerProduct.getProductnamespec());
					jsonObject.put("outstock", outstock);
					jsonObject.put("safeqty", safeQty);
					jsonObject.put("stockownandcallshipping", stockownandcallshipping);
					jsonObject.put("warningqty", warningQty);
					// 保留值, 如果前端需要再開啟
//					jsonObject.put("stockown", stockown);
//					jsonObject.put("callshipping", callshipping);
//					jsonObject.put("productid", ownerProductId);
					urgentOwnerProducts.add(jsonObject);
				}
			}
		}
		// 緊急庫存清單依照"缺貨數"由大到小排列
		Collections.sort(urgentOwnerProducts, new SortComparator("outstock", "int", "desc"));
		for (int i = 0; i < urgentOwnerProducts.size(); i++) {
			list.put(urgentOwnerProducts.get(i));
		}
		return list;
	}

	// 回傳緊急購買清單 [延遲時間 (現在時間 - Status2~5的各個購買清單的最新狀態時間) > 3hrs] && 延遲時間由大排到小
	public JSONArray returnPageBuyList(int userId) throws Exception {
		// 最外層JsonArray
		JSONArray list = new JSONArray();
		// 日期用物件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long now = Instant.now().toEpochMilli();
		Long threeHoursMillis = (long) 10800000;
		Long statusTimeLong, statusDelay;
		TimeUnit time = TimeUnit.HOURS;

		List<JSONObject> urgentBuyingOrders = new ArrayList<>();
		List<OrdersBean> buyingOrders = ordersRepository.findAllByBuyerid(userId);
		if (buyingOrders != null) {
			for (OrdersBean order : buyingOrders) {
				String orderId = order.getOrderid();
				// 找出單筆訂單所有產品名稱
				List<String> productNameSpecs = view_product_order_orderdetailsRepository
						.findProductnamespecByOrderid(orderId);
				String totalProductNameSpec = "";
				for (String productNameSpec : productNameSpecs) {
					totalProductNameSpec += productNameSpec + "<BR>";
				}
				// 將值放入物件
				JSONObject orderObj = new JSONObject();
				orderObj.put("productnamespec", totalProductNameSpec);
				orderObj.put("orderid", orderId);
				orderObj.put("companyname", accountsRepository.findCompanynameByAccountid(order.getSellerid()));
				orderObj.put("orderstatus", order.getOrderstatus());
				// 依照訂單狀態, 找出相應狀態時間
				switch (order.getOrderstatus()) {
				// 買方按下[送出叫貨單], 訂單送出待買家確認
				case 2:
					statusTimeLong = order.getOrdertime().getTime();
					statusDelay = now - statusTimeLong;
					if (statusDelay > threeHoursMillis) {
						orderObj.put("statustime", sdf.format(order.getOrdertime()));
						orderObj.put("delayhours", (int) time.convert(Duration.ofMillis(statusDelay)));
						urgentBuyingOrders.add(orderObj);
					}
					break;
				// 賣方按下[確認訂單], 訂單成立待出貨
				case 3:
					statusTimeLong = order.getAcceptordertime().getTime();
					statusDelay = now - statusTimeLong;
					if (statusDelay > threeHoursMillis) {
						orderObj.put("statustime", sdf.format(order.getAcceptordertime()));
						orderObj.put("delayhours", (int) time.convert(Duration.ofMillis(statusDelay)));
						urgentBuyingOrders.add(orderObj);
					}
					break;
				// 賣方按下[全配及出貨], 訂單出貨中
				case 4:
					statusTimeLong = order.getExporttime().getTime();
					statusDelay = now - statusTimeLong;
					if (statusDelay > threeHoursMillis) {
						orderObj.put("statustime", sdf.format(order.getExporttime()));
						orderObj.put("delayhours", (int) time.convert(Duration.ofMillis(statusDelay)));
						urgentBuyingOrders.add(orderObj);
					}
					break;
				// 賣方物流送達買方, 待買方收貨入庫
				case 5:
					statusTimeLong = order.getArriveordertime().getTime();
					statusDelay = now - statusTimeLong;
					if (statusDelay > threeHoursMillis) {
						orderObj.put("statustime", sdf.format(order.getArriveordertime()));
						orderObj.put("delayhours", (int) time.convert(Duration.ofMillis(statusDelay)));
						urgentBuyingOrders.add(orderObj);
					}
					break;
				}
			}
			// 緊急叫貨清單按延遲時間由大到小排列
			Collections.sort(urgentBuyingOrders, new SortComparator("delayhours", "int", "desc"));
			for (int i = 0; i < urgentBuyingOrders.size(); i++) {
				list.put(urgentBuyingOrders.get(i));
			}
		}
		return list;
	}

	// 回傳緊急接單清單 [延遲時間 (現在時間 - Status2~5的各個接單清單的最新狀態時間) > 3hrs] && 延遲時間由大排到小
	public JSONArray returnPageSellList(int userId) throws Exception {
		// 最外層JsonArray
		JSONArray list = new JSONArray();
		// 日期用物件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long now = Instant.now().toEpochMilli();
		Long threeHoursMillis = (long) 10800000;
		Long statusTimeLong, statusDelay;
		TimeUnit time = TimeUnit.HOURS;

		List<JSONObject> urgentSellingOrders = new ArrayList<>();
		List<OrdersBean> sellingOrders = ordersRepository.findAllBySellerid(userId);
		if (sellingOrders != null) {
			for (OrdersBean order : sellingOrders) {
				String orderId = order.getOrderid();
				// 找出單筆訂單所有產品名稱
				List<String> productNameSpecs = view_product_order_orderdetailsRepository
						.findProductnamespecByOrderid(orderId);
				String totalProductNameSpec = "";
				for (String productNameSpec : productNameSpecs) {
					totalProductNameSpec += productNameSpec + "<BR>";
				}
				// 將值放入物件
				JSONObject orderObj = new JSONObject();
				orderObj.put("productnamespec", totalProductNameSpec);
				orderObj.put("orderid", orderId);
				orderObj.put("companyname", accountsRepository.findCompanynameByAccountid(order.getBuyerid()));
				orderObj.put("orderstatus", order.getOrderstatus());
				// 依照訂單狀態, 找出相應狀態時間
				switch (order.getOrderstatus()) {
				// 買方按下[送出叫貨單], 訂單送出待買家確認
				case 2:
					statusTimeLong = order.getOrdertime().getTime();
					statusDelay = now - statusTimeLong;
					if (statusDelay > threeHoursMillis) {
						orderObj.put("statustime", sdf.format(order.getOrdertime()));
						orderObj.put("delayhours", (int) time.convert(Duration.ofMillis(statusDelay)));
						urgentSellingOrders.add(orderObj);
					}
					break;
				// 賣方按下[確認訂單], 訂單成立待出貨
				case 3:
					statusTimeLong = order.getAcceptordertime().getTime();
					statusDelay = now - statusTimeLong;
					if (statusDelay > threeHoursMillis) {
						orderObj.put("statustime", sdf.format(order.getAcceptordertime()));
						orderObj.put("delayhours", (int) time.convert(Duration.ofMillis(statusDelay)));
						urgentSellingOrders.add(orderObj);
					}
					break;
				// 賣方按下[全配及出貨], 訂單出貨中
				case 4:
					statusTimeLong = order.getExporttime().getTime();
					statusDelay = now - statusTimeLong;
					if (statusDelay > threeHoursMillis) {
						orderObj.put("statustime", sdf.format(order.getExporttime()));
						orderObj.put("delayhours", (int) time.convert(Duration.ofMillis(statusDelay)));
						urgentSellingOrders.add(orderObj);
					}
					break;
				// 賣方物流送達買方, 待買方收貨入庫
				case 5:
					statusTimeLong = order.getArriveordertime().getTime();
					statusDelay = now - statusTimeLong;
					if (statusDelay > threeHoursMillis) {
						orderObj.put("statustime", sdf.format(order.getArriveordertime()));
						orderObj.put("delayhours", (int) time.convert(Duration.ofMillis(statusDelay)));
						urgentSellingOrders.add(orderObj);
					}
					break;
				}
			}
			// 緊急叫貨清單按延遲時間由大到小排列
			Collections.sort(urgentSellingOrders, new SortComparator("delayhours", "int", "desc"));
			for (int i = 0; i < urgentSellingOrders.size(); i++) {
				list.put(urgentSellingOrders.get(i));
			}
		}
		return list;
	}
}
