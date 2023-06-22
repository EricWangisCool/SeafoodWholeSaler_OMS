package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.table.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, String> {
	List<Order> findAllBySellerId(int sellerId);

	List<Order> findAllByBuyerId(int userId);

	Order findOneByOrderId(String orderId);

	List<Order> findAllByOrderId(String orderId);

	void deleteByOrderId(String orderId);

	List<Order> findAllBySellerIdAndBuyerIdAndOrderStatusAndOrderTimeBetweenAndCompleteOrderTimeBetween(
			Integer accountId, int buyerId, int i, Date dateofOrderTime, Date dateofCompleteOrderTime,
			Date dateofOrderTime2, Date dateofCompleteOrderTime2);

	List<Order> findAllBySellerIdAndOrderStatusAndOrderTimeBetweenAndCompleteOrdertimeBetween(Integer accountId,
																							  int i, Date dateofOrderTime, Date dateofCompleteOrderTime, Date dateofOrderTime2,
																							  Date dateofCompleteOrderTime2);

}
