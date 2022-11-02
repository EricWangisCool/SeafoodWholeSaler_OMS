package tw.com.ispan.eeit48.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.domain.OrderStatusBean;

@Repository
public interface OrderStatusRepository  extends JpaRepository<OrderStatusBean, Integer>{

}
