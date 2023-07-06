package tw.com.ispan.eeit48.mainFunction.repository.view;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainFunction.model.view.Product_Order_OrderDetail;

/**
 * A view made of order that joins orderDetail and sellerProductInfo
 */
@Repository
public interface Product_Order_OrderDetailRepository extends PagingAndSortingRepository<Product_Order_OrderDetail, Integer> {

	List<Product_Order_OrderDetail> findAllByProductIdAndOrderStatusBetween(int productId, int x, int y);

	List<Product_Order_OrderDetail> findAllByOwnerIdAndOrderStatusBetweenOrderByOrderTimeDesc(int accountId, int orderStatusStart, int orderStatusEnd);

	List<Product_Order_OrderDetail> findAllByOrderId(String orderId);

	@Query(value = """
			SELECT product_names_pec 
			FROM v_product_order_order_details 
			WHERE order_id = ?1
			""", nativeQuery = true)
	List<String> findProductNameSpecByOrderId(String orderId);

	List<Product_Order_OrderDetail> findAllByOrderIdAndOwnerId(String orderId, int accountId);

	List<Product_Order_OrderDetail> findAllByOwnerIdAndOrderStatusAndOrderTimeBetweenAndCompleteOrderTimeBetween(
			Integer ownerId, int orderStatus, Date orderTimeBegin, Date orderTimeEnd, Date completeOrderTimeBegin, Date completeOrderTimeEnd);

	List<Product_Order_OrderDetail> findAllByOwnerIdAndBuyerIdAndOrderStatusAndOrderTimeBetweenAndCompleteOrderTimeBetween(
			Integer ownerId, int buyerId, int orderStatus, Date orderTimeBegin, Date orderTimeEnd, Date completeOrderTimeBegin, Date completeOrderTimeEnd);

}
