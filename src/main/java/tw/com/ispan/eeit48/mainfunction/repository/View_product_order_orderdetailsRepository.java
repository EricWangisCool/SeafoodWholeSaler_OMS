package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.View_ProductOrder_OrderDetails_Bean;

@Repository
public interface View_product_order_orderdetailsRepository extends PagingAndSortingRepository<View_ProductOrder_OrderDetails_Bean, Integer> {

	List<View_ProductOrder_OrderDetails_Bean> findAllByProductid(int productid);

	List<View_ProductOrder_OrderDetails_Bean> findAllByProductidAndOrderstatusBetween(int productid, int x, int y);

	List<View_ProductOrder_OrderDetails_Bean> findAllByOwneridAndOrderstatusBetween(Integer accountid, int x, int y);

	List<View_ProductOrder_OrderDetails_Bean> findAllByOrderid(String orderId);

	@Query(value = "SELECT productnamespec FROM view_product_order_orderdetails WHERE orderid = ?1", nativeQuery = true)
	List<String> findProductnamespecByOrderid(String orderId);

	List<View_ProductOrder_OrderDetails_Bean> findAllByOrderidAndOwnerid(String orderid, int accountid);

}
