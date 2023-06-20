package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.table.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, String> {
	List<Order> findAllBySellerid(int sellerid);

	List<Order> findAllByBuyerid(int userId);

	Order findOneByOrderid(String orderId);

	List<Order> findAllByOrderid(String orderid);

	void deleteByOrderid(String orderid);

	List<Order> findAllBySelleridAndBuyeridAndOrderstatusAndOrdertimeBetweenAndCompleteordertimeBetween(
			Integer accountid, int buyerida, int i, Date dateofOrdertime, Date dateofCompleteOrdertime,
			Date dateofOrdertime2, Date dateofCompleteOrdertime2);

	List<Order> findAllBySelleridAndOrderstatusAndOrdertimeBetweenAndCompleteordertimeBetween(Integer accountid,
																							  int i, Date dateofOrdertime, Date dateofCompleteOrdertime, Date dateofOrdertime2,
																							  Date dateofCompleteOrdertime2);

}
