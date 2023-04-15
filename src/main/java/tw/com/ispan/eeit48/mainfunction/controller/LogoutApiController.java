package tw.com.ispan.eeit48.mainfunction.controller;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/logout" })
public class LogoutApiController {
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
