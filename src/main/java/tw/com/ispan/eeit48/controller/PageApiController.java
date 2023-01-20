package tw.com.ispan.eeit48.controller;

import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.service.PageService;
import tw.com.ispan.eeit48.service.ProductService;

@RestController
@RequestMapping(path = { "/views/page" })
public class PageApiController {
	@Autowired
	ProductService productService;
	@Autowired
	PageService pageService;

	// 回傳使用者所有商品資訊, 包括已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
	@PostMapping
	public String returnPageInformation(HttpSession session) throws Exception {
		// 從session裡撈出使用者id
		if (session.getAttribute("user") != null) {
			AccountsBean user = (AccountsBean) session.getAttribute("user");
			int userId = user.getAccountid();
			// 如果使用者存在, 就製作JSON陣列等等裝他的產品資料
			if (userId > 0) {
				JSONArray list = new JSONArray();
				JSONObject obj = new JSONObject();
				JSONObject obj2 = new JSONObject();
				JSONObject obj3 = new JSONObject();

				obj.put("pageList", pageService.returnPageList(userId));
				obj2.put("pageBuyList", pageService.returnPageBuyList(userId));
				obj3.put("pageSellList", pageService.returnPageSellList(userId));

				list.put(obj);
				list.put(obj2);
				list.put(obj3);
				return list.toString();
			} else {
				System.out.println("UserId is null");
			}
		}
		return "NG";
	}
}
