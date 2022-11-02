package tw.com.ispan.eeit48.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.domain.View_companyfollowinglist_accountsBean;

@Repository
@Transactional
public interface View_companyfollowinglist_accountsRepository
		extends PagingAndSortingRepository<View_companyfollowinglist_accountsBean, Integer> {
	List<View_companyfollowinglist_accountsBean> findAllByBuyerid(int userid);

	List<View_companyfollowinglist_accountsBean> findAllByCompanyname(String companyname);
}
