package tw.com.ispan.eeit48.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.domain.View_product_order_orderdetailsBean;

@Repository
@Transactional
public interface View_product_order_orderdetailsRepository
		extends PagingAndSortingRepository<View_product_order_orderdetailsBean, Integer> {

	List<View_product_order_orderdetailsBean> findAllByProductid(int productid);

	List<View_product_order_orderdetailsBean> findAllByProductidAndOrderstatusBetween(int productid, int x, int y);

	List<View_product_order_orderdetailsBean> findAllByProductidAndOrderstatusAndBuyerid(int productid, int x, int y);

	List<View_product_order_orderdetailsBean> findAllByProductidAndOrderstatus(int productid, int x);

	List<View_product_order_orderdetailsBean> findAllByBuyerid(int buyerId);

	List<View_product_order_orderdetailsBean> findAllByProductidLike(Integer string);

	List<View_product_order_orderdetailsBean> findAllByOwnerid(int i);

	Date existsByAcceptordertime(Date acceptordertime);

	List<View_product_order_orderdetailsBean> findAllByOwneridAndOrderstatusBetween(Integer accountid, int x, int y);

	char[] findAllByAcceptordertimeIsNull();

	List<View_product_order_orderdetailsBean> findAllByOrderid(String orderId);

	@Query(value = "SELECT productnamespec FROM view_product_order_orderdetails WHERE orderid = ?1", nativeQuery = true)
	List<String> findProductnamespecByOrderid(String orderId);

	List<View_product_order_orderdetailsBean> findAllByProductidAndBuyerid(int i, int buyerida);


}
