package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.table.SystemNoticeMessage;

@Repository
public interface SystemNoticeMessageRepository extends JpaRepository<SystemNoticeMessage, Integer> {
	List<SystemNoticeMessage> findAllByReceiverIdOrderByMessageIdDesc(int receiverId);
	List<SystemNoticeMessage> findAllByMessageIdIn(List<Integer> messageIds);
}
