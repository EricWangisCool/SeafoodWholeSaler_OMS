package tw.com.ispan.eeit48.mainFunction.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainFunction.model.table.Product;
import tw.com.ispan.eeit48.mainFunction.model.table.Order;
import tw.com.ispan.eeit48.common.util.SortComparator;
import tw.com.ispan.eeit48.mainFunction.repository.table.AccountRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.OrderRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.ProductRepository;
import tw.com.ispan.eeit48.mainFunction.repository.view.Product_Order_OrderDetailRepository;
import static tw.com.ispan.eeit48.mainFunction.service.AuthService.getCurrentUserId;

@Service
public class PageService {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private Product_Order_OrderDetailRepository product_Order_OrderDetailRepository;

	/**
	 * 回傳使用者所有商品資訊, 包括已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
	 */
	public List<Map<String, Object>> getUserMainPageInfo() throws Exception {
		int userId = getCurrentUserId();
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(new HashMap<>(){{ put("pageList", returnPageList(userId));}});
		list.add(new HashMap<>(){{ put("pageBuyList", returnPageBuyList(userId));}});
		list.add(new HashMap<>(){{ put("pageSellList", returnPageSellList(userId));}});
		list.add(new HashMap<>(){{ put("userCompanyName", accountRepository.findCompanyNameByAccountId(userId));}});
		return list;
	}

	// 回傳緊急庫存清單, 判斷條件 = [("可出現貨" + "已叫現貨") < "警戒庫存"]  &&  依照缺貨數["安全庫存" - ("可出現貨" + "已叫現貨")]由大到小排列
	public List returnPageList(int userId) {
		List list = new ArrayList();
		List<Map<String, Object>> urgentOwnerProducts = new ArrayList<>();
		List<Product> ownerProducts = productRepository.findAllByOwnerIdByOrderByOwnerIdDesc(userId);
		if (ownerProducts != null) {
			for (Product ownerProduct : ownerProducts) {
				String ownerProductId = ownerProduct.getProductId();
				int sellableQty = productService.findSellableQtyByProductId(ownerProductId);
				int requestedQty = productService.findRequestedQtyByProductId(ownerProductId);
				int sellableAndRequestedQty = sellableQty + requestedQty;
				int warningQty = ownerProduct.getWarningQty();

				// 如果 ( "可出現貨" + "已叫現貨") < "警戒庫存", 就放入緊急庫存清單
				if (sellableAndRequestedQty < warningQty) {
					// "缺貨數"(outStock) = "安全庫存數"（safeQty) -  ("可出現貨" + "已叫現貨")(sellableAndRequestedQty)
					int safeQty = ownerProduct.getSafeQty();
					int outStock = safeQty - sellableAndRequestedQty;

					// 前端頁面需要值
					Map<String, Object> orderObj = new HashMap<>();
					orderObj.put("productNameSpec", ownerProduct.getProductNameSpec());
					orderObj.put("outStock", outStock);
					orderObj.put("safeQty", safeQty);
					orderObj.put("sellableAndRequestedQty", sellableAndRequestedQty);
					orderObj.put("warningQty", warningQty);
					urgentOwnerProducts.add(orderObj);
				}
			}
		}
		// 緊急庫存清單依照"缺貨數"由大到小排列
		Collections.sort(urgentOwnerProducts, new SortComparator("outStock", "int", "desc"));
		for (int i = 0; i < urgentOwnerProducts.size(); i++) {
			list.add(urgentOwnerProducts.get(i));
		}
		return list;
	}


	// 回傳緊急購買清單 [延遲時間 (現在時間 - Status2~5的各個購買清單的最新狀態時間) > 3hrs] && 延遲時間由大排到小
	public List returnPageBuyList(int userId) throws Exception {
		List list = new ArrayList();
		// 日期用物件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long now = Instant.now().toEpochMilli();
		long threeHoursMillis = 10800000;
		long statusTimeLong, statusDelay;
		TimeUnit time = TimeUnit.HOURS;

		List<Map<String, Object>> urgentBuyingOrders = new ArrayList<>();
		List<Order> buyingOrders = orderRepository.findAllByBuyerId(userId);
		if (buyingOrders != null) {
			for (Order order : buyingOrders) {
				String orderId = order.getOrderId();
				// 找出單筆訂單所有產品名稱
				List<String> productNameSpecs = product_Order_OrderDetailRepository.findProductNameSpecByOrderId(orderId);
				String totalProductNameSpec = "";
				for (String productNameSpec : productNameSpecs) {
					totalProductNameSpec += productNameSpec + "<BR>";
				}
				// 將值放入物件
				Map<String, Object> orderObj = new HashMap<>();
				orderObj.put("productNameSpec", totalProductNameSpec);
				orderObj.put("orderId", orderId);
				orderObj.put("companyName", accountRepository.findCompanyNameByAccountId(order.getSellerId()));
				orderObj.put("orderStatus", order.getOrderStatus());
				// 依照訂單狀態, 找出相應狀態時間
				switch (order.getOrderStatus()) {
					// 買方按下[送出叫貨單], 訂單送出待買家確認
					case 2:
						statusTimeLong = order.getOrderTime().getTime();
						statusDelay = now - statusTimeLong;
						if (statusDelay > threeHoursMillis) {
							orderObj.put("statusTime", sdf.format(order.getOrderTime()));
							orderObj.put("delayHours", (int) time.convert(Duration.ofMillis(statusDelay)));
							urgentBuyingOrders.add(orderObj);
						}
						break;
					// 賣方按下[確認訂單], 訂單成立待出貨
					case 3:
						statusTimeLong = order.getAcceptOrderTime().getTime();
						statusDelay = now - statusTimeLong;
						if (statusDelay > threeHoursMillis) {
							orderObj.put("statusTime", sdf.format(order.getAcceptOrderTime()));
							orderObj.put("delayHours", (int) time.convert(Duration.ofMillis(statusDelay)));
							urgentBuyingOrders.add(orderObj);
						}
						break;
					// 賣方按下[全配及出貨], 訂單出貨中
					case 4:
						statusTimeLong = order.getExportTime().getTime();
						statusDelay = now - statusTimeLong;
						if (statusDelay > threeHoursMillis) {
							orderObj.put("statusTime", sdf.format(order.getExportTime()));
							orderObj.put("delayHours", (int) time.convert(Duration.ofMillis(statusDelay)));
							urgentBuyingOrders.add(orderObj);
						}
						break;
					// 賣方物流送達買方, 待買方收貨入庫
					case 5:
						statusTimeLong = order.getArriveOrderTime().getTime();
						statusDelay = now - statusTimeLong;
						if (statusDelay > threeHoursMillis) {
							orderObj.put("statusTime", sdf.format(order.getArriveOrderTime()));
							orderObj.put("delayHours", (int) time.convert(Duration.ofMillis(statusDelay)));
							urgentBuyingOrders.add(orderObj);
						}
						break;
				}
			}
			// 緊急叫貨清單按延遲時間由大到小排列
			Collections.sort(urgentBuyingOrders, new SortComparator("delayHours", "int", "desc"));
			for (int i = 0; i < urgentBuyingOrders.size(); i++) {
				list.add(urgentBuyingOrders.get(i));
			}
		}
		return list;
	}

