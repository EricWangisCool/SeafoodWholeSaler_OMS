package tw.com.ispan.eeit48.controller;

import java.text.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.service.MakeMoneyService;
import tw.com.ispan.eeit48.service.AuthService;

@RestController
@RequestMapping(path = { "/views/statement" })
public class MakeMoneyController {
	@Autowired
	MakeMoneyService makeMoneyService;
	@Autowired
	AuthService authService;

	@PostMapping
	public String ShowAll(@RequestBody String body) throws ParseException {
		int userId = authService.getCurrentUserId();
		JSONObject jsonObject = new JSONObject(body);
		String ordertime = (String) jsonObject.get("ordertime");
		String completeordertime = (String) jsonObject.get("completeordertime");
		int buyerid = (int) jsonObject.get("buyerid");
		String beans = makeMoneyService.ShowAll(userId, ordertime, completeordertime, buyerid);
		return beans;
	}
}
