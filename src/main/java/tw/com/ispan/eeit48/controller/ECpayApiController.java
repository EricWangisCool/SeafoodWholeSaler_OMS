package tw.com.ispan.eeit48.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ecpay.payment.integration.ExampleAllInOne;


//登入帳號頁面
@RestController
@RequestMapping(path = { "/ecpay" })
public class ECpayApiController {

	@PostMapping
	public String Ecpay2() {
		JSONObject obj = new JSONObject(); 
		obj.put("key", ExampleAllInOne.genAioCheckOutALL());
		return obj.toString(); 
	}
}
