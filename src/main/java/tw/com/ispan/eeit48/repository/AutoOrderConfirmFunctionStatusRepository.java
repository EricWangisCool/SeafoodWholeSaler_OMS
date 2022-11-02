package tw.com.ispan.eeit48.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.domain.AutoOrderConfirmFunctionStatusBean;

@Repository
public interface AutoOrderConfirmFunctionStatusRepository
		extends JpaRepository<AutoOrderConfirmFunctionStatusBean, Integer> {

//	Optional<AccountsBean> findOneBystatusid(Integer integer);

//	AutoOrderConfirmFunctionStatusBean findOneBystatusid(Integer integer);

}
