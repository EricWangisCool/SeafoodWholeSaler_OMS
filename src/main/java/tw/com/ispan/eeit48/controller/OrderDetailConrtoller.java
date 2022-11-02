package tw.com.ispan.eeit48.controller;

import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.OrdersBean;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.service.OrderDetailService;

@RestController
@RequestMapping(path = { "/views/ordersell" })
public class OrderDetailConrtoller {
	@Autowired
	OrderDetailService orderDetailService;

	@PostMapping

	
	public String ShowAll(@RequestBody  ProductBean dataRequest, HttpSession session) {
		AccountsBean beanofaccount=(AccountsBean) session.getAttribute("user");
		
		String beans=orderDetailService.ShowAll(beanofaccount.getAccountid());
		System.out.println("beans"+beans);
		return beans;
		
	}
	
	@PostMapping(path= {"/update"})
	
	
	public String Update(@RequestBody  OrdersBean dataRequest, HttpSession session) {
		AccountsBean beanofaccount=(AccountsBean) session.getAttribute("user");
		
		String beans=orderDetailService.Update(beanofaccount.getAccountid(),dataRequest.getOrderid(),dataRequest.getOrderstatus()
				,dataRequest.getBuyerid(),dataRequest.getSellerid(),dataRequest.getPaymentterms(),dataRequest.getAcceptordertime()
				,dataRequest.getExporttime(),dataRequest.getArriveordertime(),
				dataRequest.getCompleteordertime(),dataRequest.getCancelordertime(),dataRequest.getOrdertime());
		
		
		return beans;
		
	}
}
