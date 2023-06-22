package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.view.ProductOrder_OrderDetail;

@Repository
public interface View_product_order_orderdetailsRepository extends PagingAndSortingRepository<ProductOrder_OrderDetail, Integer> {

	List<ProductOrder_OrderDetail> findAllByProductId(int productId);

	List<ProductOrder_OrderDetail> findAllByProductIdAndOrderstatusBetween(int productId, int x, int y);

	List<ProductOrder_OrderDetail> findAllByOwnerIdAndOrderstatusBetween(Integer accountId, int x, int y);

	List<ProductOrder_OrderDetail> findAllByOrderId(String orderId);

	@Query(value = """
			SELECT product_names_pec 
			FROM v_product_order_order_details 
			WHERE order_id = ?1
			""", nativeQuery = true)
	List<String> findProductNameSpecByOrderId(String orderId);

	List<ProductOrder_OrderDetail> findAllByOrderIdAndOwnerId(String orderId, int accountId);

}
