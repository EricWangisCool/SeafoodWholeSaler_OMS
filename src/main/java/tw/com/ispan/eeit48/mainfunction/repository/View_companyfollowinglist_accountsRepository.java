package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.View_companyfollowinglist_accountsBean;

@Repository
public interface View_companyfollowinglist_accountsRepository extends PagingAndSortingRepository<View_companyfollowinglist_accountsBean, Integer> {

	List<View_companyfollowinglist_accountsBean> findAllByBuyerid(int userid);

	List<View_companyfollowinglist_accountsBean> findAllByCompanyname(String companyname);

}
