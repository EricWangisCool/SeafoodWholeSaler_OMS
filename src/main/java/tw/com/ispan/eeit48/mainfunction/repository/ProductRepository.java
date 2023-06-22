package tw.com.ispan.eeit48.mainfunction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainfunction.model.table.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Transactional
	int deleteByProductId(Integer productId);
	List<Product> findAllByProductId(Integer productId);
	List<Product> findAllByAutoOrderFunctionAndOwnerId(String autoOrderFunc, Integer accountId);
	Product findOneByProductId(int productId);
	boolean existsById(int id);

	@Query(value = """
			SELECT product_name_spec
			  FROM t_product
			 WHERE product_id = ?1
			"""
			, nativeQuery = true)
	String findProductNameSpecByProductId(Integer productId);

	@Query(value = """
			SELECT stock_qty		
			  FROM t_product  		
			 WHERE product_id = ?1	
			"""
			, nativeQuery = true)
	int findStockQtyByProductId(Integer productId);

	@Query(value = """
			SELECT safe_qty         
			  FROM t_product 			
			 WHERE product_id = ?1	
			"""
			, nativeQuery = true)
	Integer findSafeQtyByProductId(Integer productId);

	@Query(value = """
			SELECT * 				 
			  FROM t_product 		     
			 WHERE owner_id = ?1 	 
			 ORDER BY product_id DESC 
			"""
			, nativeQuery = true)
	List<Product> findAllByOwnerIdByOrderByOwnerIdDesc(int ownerId);

	@Query(value = """
            SELECT product_id 
              FROM t_product 
             WHERE owner_id = ?1 
             ORDER BY Product_id DESC 
             LIMIT 1
             """
			, nativeQuery = true)
	Integer findLastProductIdByOwnerId(int ownerId);

}
