package tw.com.ispan.eeit48.websocket.job;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.websocket.common.NewsBean;
import tw.com.ispan.eeit48.websocket.common.WebsocketConsts;



@Component
public class RoutineJob {
	private static final Logger log = LogManager.getLogger(RoutineJob.class);
	@Autowired
    private SimpMessagingTemplate wsTemplate;
	@Autowired
	private SimpUserRegistry simpUserRegistry;
	@Autowired
	AccountsRepository accountsRepository;
	/*
	 * 每隔30秒 如果ws有在線使用者
	 * 就朝在線使用者發送訊息
	 */
	@Scheduled(cron = "0/30 * * * * ?")
    public void websocket() throws Exception {
		if(simpUserRegistry.getUserCount() !=0) {
        NewsBean news = new NewsBean(WebsocketConsts.NEWS_ANNOUNCE, "請盡快安排訂單#220101574出貨。", new Date());
        var users = simpUserRegistry.getUsers();
        StringBuilder onlineusers = new StringBuilder();

        for (SimpUser simpUser : users) {
        	wsTemplate.convertAndSendToUser(simpUser.getName(), "/PersonalNotify/Notify", news);
        	onlineusers.append( accountsRepository.findCompanynameByAccountid( Integer.valueOf(simpUser.getName()) ) +", ");
			}
        log.info("當前使用者人數: {} 人,使用者: {} 30秒發送一次消息", simpUserRegistry.getUserCount(),onlineusers.toString());
		}
    }
}