	// 回傳緊急接單清單 [延遲時間 (現在時間 - Status2~5的各個接單清單的最新狀態時間) > 3hrs] && 延遲時間由大排到小
	public List returnPageSellList(int userId) throws Exception {
		List list = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long now = Instant.now().toEpochMilli();
		long threeHoursMillis = 10800000;
		long statusTimeLong, statusDelay;
		TimeUnit time = TimeUnit.HOURS;

		List<Map<String, Object>> urgentSellingOrders = new ArrayList<>();
		List<Order> sellingOrders = orderRepository.findAllBySellerId(userId);
		if (sellingOrders != null) {
			for (Order order : sellingOrders) {
				String orderId = order.getOrderId();
				List<String> productNameSpecs = product_Order_OrderDetailRepository.findProductNameSpecByOrderId(orderId);
				String totalProductNameSpec = "";
				for (String productNameSpec : productNameSpecs) {
					totalProductNameSpec += productNameSpec + "<BR>";
				}
				Map<String, Object> orderObj = new HashMap<>();
				orderObj.put("productNameSpec", totalProductNameSpec);
				orderObj.put("orderId", orderId);
				orderObj.put("companyName", accountRepository.findCompanyNameByAccountId(order.getBuyerId()));
				orderObj.put("orderStatus", order.getOrderStatus());
				// 依照訂單狀態, 找出相應狀態時間
				switch (order.getOrderStatus()) {
					// 買方按下[送出叫貨單], 訂單送出待買家確認
					case 2:
						statusTimeLong = order.getOrderTime().getTime();
						statusDelay = now - statusTimeLong;
						if (statusDelay > threeHoursMillis) {
							orderObj.put("statusTime", sdf.format(order.getOrderTime()));
							orderObj.put("delayHours", (int) time.convert(Duration.ofMillis(statusDelay)));
							urgentSellingOrders.add(orderObj);
						}
						break;
					// 賣方按下[確認訂單], 訂單成立待出貨
					case 3:
						statusTimeLong = order.getAcceptOrderTime().getTime();
						statusDelay = now - statusTimeLong;
						if (statusDelay > threeHoursMillis) {
							orderObj.put("statusTime", sdf.format(order.getAcceptOrderTime()));
							orderObj.put("delayHours", (int) time.convert(Duration.ofMillis(statusDelay)));
							urgentSellingOrders.add(orderObj);
						}
						break;
					// 賣方按下[全配及出貨], 訂單出貨中
					case 4:
						statusTimeLong = order.getExportTime().getTime();
						statusDelay = now - statusTimeLong;
						if (statusDelay > threeHoursMillis) {
							orderObj.put("statusTime", sdf.format(order.getExportTime()));
							orderObj.put("delayHours", (int) time.convert(Duration.ofMillis(statusDelay)));
							urgentSellingOrders.add(orderObj);
						}
						break;
					// 賣方物流送達買方, 待買方收貨入庫
					case 5:
						statusTimeLong = order.getArriveOrderTime().getTime();
						statusDelay = now - statusTimeLong;
						if (statusDelay > threeHoursMillis) {
							orderObj.put("statusTime", sdf.format(order.getArriveOrderTime()));
							orderObj.put("delayHours", (int) time.convert(Duration.ofMillis(statusDelay)));
							urgentSellingOrders.add(orderObj);
						}
						break;
				}
			}
			// 緊急叫貨清單按延遲時間由大到小排列
			Collections.sort(urgentSellingOrders, new SortComparator("delayHours", "int", "desc"));
			for (int i = 0; i < urgentSellingOrders.size(); i++) {
				list.add(urgentSellingOrders.get(i));
			}
		}
		return list;
	}
}
