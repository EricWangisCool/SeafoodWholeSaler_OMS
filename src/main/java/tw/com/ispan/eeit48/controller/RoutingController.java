package tw.com.ispan.eeit48.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoutingController {
	// 輸入http://localhost:8080直接導到/index.html
	@RequestMapping(path = { "/" })
	public String indexPage() {
		return "/index.html";
	}
}
