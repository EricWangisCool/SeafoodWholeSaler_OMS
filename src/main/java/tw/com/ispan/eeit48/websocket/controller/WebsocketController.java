package tw.com.ispan.eeit48.websocket.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.eeit48.model.AccountsBean;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.service.AuthService;
import tw.com.ispan.eeit48.websocket.common.NewsBean;



@RestController
public class WebsocketController {

	private static final Logger log = LogManager.getLogger(WebsocketController.class);
	
	@Autowired
    private SimpMessagingTemplate wsTemplate;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	AccountsRepository accountsRepository;

	/*
	 * 預留方法
	 * 前端發送至 /PersonalNotify/Send 後端接收
	 * 當接收訊息轉傳到 /PersonalNotify/Notify
	 */
	@MessageMapping("/PersonalNotify/Send")
    public void getUser(Authentication authentication,NewsBean newsBean) {
		// int AcccountId =Integer.valueOf(authentication.getName());
		// AccountsBean accountsBean = accountsRepository.findOneByAccountid(AcccountId);
		// authentication.getName() 轉傳給自己 
		wsTemplate.convertAndSendToUser(authentication.getName(), "/PersonalNotify/Notify", newsBean);

    }

	
}
