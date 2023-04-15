package tw.com.ispan.eeit48.mainfunction.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.service.SystemNoticeMessageService;

@RestController
@RequestMapping(path = {"/views/news"})
public class NewsApiController {
    @Autowired
    SystemNoticeMessageService systemNoticeMessageService;

    @PostMapping(path = ("/update"))
    public String updateUserMessages(@RequestBody String body) {
        try {
            List<Integer> messageIds = new JSONArray(body).toList()
                    .stream().map(Integer.class::cast)
                    .collect(Collectors.toList());

            systemNoticeMessageService.updateMessagesToRead(messageIds);
        } catch (Exception e) {
            return "NG";
        }
        return "OK";
    }
}
