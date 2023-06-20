package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.table.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {

	Optional<Account> findOneByaccount(String string);

	Optional<Account> findOneByEmail(String email);

	List<Account> findAllByAccountid(int i);

	List<Account> findAllByCompanyname(String companyname);

	Account findByaccount(String account);

	Account findOneByAccountid(int accountId);

	@Query(value = "SELECT companyname FROM accounts WHERE accountid = ?1", nativeQuery = true)
	String findCompanynameByAccountid(int accountId);

	@Query(value = "SELECT email FROM accounts WHERE accountid = ?1", nativeQuery = true)
	String findEmailByAccountid(int accountId);

}
