package tw.com.ispan.eeit48.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.model.AccountsBean;

@Repository
public interface AccountsRepository extends JpaRepository<AccountsBean, Integer> {

	Optional<AccountsBean> findOneByaccount(String string);

	Optional<AccountsBean> findOneByEmail(String email);

	List<AccountsBean> findAllByAccountid(int i);

	List<AccountsBean> findAllByCompanyname(String companyname);

	AccountsBean findByaccount(String account);

	AccountsBean findOneByAccountid(int accountId);

	@Query(value = "SELECT companyname FROM accounts WHERE accountid = ?1", nativeQuery = true)
	String findCompanynameByAccountid(int accountId);

	@Query(value = "SELECT email FROM accounts WHERE accountid = ?1", nativeQuery = true)
	String findEmailByAccountid(int accountId);

}
