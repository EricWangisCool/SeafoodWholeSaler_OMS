package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.OrdersBean;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersBean, String> {
	List<OrdersBean> findAllBySellerid(int sellerid);

	List<OrdersBean> findAllByBuyerid(int userId);

	OrdersBean findOneByOrderid(String orderId);

	List<OrdersBean> findAllByOrderid(String orderid);

	void deleteByOrderid(String orderid);

	List<OrdersBean> findAllBySelleridAndBuyeridAndOrderstatusAndOrdertimeBetweenAndCompleteordertimeBetween(
			Integer accountid, int buyerida, int i, Date dateofOrdertime, Date dateofCompleteOrdertime,
			Date dateofOrdertime2, Date dateofCompleteOrdertime2);

	List<OrdersBean> findAllBySelleridAndOrderstatusAndOrdertimeBetweenAndCompleteordertimeBetween(Integer accountid,
			int i, Date dateofOrdertime, Date dateofCompleteOrdertime, Date dateofOrdertime2,
			Date dateofCompleteOrdertime2);

}
