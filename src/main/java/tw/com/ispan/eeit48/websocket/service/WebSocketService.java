package tw.com.ispan.eeit48.websocket.service;

import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainfunction.model.SystemNoticeMessageBean;
import tw.com.ispan.eeit48.mainfunction.repository.SystemNoticeMessageRepository;


@Service
public class WebSocketService {
	private static final Logger log = LogManager.getLogger(WebSocketService.class);
	private static final String DESTINATION = "/PersonalNotify/Notify";
	@Autowired
    private SimpMessagingTemplate wsTemplate;
	@Autowired
	private SimpUserRegistry simpUserRegistry;
	@Autowired
	private SystemNoticeMessageRepository systemNoticeMessageRepository;

	/**
	 * 如果使用者在線，就朝使用者發送通知訊息
	 */
    public void sendNoticeMessages(String userId) {
		if (simpUserRegistry.getUserCount() > 0) {
			Set<SimpUser> onlineUsers = simpUserRegistry.getUsers();

			boolean isUserOnline = onlineUsers.stream()
					.map(SimpUser::getName)
					.anyMatch(name -> name.contains(userId));

			if (isUserOnline) {
				List<SystemNoticeMessageBean> messages =
						systemNoticeMessageRepository.findAllByReceiverIdOrderByMessageIdDesc(Integer.parseInt(userId));

				wsTemplate.convertAndSendToUser(userId, DESTINATION, messages);
				log.info("發送最新消息給使用者，userId: {}", userId);
			}
		}
    }
}
