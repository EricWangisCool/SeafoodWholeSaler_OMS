package tw.com.ispan.eeit48.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.model.ProductClassIficationBean;

@Repository
public interface ProductClassIficationRepository extends JpaRepository<ProductClassIficationBean, Integer> {

	List<ProductClassIficationBean> findAllByClassid(int i);

}
