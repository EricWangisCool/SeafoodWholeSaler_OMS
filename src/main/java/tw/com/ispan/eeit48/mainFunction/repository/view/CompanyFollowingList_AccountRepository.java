package tw.com.ispan.eeit48.mainFunction.repository.view;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainFunction.model.view.CompanyFollowingList_Account;

@Repository
public interface CompanyFollowingList_AccountRepository extends PagingAndSortingRepository<CompanyFollowingList_Account, Integer> {

	List<CompanyFollowingList_Account> findAllByBuyerId(int userId);

	List<CompanyFollowingList_Account> findAllByCompanyName(String companyName);

}
