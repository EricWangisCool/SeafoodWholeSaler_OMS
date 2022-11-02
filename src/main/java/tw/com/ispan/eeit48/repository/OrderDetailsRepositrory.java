package tw.com.ispan.eeit48.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.domain.OrderDetailsBean;

@Repository
public interface OrderDetailsRepositrory extends JpaRepository<OrderDetailsBean, String> {

	List<OrderDetailsBean> findAllByOrderid(String orderid);

}
