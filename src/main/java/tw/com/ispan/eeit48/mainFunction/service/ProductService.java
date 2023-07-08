package tw.com.ispan.eeit48.mainFunction.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainFunction.model.view.Product_Order_OrderDetail;
import tw.com.ispan.eeit48.mainFunction.model.table.SupplierProductForOwnerProduct;
import tw.com.ispan.eeit48.mainFunction.repository.table.ProductRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.mainFunction.repository.view.Product_Order_OrderDetailRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private Product_Order_OrderDetailRepository productOrderOrderDetailRepository;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;

	/**
	 * 可出現貨sellableQty = 產品庫存數 - 被訂購量
	 */
	public int findSellableQtyByProductId(int productId) {
		int stockQty = productRepository.findStockQtyByProductId(productId);
		int orderedQty = findOrderedQtyByProductId(productId);
		return stockQty - orderedQty;
	}

	/**
	 * 被訂購量orderedQty = 接單狀態為3~5的總數
	 */
	public int findOrderedQtyByProductId(int productId) {
		List<Product_Order_OrderDetail> orderedOrdersInfo = productOrderOrderDetailRepository
				.findAllByProductIdAndOrderStatusBetween(productId, 3, 5);

		int orderedQty = 0;
		for (Product_Order_OrderDetail orderedOrderInfo : orderedOrdersInfo) {
			int orderQty = orderedOrderInfo.getOrderQty() == null ? 0 : orderedOrderInfo.getOrderQty();
			orderedQty += orderQty;
		}
		return orderedQty;
	}

	/**
	 * 已叫現貨數requestedQty = 叫貨狀態為2~5的總數
	 */
	public int findRequestedQtyByProductId(int productId) {
		List<SupplierProductForOwnerProduct> supplierProductsInfo = supplierProductForOwnerProductRepository
				.findAllByProductId(productId);

		int requestedQty = 0;
		for (SupplierProductForOwnerProduct supplierProductInfo : supplierProductsInfo) {
			List<Product_Order_OrderDetail> orderedOrdersInfo = productOrderOrderDetailRepository
					.findAllByProductIdAndOrderStatusBetween(supplierProductInfo.getSupplierProductId(), 2, 5);

			for (Product_Order_OrderDetail orderedOrderInfo : orderedOrdersInfo) {
				int orderQty = orderedOrderInfo.getOrderQty() == null ? 0 : orderedOrderInfo.getOrderQty();
				requestedQty += orderQty;
			}
		}
		return requestedQty;
	}
}
