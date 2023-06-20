package tw.com.ispan.eeit48.mainfunction.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainfunction.model.table.Product;
import tw.com.ispan.eeit48.mainfunction.model.view.ProductOrder_OrderDetail;
import tw.com.ispan.eeit48.mainfunction.model.table.OrderDetail;
import tw.com.ispan.eeit48.mainfunction.model.table.Order;
import tw.com.ispan.eeit48.common.util.SortComparator;
import tw.com.ispan.eeit48.mainfunction.repository.OrderDetailsRepositrory;
import tw.com.ispan.eeit48.mainfunction.repository.OrdersRepository;
import tw.com.ispan.eeit48.mainfunction.repository.ProductRepository;
import tw.com.ispan.eeit48.mainfunction.repository.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.mainfunction.repository.View_product_order_orderdetailsRepository;
import static tw.com.ispan.eeit48.mainfunction.service.AuthService.getCurrentUserId;

@Service
@Transactional
public class OrderBuyService {
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrderDetailsRepositrory orderDetailsRepositrory;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private View_product_order_orderdetailsRepository view_product_order_orderdetailsRepository;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;

	// 製作新訂單編號, 編號邏輯是: 使用者ID + "D" + 今天日期 + 四位數流水號
	public String createNewBookingOrderId(int userId) {
		int serialNumber = 1;

		// 今天日期
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		String simpleToday = sdFormat.format(new Date());
		System.out.println("Today is= " + simpleToday);

		// 找出使用者所有訂單, 查看是否有今天日期的訂單(只能找出來比對, 因為DB裡是dateTime, 無法單純使用date做搜尋)
		List<Order> orders = ordersRepository.findAllByBuyerid(userId);
		if (orders != null) {
			for (Order order : orders) {
				String orderId = order.getOrderid();
				int indexAfterD = orderId.indexOf("D") + 1;
				String orderCreateDate = orderId.substring(indexAfterD, indexAfterD + 8);
				// 如果有今天日期的訂單, 就把最新訂單的末四碼流水號叫出來+1成為最新訂單流水號
				if (simpleToday.equals(orderCreateDate)) {
					// 訂單末四碼流水號
					int firstIndexOfSerialNum = indexAfterD + 8;
					serialNumber = Integer.valueOf(orderId.substring(firstIndexOfSerialNum, firstIndexOfSerialNum + 4))
							+ 1;
				} else {
					System.out.println("This order is not for today: " + orderId);
				}
			}
		} else {
			System.out.println("No order for this user");
		}
		// 建立新訂單編號
		String newOrderId = String.format("%dD%s%04d", userId, simpleToday, serialNumber);
		return newOrderId;
	}

	// 找出使用者的所有叫貨訂單資訊
	public List<JSONObject> findUserOrders() {
		int userId = getCurrentUserId();

		List<JSONObject> list = new ArrayList();
		List<JSONObject> bookingOrdersJsonList = new ArrayList();
		List<Order> bookingOrders = ordersRepository.findAllByBuyerid(userId);
		for (Order bookingOrder : bookingOrders) {
			String bookingOrderId = bookingOrder.getOrderid();
			int indexAfterD = bookingOrderId.indexOf("D") + 1;
			int orderDateSerial = (int) Long.parseLong(bookingOrderId.substring(indexAfterD, indexAfterD + 12));
			JSONObject bookingOrderJson = bookingOrder.toJsonObject().put("orderDateSerial", orderDateSerial);
			bookingOrdersJsonList.add(bookingOrderJson);
		}
		// 叫貨單按訂購時間由新至舊排列
		Collections.sort(bookingOrdersJsonList, new SortComparator("orderDateSerial", "int", "desc"));
		for (int i = 0; i < bookingOrdersJsonList.size(); i++) {
			list.add(bookingOrdersJsonList.get(i));
		}
		return list;
	}

	// 以orderId找出: 訂單總金額
	public int findTotalAmountByOrderId(String orderId) {
		int totalPriceOfOrderId = 0;
		// 依照orderId, 找出該訂單的所有產品資訊
		List<OrderDetail> orderDeatils = orderDetailsRepositrory.findAllByOrderid(orderId);
		// 依照該訂單所有產品資訊, 尋訪單個產品資訊
		for (OrderDetail orderDeatil : orderDeatils) {
			// 該產品小計
			int orderQty = orderDeatil.getOrderqty() == null ? 0 : orderDeatil.getOrderqty();
			int unitDealPrice = orderDeatil.getUnitdealprice() == null ? 0 : orderDeatil.getUnitdealprice();
			int totalPricePerProduct = orderQty * unitDealPrice;
			totalPriceOfOrderId += totalPricePerProduct;
		}
		return totalPriceOfOrderId;
	}

