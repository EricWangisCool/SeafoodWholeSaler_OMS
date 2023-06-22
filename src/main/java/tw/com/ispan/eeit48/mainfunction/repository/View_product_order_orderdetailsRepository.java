package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.view.ProductOrder_OrderDetail;

@Repository
public interface View_product_order_orderdetailsRepository extends PagingAndSortingRepository<ProductOrder_OrderDetail, Integer> {

	List<ProductOrder_OrderDetail> findAllByProductid(int productid);

	List<ProductOrder_OrderDetail> findAllByProductidAndOrderstatusBetween(int productid, int x, int y);

	List<ProductOrder_OrderDetail> findAllByOwneridAndOrderstatusBetween(Integer accountid, int x, int y);

	List<ProductOrder_OrderDetail> findAllByOrderid(String orderId);

	@Query(value = """
			SELECT productnamespec 
			FROM view_product_order_orderdetails 
			WHERE orderid = ?1
			""", nativeQuery = true)
	List<String> findProductnamespecByOrderid(String orderId);

	List<ProductOrder_OrderDetail> findAllByOrderidAndOwnerid(String orderid, int accountid);

}
