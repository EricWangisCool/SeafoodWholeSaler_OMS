package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.view.CompanyFollowingList_Account;

@Repository
public interface View_companyfollowinglist_accountsRepository extends PagingAndSortingRepository<CompanyFollowingList_Account, Integer> {

	List<CompanyFollowingList_Account> findAllByBuyerId(int userId);

	List<CompanyFollowingList_Account> findAllByCompanyName(String companyName);

}
