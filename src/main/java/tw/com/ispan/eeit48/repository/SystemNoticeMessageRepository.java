package tw.com.ispan.eeit48.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.domain.SystemNoticeMessageBean;

@Repository
public interface SystemNoticeMessageRepository extends JpaRepository<SystemNoticeMessageBean, Integer> {
	
	List<SystemNoticeMessageBean> findAllByReceiveridOrderByMessageidDesc(int receiverid);

	List<SystemNoticeMessageBean> findAllByReceiveridAndMessageread(int receiverid, int messageread);

	SystemNoticeMessageBean findFirstByOrderByMessageidDesc();

	SystemNoticeMessageBean findOneByMessageid(Integer messageId);

}
