package tw.com.ispan.eeit48.controller;

import java.util.List;;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.model.SystemNoticeMessageBean;
import tw.com.ispan.eeit48.repository.SystemNoticeMessageRepository;
import tw.com.ispan.eeit48.service.AuthService;

@RestController
@RequestMapping(path = {"/views/news"})
public class NewsApiController {
    @Autowired
    SystemNoticeMessageRepository systemNoticeMessageRepository;
    @Autowired
    AuthService authService;

    @PostMapping
    public String retrunUserMessages() {
        int userId = authService.getCurrentUserId();
        if (userId > 0) {
            JSONArray list = new JSONArray();
            List<SystemNoticeMessageBean> beans = systemNoticeMessageRepository
                    .findAllByReceiveridOrderByMessageidDesc(userId);
            if (!beans.isEmpty()) {
                for (SystemNoticeMessageBean bean : beans) {
                    list.put(bean.toJsonObject());
                }
            }
            return list.toString();
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