	// 新增一筆叫貨單
	public Order saveNewOrdersBean(Order newOrder) {
		return ordersRepository.save(newOrder);
	}

	// 新增一筆叫貨單明細
	public Boolean saveNewOrderDetailsBean(List<OrderDetail> newOrderDetails) {
		if (!newOrderDetails.isEmpty()) {
			for (OrderDetail newOrderDetailsbean : newOrderDetails) {
				orderDetailsRepositrory.save(newOrderDetailsbean);
			}
			return true;
		} else {
			return false;
		}
	}

	// 修改一筆叫貨單
	public Order updateOrdersBean(Order bean) {
		Order result = null;
		if (bean != null && bean.getOrderid() != null) {
			if (ordersRepository.existsById(bean.getOrderid())) {
				result = ordersRepository.save(bean);
			}
		}
		return result;
	}

	// 修改一筆叫貨單明細
	public OrderDetail updateOrderDetialsBean(OrderDetail bean) {
		OrderDetail result = null;
		if (bean != null && bean.getOrderid() != null) {
			if (orderDetailsRepositrory.existsById(bean.getOrderid())) {
				result = orderDetailsRepositrory.save(bean);
			}
		}
		return result;
	}

	// 刪除一筆暫存叫貨單
	public Boolean deleteOrdersBean(Order bean) {
		Boolean result = false;
		if (bean != null && bean.getOrderid() != null) {
			if (ordersRepository.existsById(bean.getOrderid())) {
				ordersRepository.deleteByOrderid(bean.getOrderid());
				return true;
			}
		}
		return result;
	}

	// 將一筆訂單內使用者所有商品進行入庫, 同時將各商品的UnitCost以平均單價更新, 將StockQty以"現在庫存數量加上訂單進貨數量"更新
	public Boolean productsInStockByOrderId(String orderId) {
		Boolean saveResult = false;
		List<ProductOrder_OrderDetail> beans = view_product_order_orderdetailsRepository
				.findAllByOrderid(orderId);
		if (!beans.isEmpty()) {
			for (ProductOrder_OrderDetail bean : beans) {
				// 賣家與進貨單資訊
				int sellerProductId = bean.getProductid();
				int inStockPrice = bean.getUnitdealprice();
				int inStockQty = bean.getOrderqty();
				// 使用者與產品資訊
				List<Integer> ownerProductIds = supplierProductForOwnerProductRepository
						.findProductIdBySupplierProductId(sellerProductId);
				int ownerProductId = ownerProductIds.get(0);
				Product ownerProduct = productRepository.findOneByProductId(ownerProductId);
				int originalUnitCost = ownerProduct.getUnitCost();
				int originalStockQty = ownerProduct.getStockQty();
				// 新庫存數 = 現在庫存數量 + 訂單進貨數量
				int newStockQty = originalStockQty + inStockQty;
				// 平均單價 = [(現在庫存的 成本單價 * 數量) + (訂單進價 * 數量)] / (現在庫存數量 + 訂單進貨數量)
				int newAverageUnitCost = ((originalUnitCost * originalStockQty) + (inStockPrice * inStockQty))
						/ (newStockQty);

				// 將值進行存檔
				ownerProduct.setStockQty(newStockQty);
				ownerProduct.setUnitCost(newAverageUnitCost);
				Product savedBean = productRepository.save(ownerProduct);
				if (savedBean != null) {
					saveResult = true;
				}
			}
		}
		return saveResult;
	}

	// 將一筆訂單內供應商所有商品進行售出(StockQty以"現在庫存數量減去訂單進貨數量"更新)
	public Boolean productsOutStockByOrderId(String orderId) {
		Boolean saveResult = false;
		List<ProductOrder_OrderDetail> beans = view_product_order_orderdetailsRepository
				.findAllByOrderid(orderId);
		if (!beans.isEmpty()) {
			for (ProductOrder_OrderDetail bean : beans) {
				// 賣家與進貨單資訊
				int sellerProductId = bean.getProductid();
				int outStockQty = bean.getOrderqty();
				Product supplierProduct = productRepository.findOneByProductId(sellerProductId);
				int originalStockQty = supplierProduct.getStockQty();
				// 新庫存數 = 現在庫存數量 - 訂單出售數量
				int newStockQty = originalStockQty - outStockQty;
				// 將值進行存檔
				supplierProduct.setStockQty(newStockQty);
				Product savedBean = productRepository.save(supplierProduct);
				if (savedBean != null) {
					saveResult = true;
				}
			}
		}
		return saveResult;
	}

}
