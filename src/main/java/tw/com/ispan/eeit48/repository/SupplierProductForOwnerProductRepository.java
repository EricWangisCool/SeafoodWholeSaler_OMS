package tw.com.ispan.eeit48.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.domain.SupplierProductForOwnerProductBean;

@Repository
public interface SupplierProductForOwnerProductRepository
		extends JpaRepository<SupplierProductForOwnerProductBean, Integer> {

	void deleteAllByProductid(Object object);

	List<SupplierProductForOwnerProductBean> findAllByProductid(int productId);

	@Query(value = "select productid from supplierproductforownerproduct where supplierproductid = ?1", nativeQuery = true)
	List<Integer> findProductIdBySupplierProductId(Integer supplierProductId);

	@Query(value = "select supplierproductid from supplierproductforownerproduct where productid  = ?1", nativeQuery = true)
	Integer findSupplierProductIdByOwnerProductId(Integer supplierProductId);

	SupplierProductForOwnerProductBean findOneByProductid(int ownerProductId);

	void deleteAllBySupplierproductidAndProductid(Integer productid, Integer integer);

	List<SupplierProductForOwnerProductBean> findAllBySupplierproductid(Integer productid);

}
