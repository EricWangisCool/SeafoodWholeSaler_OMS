package tw.com.ispan.eeit48.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.service.DataAnalyzeService;

@RestController
@RequestMapping(path = { "views/analyze" })
public class DataAnalyzeController {
	@Autowired
	DataAnalyzeService analyzeService;

	@PostMapping
	public String ShowAll(@RequestBody String body, HttpSession session) throws ParseException {
		AccountsBean beanofaccount = (AccountsBean) session.getAttribute("user");

		JSONObject jsonObject = new JSONObject(body);
		String ordertime = (String) jsonObject.get("ordertime");
		String completeordertime = (String) jsonObject.get("completeordertime");
		String beans = analyzeService.ShowAll(beanofaccount.getAccountid(), ordertime, completeordertime);
		return beans;
	}
}
