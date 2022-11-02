package tw.com.ispan.eeit48.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.domain.ProductBean;

@Repository
public interface ProductRepository extends JpaRepository<ProductBean, Integer> {
	List<ProductBean> findAllByOwnerid(int ownerid);

	List<ProductBean> findAllByProductid(Integer productid);

	void deleteByProductid(Integer productid);

	@Query(value = "select productnamespec from product where productid = ?1", nativeQuery = true)
	String findProductNameByProductid(Integer sellerproductid);

	@Query(value = "select stockqty from product where productid = ?1", nativeQuery = true)
	int findStockqtyByProductid(Integer productId);

	@Query(value = "select safeqty from product where productid = ?1", nativeQuery = true)
	Integer findSafeQtyByProductid(Integer productId);

	@Query(value = "select ownerid from product where productid = ?1", nativeQuery = true)
	int findOwnerIdByProductid(Integer productId);

	@Query(value = "select unitcost from product where productid = ?1", nativeQuery = true)
	int findUnitCostByProductid(int productId);

	List<ProductBean> findAllByAutoorderfunctionAndOwnerid(String string, Integer accountid);

	@Query(value = "SELECT * FROM product WHERE ownerid = ?1 ORDER BY productId DESC", nativeQuery = true)
	List<ProductBean> findAllByOwneridByOrderByOwneridDesc(int ownerid);

	ProductBean findOneByProductid(int productId);

}
