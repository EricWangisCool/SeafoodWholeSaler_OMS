package tw.com.ispan.eeit48.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.service.PageService;
import tw.com.ispan.eeit48.service.AuthService;

@RestController
@RequestMapping(path = {"/views/page"})
public class PageApiController {
    @Autowired
    PageService pageService;
    @Autowired
    AuthService authService;
    @Autowired
    AccountsRepository accountsRepository;

    // 回傳使用者所有商品資訊, 包括已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
    @PostMapping
    public String returnPageInformation() throws Exception {
        int userId = authService.getCurrentUserId();

        // 如果使用者存在, 就製作JSON陣列等等裝他的產品資料
        if (userId > 0) {
            JSONArray list = new JSONArray();
            JSONObject obj = new JSONObject();
            JSONObject obj2 = new JSONObject();
            JSONObject obj3 = new JSONObject();
            JSONObject obj4 = new JSONObject();

            obj.put("pageList", pageService.returnPageList(userId));
            obj2.put("pageBuyList", pageService.returnPageBuyList(userId));
            obj3.put("pageSellList", pageService.returnPageSellList(userId));
            obj4.put("userCompanyName", accountsRepository.findCompanynameByAccountid(userId));

            list.put(obj);
            list.put(obj2);
            list.put(obj3);
            list.put(obj4);
            return list.toString();
        } else {
            System.out.println("UserId is null");
        }
        return "NG";
    }
}
