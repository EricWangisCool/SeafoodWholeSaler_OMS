package tw.com.ispan.eeit48.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.service.MakeMoneyService;
import tw.com.ispan.eeit48.domain.OrdersBean;

@RestController
@RequestMapping(path = { "/views/statement" })
public class MakeMoneyController {

	@Autowired
	MakeMoneyService makeMoneyService;
	
	@PostMapping
	
	public String ShowAll(@RequestBody  String body, HttpSession session) throws ParseException {
		AccountsBean beanofaccount=(AccountsBean) session.getAttribute("user");
		
		
		JSONObject jsonObject = new JSONObject(body);
		String ordertime=(String) jsonObject.get("ordertime");	
		String completeordertime=(String) jsonObject.get("completeordertime");
		int buyerid=(int) jsonObject.get("buyerid");
		String beans=makeMoneyService.ShowAll(beanofaccount.getAccountid() ,ordertime,completeordertime,buyerid) ;
		//System.out.println("beans"+beans);
		return beans;
		
	}
	
	
	
}
