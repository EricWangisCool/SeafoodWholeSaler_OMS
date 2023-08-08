package tw.com.ispan.eeit48.mainFunction.repository.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainFunction.model.table.Product;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	int deleteByProductId(String productId);
	Product findOneByProductId(String productId);
	Optional<Product> findOneByProductIdAndAutoOrderFunction(String productId, String autoOrderFunction);
	boolean existsByProductId(String productId);

	@Query(value = """
			SELECT product_name_spec
			  FROM t_product
			 WHERE product_id = ?1
			"""
			, nativeQuery = true)
	String findProductNameSpecByProductId(String productId);

	@Query(value = """
			SELECT stock_qty		
			  FROM t_product  		
			 WHERE product_id = ?1	
			"""
			, nativeQuery = true)
	int findStockQtyByProductId(String productId);

	@Query(value = """
			SELECT safe_qty         
			  FROM t_product 			
			 WHERE product_id = ?1	
			"""
			, nativeQuery = true)
	Integer findSafeQtyByProductId(String productId);

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
             ORDER BY product_id DESC 
             LIMIT 1
             """
			, nativeQuery = true)
	String findLastProductIdByOwnerId(int ownerId);
}
