package tw.com.ispan.eeit48.mainFunction.repository.table;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainFunction.model.table.SupplierProductForOwnerProduct;

@Repository
public interface SupplierProductForOwnerProductRepository extends JpaRepository<SupplierProductForOwnerProduct, Integer> {

	List<SupplierProductForOwnerProduct> findAllByProductId(String productId);

	@Query(value = """
			SELECT product_id 
			FROM t_supplier_product_for_owner_product 
			WHERE supplier_product_id = ?1
			LIMIT 1
			""", nativeQuery = true)
	String findUserProductIdBySupplierProductId(String supplierProductId);

	SupplierProductForOwnerProduct findOneByProductId(String ownerProductId);

}
