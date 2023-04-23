package tw.com.ispan.eeit48.mainfunction.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.service.MakeMoneyService;

@RestController
@RequestMapping(path = { "/views/statement" })
public class MakeMoneyController {
	@Autowired
    MakeMoneyService makeMoneyService;

	@PostMapping
	public String ShowAll(@RequestBody String body) {
		try {
			JSONObject jsonObject = new JSONObject(body);
			return makeMoneyService.ShowAll(
					(String) jsonObject.get("ordertime"),
					(String) jsonObject.get("completeordertime"),
					(int) jsonObject.get("buyerid")
			);
		} catch (Exception e) {
			return "NG";
		}
	}
}
