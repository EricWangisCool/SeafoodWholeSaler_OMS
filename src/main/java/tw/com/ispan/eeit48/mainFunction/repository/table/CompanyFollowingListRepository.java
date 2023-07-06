package tw.com.ispan.eeit48.mainFunction.repository.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainFunction.model.table.CompanyFollowingList;

@Repository
public interface CompanyFollowingListRepository extends JpaRepository<CompanyFollowingList, Integer> {

}
