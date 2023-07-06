package tw.com.ispan.eeit48.mainFunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.common.dto.request.CreateOrderRequest;
import tw.com.ispan.eeit48.mainFunction.model.table.*;
import tw.com.ispan.eeit48.mainFunction.repository.table.OrderDetailRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.OrderRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.ProductRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.SupplierProductForOwnerProductRepository;
import java.util.*;
import static tw.com.ispan.eeit48.common.util.CommonUtil.convertObjectToMap;
import static tw.com.ispan.eeit48.mainFunction.service.AuthService.getCurrentUserId;

@Service
public class GoodsService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderSellService orderSellService;
	@Autowired
	private OrderBuyService orderBuyService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	public List<Map<String, Object>> getSupplierProductInfo(int supplierId) {
		List<Map<String, Object>> list = new ArrayList<>();

		List<Product> supplierProducts = productRepository.findAllByOwnerIdByOrderByOwnerIdDesc(supplierId);
		supplierProducts.forEach(supplierProduct -> {
			// 從單筆供應商productId, 查看是否為自己productId的補貨對象, 是的話回傳缺貨數(outStock), 不是的話回傳0
			int userOutStock = 0;
			Integer userProductId = supplierProductForOwnerProductRepository
					.findUserProductIdBySupplierProductId(supplierProduct.getProductId());
			if (userProductId != null) {
				userOutStock += findOutStockByProductId(userProductId);
			}

			Map<String, Object> supplierProductMap =  convertObjectToMap(supplierProduct);
			supplierProductMap.put("userOutStock", userOutStock);
			supplierProductMap.putIfAbsent("reservedQty", "null");
			supplierProductMap.putIfAbsent("productPic", "null");
			list.add(supplierProductMap);
		});
		return list;
	}

	// 缺貨數outStock = 安全庫存 - 可出現貨 - 已叫現貨
	private int findOutStockByProductId(Integer productId) {
		int safeQty = productRepository.findSafeQtyByProductId(productId);
		int sellableQty = productService.findSellableQtyByProductId(productId);
		int requestedQty = productService.findRequestedQtyByProductId(productId);
		return safeQty - sellableQty - requestedQty;
	}

	@Transactional
	public void createNewOrder(CreateOrderRequest request) {
		int userId = getCurrentUserId();
		String newOrderId = orderSellService.createNewOrderId(userId);

		Order newOrder = new Order();
		newOrder.setOrderId(newOrderId);
		newOrder.setBuyerId(userId);
		newOrder.setSellerId(request.getSellerId());
		newOrder.setOrderStatus(1);  // 買方按下[新增商品]建立叫貨單, 訂單暫存未送出(orderStatus = 1)
		newOrder.setPaymentTerms("銀行轉帳<br>月結(TT30)");

		List<OrderDetail> newOrderDetails = new ArrayList<>();
		request.getProducts().forEach(product -> {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(newOrderId);
			orderDetail.setSellerProductId(product.getSellerProductId());
			orderDetail.setOrderQty(product.getOrderQty());
			orderDetail.setUnitDealPrice(product.getUnitDealPrice());
			newOrderDetails.add(orderDetail);
		});

		orderRepository.save(newOrder);
		newOrderDetails.forEach(orderDetail -> orderDetailRepository.save(orderDetail));
	}
}
