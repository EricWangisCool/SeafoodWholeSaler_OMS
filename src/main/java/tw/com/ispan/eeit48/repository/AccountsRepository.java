package tw.com.ispan.eeit48.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.domain.AccountsBean;

@Repository
public interface AccountsRepository extends JpaRepository<AccountsBean, Integer> {

	Optional<AccountsBean> findOneByaccount(String string);
	
	Optional<AccountsBean> findOneByEmail(String email);

	AccountsBean findByaccount(String account);

	List<AccountsBean> findAllByAccountid(int i);

	@Query(value = "select companyname from accounts  where accountid = ?1", nativeQuery = true)
	String findCompanynameByAccountid(int sellerid);

	List<AccountsBean> findAllByCompanyname(String companyname);

}
