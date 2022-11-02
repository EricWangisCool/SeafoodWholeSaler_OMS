package tw.com.ispan.eeit48.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.SystemNoticeMessageBean;
import tw.com.ispan.eeit48.repository.SystemNoticeMessageRepository;

@RestController
@RequestMapping(path = { "/views/news" })
public class NewsApiController {
	@Autowired
	SystemNoticeMessageRepository systemNoticeMessageRepository;

	@PostMapping
	public String retrunUserMessages(HttpSession session) {
		// 從session裡撈出使用者id
		if (session.getAttribute("user") != null) {
			AccountsBean user = (AccountsBean) session.getAttribute("user");
			int userId = user.getAccountid();
			// 如果使用者存在, 就製作JSON陣列等等裝他的產品資料
			if (userId > 0) {
				JSONArray list = new JSONArray();
				List<SystemNoticeMessageBean> beans = systemNoticeMessageRepository.findAllByReceiveridOrderByMessageidDesc(userId);
				if (!beans.isEmpty()) {
					for (SystemNoticeMessageBean bean : beans) {
						list.put(bean.toJsonObject());
					}
				}
				return list.toString();
			}
		}
		return "NG";
	}

	@PostMapping(path = ("/update"))
	public String updateUserMessages(@RequestBody String body) {
		String result = "NG";
		JSONArray messagesIds = new JSONArray(body);

		for (Object messageId : messagesIds) {
			SystemNoticeMessageBean bean = systemNoticeMessageRepository.findOneByMessageid((Integer) messageId);
			bean.setMessageread("Y");
			if (systemNoticeMessageRepository.save(bean) != null) {
				result = "OK";
			} else {
				result = "NG";
			}
		}
		return result;
	}
}
