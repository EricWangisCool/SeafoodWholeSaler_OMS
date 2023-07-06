package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainFunction.service.PageService;

@RestController
@RequestMapping(path = {"/page"})
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping
    public void getUserMainPageInfo() {
        try {
            pageService.getUserMainPageInfo();
        } catch (Exception e) {

        }
    }
}
