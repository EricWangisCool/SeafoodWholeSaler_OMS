package tw.com.ispan.eeit48.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.domain.CompanyFollowingListBean;

@Repository
public interface CompanyFollowingListRepository extends JpaRepository<CompanyFollowingListBean, Integer> {
	List<CompanyFollowingListBean> findAllByBuyerid(int buyerid);
}
