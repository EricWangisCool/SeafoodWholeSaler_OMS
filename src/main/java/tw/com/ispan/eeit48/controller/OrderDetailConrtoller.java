package tw.com.ispan.eeit48.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.OrdersBean;
import tw.com.ispan.eeit48.service.OrderDetailService;

@RestController
@RequestMapping(path = { "/views/ordersell" })
public class OrderDetailConrtoller {
	@Autowired
	OrderDetailService orderDetailService;

	@PostMapping
	public String ShowAll(HttpSession session) {
		AccountsBean user = (AccountsBean) session.getAttribute("user");
		Integer userAccountId = user.getAccountid();
		String result = "NG";

		if (userAccountId != null) {
			result = orderDetailService.ShowAll(userAccountId);
		}

		return result;
	}

	@PostMapping(path = { "/update" })
	public String Update(@RequestBody OrdersBean ordersBean, HttpSession session) throws Exception {
		AccountsBean user = (AccountsBean) session.getAttribute("user");
		Integer userAccountId = user.getAccountid();
		String result = "NG";

		if (userAccountId != null) {
			result = orderDetailService.Update(userAccountId, ordersBean);
		}
		return result;
	}
}
