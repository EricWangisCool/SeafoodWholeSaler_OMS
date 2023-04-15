package tw.com.ispan.eeit48.mainfunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.service.PageService;
import tw.com.ispan.eeit48.mainfunction.service.AuthService;

@RestController
@RequestMapping(path = {"/views/page"})
public class PageApiController {
    @Autowired
    private PageService pageService;
    @Autowired
    private AuthService authService;

    /**
     * 回傳使用者所有商品資訊, 包括已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
     */
    @PostMapping
    public String returnPageInformation() {
        int userId = authService.getCurrentUserId();
        try {
            return pageService.getPageInformation(userId);
        } catch (Exception e) {
            return "NG";
        }
    }
}
