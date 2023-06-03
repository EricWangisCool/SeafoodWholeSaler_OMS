package tw.com.ispan.eeit48.mainfunction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainfunction.model.ProductBean;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductBean, Integer> {
	@Transactional
	int deleteByProductId(Integer productId);
	List<ProductBean> findAllByProductId(Integer productId);
	List<ProductBean> findAllByAutoOrderFunctionAndOwnerId(String autoOrderFunc, Integer accountId);
	ProductBean findOneByProductId(int productId);
	boolean existsById(int id);

	@Query(value = """
			SELECT productNameSpec
			  FROM Product
			 WHERE productId = ?1
			"""
			, nativeQuery = true)
	String findProductNameSpecByProductId(Integer productId);

	@Query(value = """
			SELECT stockQty		
			  FROM Product  		
			 WHERE productId = ?1	
			"""
			, nativeQuery = true)
	int findStockQtyByProductId(Integer productId);

	@Query(value = """
			SELECT safeQty         
			  FROM Product 			
			 WHERE productId = ?1	
			"""
			, nativeQuery = true)
	Integer findSafeQtyByProductId(Integer productId);

	@Query(value = """
			SELECT * 				 
			  FROM Product 		     
			 WHERE ownerId = ?1 	 
			 ORDER BY productId DESC 
			"""
			, nativeQuery = true)
	List<ProductBean> findAllByOwnerIdByOrderByOwnerIdDesc(int ownerId);

	@Query(value = """
            SELECT productId 
              FROM Product 
             WHERE ownerId = ?1 
             ORDER BY ProductId DESC 
             LIMIT 1
             """
			, nativeQuery = true)
	Integer findLastProductIdByOwnerId(int ownerId);

}
