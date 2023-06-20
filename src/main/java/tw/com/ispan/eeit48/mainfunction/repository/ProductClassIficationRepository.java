package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.table.ProductClassification;

@Repository
public interface ProductClassIficationRepository extends JpaRepository<ProductClassification, Integer> {

	List<ProductClassification> findAllByClassid(int i);

}
