package tw.com.ispan.eeit48.mainFunction.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.mainFunction.service.NewsService;

@RestController
@RequestMapping(path = {"/news"})
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PutMapping
    public void updateUserMessages(@RequestBody List<Integer> messageIds) {
        try {
            newsService.updateMessagesToRead(messageIds);
        } catch (Exception e) {

        }
    }
}
