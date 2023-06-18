package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.View_CompanyFollowingList_Accounts_Bean;

@Repository
public interface View_companyfollowinglist_accountsRepository extends PagingAndSortingRepository<View_CompanyFollowingList_Accounts_Bean, Integer> {

	List<View_CompanyFollowingList_Accounts_Bean> findAllByBuyerid(int userid);

	List<View_CompanyFollowingList_Accounts_Bean> findAllByCompanyname(String companyname);

}
