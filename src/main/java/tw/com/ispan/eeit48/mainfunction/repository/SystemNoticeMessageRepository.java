package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.SystemNoticeMessageBean;

@Repository
public interface SystemNoticeMessageRepository extends JpaRepository<SystemNoticeMessageBean, Integer> {
	List<SystemNoticeMessageBean> findAllByReceiverIdOrderByMessageIdDesc(int receiverId);
	List<SystemNoticeMessageBean> findAllByMessageIdIn(List<Integer> messageIds);
}
