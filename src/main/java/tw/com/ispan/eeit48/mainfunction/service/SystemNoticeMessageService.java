package tw.com.ispan.eeit48.mainfunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainfunction.model.SystemNoticeMessageBean;
import tw.com.ispan.eeit48.mainfunction.repository.SystemNoticeMessageRepository;
import tw.com.ispan.eeit48.websocket.service.WebSocketService;
import java.util.List;

@Service
public class SystemNoticeMessageService {
	@Autowired
	private SystemNoticeMessageRepository systemNoticeMessageRepository;
	@Autowired
	private WebSocketService webSocketService;

	/**
	 * 儲存新訊息同時，如果該receiver在線上就更新最新資訊給他讀取
	 */
	public void saveNewMessage(String messageContent, int receiverId) {
		systemNoticeMessageRepository.save(new SystemNoticeMessageBean(null, receiverId, "N", messageContent));
		webSocketService.sendNoticeMessages(String.valueOf(receiverId));
	}

	/**
	 * 將未讀訊息更新為已讀
	 */
	public void updateMessagesToRead(List<Integer> messageIds) {
		List<SystemNoticeMessageBean> messages = systemNoticeMessageRepository.findAllByMessageIdIn(messageIds);
		messages.forEach(message -> message.setMessageRead("Y"));
		systemNoticeMessageRepository.saveAll(messages);
	}
}
