package tw.com.ispan.eeit48.webSocket.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.webSocket.service.WebSocketService;

/**
 *  WebSocket消息接口
 */
@RestController
public class WebsocketController {
	private static final Logger log = LogManager.getLogger(WebsocketController.class);
	@Autowired
	WebSocketService webSocketService;

	@MessageMapping("/PersonalNotify/GetMessages")
    public void getMessages(Authentication authentication) {
		try {
			String userId = (String) authentication.getPrincipal();
			webSocketService.sendNoticeMessages(userId);
		} catch (Exception e) {
			log.info(e.toString());
		}
    }
}
