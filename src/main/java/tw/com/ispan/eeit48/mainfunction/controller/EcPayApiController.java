package tw.com.ispan.eeit48.mainfunction.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ecpay.payment.integration.ExampleAllInOne;

@RestController
@RequestMapping(path = { "/ecpay" })
public class EcPayApiController {

	@PostMapping
	public String ecPay() {
		JSONObject obj = new JSONObject(); 
		obj.put("key", ExampleAllInOne.genAioCheckOutALL());
		return obj.toString();
	}
}
