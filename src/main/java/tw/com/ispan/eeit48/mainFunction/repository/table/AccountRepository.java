package tw.com.ispan.eeit48.mainFunction.repository.table;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainFunction.model.table.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	Optional<Account> findOneByAccountId(int accountId);
	Optional<Account> findOneByAccount(String account);
	Optional<Account> findOneByEmail(String email);
	List<Account> findAllByCompanyName(String companyName);
	@Query(value = """
			SELECT company_name 
			FROM t_accounts 
			WHERE account_id = ?1
			""", nativeQuery = true)
	String findCompanyNameByAccountId(int accountId);

}
