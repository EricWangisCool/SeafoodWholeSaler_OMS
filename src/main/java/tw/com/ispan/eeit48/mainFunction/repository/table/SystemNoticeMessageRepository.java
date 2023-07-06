package tw.com.ispan.eeit48.mainFunction.repository.table;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainFunction.model.table.SystemNoticeMessage;

@Repository
public interface SystemNoticeMessageRepository extends JpaRepository<SystemNoticeMessage, Integer> {
	List<SystemNoticeMessage> findAllByReceiverIdOrderByMessageIdDesc(int receiverId);
	List<SystemNoticeMessage> findAllByMessageIdIn(List<Integer> messageIds);
}
