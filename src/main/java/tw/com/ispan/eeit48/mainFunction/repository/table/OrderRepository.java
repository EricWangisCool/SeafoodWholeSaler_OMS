package tw.com.ispan.eeit48.mainFunction.repository.table;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainFunction.model.table.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	List<Order> findAllBySellerId(int sellerId);

	List<Order> findAllByBuyerId(int buyerId);

	@Query(value = """
			SELECT order_id				 
			  FROM t_orders 		     
			 WHERE buyer_id = ?1
			   AND (order_time BETWEEN ?2 AND ?3)  	 
			 ORDER BY order_time DESC
			 LIMIT 1
			"""
			, nativeQuery = true)
	Optional<String> findLastOrderIdByBuyerIdAndOrderTimeBetween(int buyerId, Date todayStart, Date todayEnd);

	Optional<Order> findOneByOrderId(String orderId);

	void deleteByOrderId(String orderId);

	List<Order> findAllByBuyerIdOrderByOrderTimeDesc(int buyerId);

}
