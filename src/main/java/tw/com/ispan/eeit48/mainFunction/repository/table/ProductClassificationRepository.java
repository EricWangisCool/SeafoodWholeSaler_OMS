package tw.com.ispan.eeit48.mainFunction.repository.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainFunction.model.table.ProductClassification;

import java.util.Optional;

@Repository
public interface ProductClassificationRepository extends JpaRepository<ProductClassification, Integer> {
	Optional<ProductClassification> findOneByClassId(int classId);

}
