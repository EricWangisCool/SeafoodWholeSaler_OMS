package tw.com.ispan.eeit48.mainFunction.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.common.dto.request.UpdateOrderRequest;
import tw.com.ispan.eeit48.mainFunction.model.table.Product;
import tw.com.ispan.eeit48.mainFunction.model.view.Product_Order_OrderDetail;
import tw.com.ispan.eeit48.mainFunction.model.table.OrderDetail;
import tw.com.ispan.eeit48.mainFunction.model.table.Order;
import tw.com.ispan.eeit48.mainFunction.repository.table.*;
import tw.com.ispan.eeit48.mainFunction.repository.view.Product_Order_OrderDetailRepository;
import static tw.com.ispan.eeit48.common.util.CommonUtil.convertObjectToMap;
import static tw.com.ispan.eeit48.mainFunction.service.AuthService.getCurrentUserId;

@Service
public class OrderBuyService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private Product_Order_OrderDetailRepository productOrderOrderDetailRepository;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;

	// 找出使用者的所有叫貨訂單資訊
	public List<Map<String, Object>> findUserRequestedOrders() throws Exception {
		List<Order> orders = orderRepository.findAllByBuyerIdOrderByOrderTimeDesc(getCurrentUserId());
		if (orders.isEmpty()) return null;

		List<Map<String, Object>> result = new ArrayList<>();
		orders.forEach(order -> {
			List<Product_Order_OrderDetail> ordersInfo = productOrderOrderDetailRepository
					.findAllByOrderId(order.getOrderId());

			String productNameSpecPerOrder = "";
			int total = 0;
			for (Product_Order_OrderDetail orderInfo : ordersInfo) {
				productNameSpecPerOrder += orderInfo.getProductNameSpec() + "<BR>";
				int orderQty = orderInfo.getOrderQty() == null ? 0 : orderInfo.getOrderQty();
				int unitDealPrice = orderInfo.getUnitDealPrice() == null ? 0 : orderInfo.getUnitDealPrice();
				total +=  orderQty * unitDealPrice;
			}

			Map<String, Object> orderMap = convertObjectToMap(order);
			orderMap.put("productNameSpec", productNameSpecPerOrder);
			orderMap.put("companyName", ordersInfo.get(0).getCompanyName());
			orderMap.put("total", total);
			result.add(orderMap);
		});
		return result;
	}

	public List<Map<String, Object>> findOrderDetailByOrderId(String orderId) throws Exception {
		List<OrderDetail> ordersDetail = orderDetailRepository.findAllByOrderId(orderId);
		if (ordersDetail.isEmpty()) throw new Exception("OrdersDetail not found for the given order id!");

		List<Map<String, Object>> result = new ArrayList<>();
		ordersDetail.forEach(orderDetail -> {
			String productNameSpec = productRepository.findProductNameSpecByProductId(orderDetail.getSellerProductId());
			int orderQty = orderDetail.getOrderQty() == null ? 0 : orderDetail.getOrderQty();
			int unitDealPrice = orderDetail.getUnitDealPrice() == null ? 0 : orderDetail.getUnitDealPrice();
			int detailTotal = orderQty * unitDealPrice;

			Map<String, Object> orderDetailMap = convertObjectToMap(orderDetail);
			orderDetailMap.put("productNameSpec", productNameSpec);
			orderDetailMap.put("detailTotal", detailTotal);
			result.add(orderDetailMap);
		});
		return result;
	}

	@Transactional
	public void updateOrder(UpdateOrderRequest request) throws Exception {
		String orderId = request.getOrderId();
		Order order;
		Optional<Order> orderOptional = orderRepository.findOneByOrderId(orderId);
		if (orderOptional.isPresent()) {
			order = orderOptional.get();
		} else {
			throw new Exception("Order not found for requested order id");
		}

		Integer orderStatus = request.getOrderStatus();
		order.setOrderStatus(orderStatus);
		switch (orderStatus) {
			// 買方按下[送出叫貨單], 訂單送出待買家確認
			case 2 -> order.setOrderTime(new Date());

			// 賣方按下[確認訂單], 訂單成立待出貨
			case 3 -> order.setAcceptOrderTime(new Date());

			// 賣方按下[全配及出貨], 訂單出貨中
			case 4 -> {
				order.setExportTime(new Date());
				order.setDeliveryOrderId("EX" + orderId);
				order.setDeliveryOrderRemark(request.getDeliveryOrderRemark());
			}

			// 賣方物流送達買方, 待買方收貨入庫
			case 5 -> order.setArriveOrderTime(new Date());

			// 買方案下[確認入庫], 入庫和訂單同時完成
			case 6 -> {
				productInStockOutStock(orderId);
				order.setCompleteOrderTime(new Date());
			}
			// 買方或賣方按下[取消訂單], 訂單不成立
			case 7 -> order.setCancelOrderTime(new Date());

			// 買家將暫存訂單刪除
			case 8 -> orderRepository.deleteByOrderId(orderId);
		}

		// 如果訂單狀態為2~7, 就將newOrdersBean資料修改進DB
		if (orderStatus > 1 && orderStatus < 8) {
			orderRepository.save(order);
		} else if (orderStatus != 8){
			throw new Exception("Do not support requested order status");
		}
	}

	// 將訂單內使用者所有商品進行入庫, 同時將各商品的UnitCost以平均單價更新, StockQty以"現在庫存數量加上訂單進貨數量"更新
	// 將訂單內供應商所有商品進行售出, StockQty以"現在庫存數量減去訂單進貨數量"更新
	public void productInStockOutStock(String orderId) {
		List<Product_Order_OrderDetail> ordersInfo = productOrderOrderDetailRepository.findAllByOrderId(orderId);

		ordersInfo.forEach(orderInfo -> {
			String sellerProductId = orderInfo.getProductId();
			int inStockPrice = orderInfo.getUnitDealPrice();
			int inStockQty = orderInfo.getOrderQty();
			int originalStockQty;
			int newStockQty;

			// User stock
			String userProductId = supplierProductForOwnerProductRepository.findUserProductIdBySupplierProductId(sellerProductId);
			Product userProduct = productRepository.findOneByProductId(userProductId);

			int originalUnitCost = userProduct.getUnitCost();
			originalStockQty = userProduct.getStockQty();
			newStockQty = originalStockQty + inStockQty;
			int newAverageUnitCost = ((originalUnitCost * originalStockQty) + (inStockPrice * inStockQty)) / (newStockQty);

			userProduct.setStockQty(newStockQty);
			userProduct.setUnitCost(newAverageUnitCost);
			productRepository.save(userProduct);

			// Supplier stock
			int outStockQty = orderInfo.getOrderQty();
			Product supplierProduct = productRepository.findOneByProductId(sellerProductId);
			originalStockQty = supplierProduct.getStockQty();
			newStockQty = originalStockQty - outStockQty;
			supplierProduct.setStockQty(newStockQty);
			productRepository.save(supplierProduct);
		});
	}
}
