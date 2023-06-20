package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.table.SupplierProductForOwnerProduct;

@Repository
public interface SupplierProductForOwnerProductRepository extends JpaRepository<SupplierProductForOwnerProduct, Integer> {

	List<SupplierProductForOwnerProduct> findAllByProductid(int productId);

	@Query(value = "select productid from supplierproductforownerproduct where supplierproductid = ?1", nativeQuery = true)
	List<Integer> findProductIdBySupplierProductId(Integer supplierProductId);

	SupplierProductForOwnerProduct findOneByProductid(int ownerProductId);

}
