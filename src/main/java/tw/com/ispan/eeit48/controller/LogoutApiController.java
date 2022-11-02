package tw.com.ispan.eeit48.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.service.AccountsService;

//使用者登出帳號
@RestController
@RequestMapping(path = { "/logout" })
public class LogoutApiController {
	@Autowired
	AccountsService accountsService;

	@PostMapping
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		if (session.getAttribute("user") == null) {
			return "OK";
		} else {
			return "NG";
		}
	}
}
