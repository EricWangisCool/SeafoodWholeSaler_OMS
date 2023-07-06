package tw.com.ispan.eeit48.mainFunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainFunction.model.table.SystemNoticeMessage;
import tw.com.ispan.eeit48.mainFunction.repository.table.SystemNoticeMessageRepository;
import tw.com.ispan.eeit48.webSocket.service.WebSocketService;
import java.util.List;

@Service
public class NewsService {
	@Autowired
	private SystemNoticeMessageRepository systemNoticeMessageRepository;
	@Autowired
	private WebSocketService webSocketService;

	/**
	 * 儲存新訊息同時，如果該receiver在線上就更新最新資訊給他讀取
	 */
	public void saveNewMessage(String messageContent, int receiverId) {
		systemNoticeMessageRepository.save(new SystemNoticeMessage(null, receiverId, "N", messageContent));
		webSocketService.sendNoticeMessages(String.valueOf(receiverId));
	}

	/**
	 * 將未讀訊息更新為已讀
	 */
	public void updateMessagesToRead(List<Integer> messageIds) {
		List<SystemNoticeMessage> messages = systemNoticeMessageRepository.findAllByMessageIdIn(messageIds);
		messages.forEach(message -> message.setMessageRead("Y"));
		systemNoticeMessageRepository.saveAll(messages);
	}
}
