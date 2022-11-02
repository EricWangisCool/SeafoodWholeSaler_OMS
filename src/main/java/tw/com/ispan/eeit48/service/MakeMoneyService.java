package tw.com.ispan.eeit48.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.OrderDetailsBean;
import tw.com.ispan.eeit48.domain.OrdersBean;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.domain.SortComparator;
import tw.com.ispan.eeit48.domain.View_product_order_orderdetailsBean;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.repository.OrderDetailsRepositrory;
import tw.com.ispan.eeit48.repository.OrdersRepository;
import tw.com.ispan.eeit48.repository.ProductRepository;
import tw.com.ispan.eeit48.repository.View_product_order_orderdetailsRepository;

@Service
@Transactional
public class MakeMoneyService {
	@Autowired
	private View_product_order_orderdetailsRepository view_product_order_orderdetailsRepository;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrderDetailsRepositrory orderDetailsRepositrory;
	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private ProductRepository productRepository;


	public String ShowAll(Integer accountid, String ordertime, String completeordertime,int buyerida) throws ParseException {

		JSONObject[] obj;
		JSONArray ListShowAll = new JSONArray();
		ListShowAll.clear();
		String[] orderid;
		int[] buyerid;
		String[] orderidlist;
		String[] companyname;
		String[] address;
		String[] taxid;
		String[] contactperson;
		String[] fax;
		String[] mobile;
		int[] productid;
		int[] productid2;
		String[] productnamespec;
		String[] ordertime1;
		int[] orderqty;
		int[] unitdealprice;
		int[] detail;
		int[]total;
		Date DateofOrdertime  = new SimpleDateFormat("yyyy-MM-dd").parse(ordertime);
		Date DateofCompleteOrdertime = new SimpleDateFormat("yyyy-MM-dd").parse(completeordertime);

		List<OrdersBean> ob = ordersRepository
				.findAllBySelleridAndBuyeridAndOrderstatusAndOrdertimeBetweenAndCompleteordertimeBetween(accountid,buyerida,6, DateofOrdertime,DateofCompleteOrdertime, DateofOrdertime, DateofCompleteOrdertime); // 把所有完成的訂單放進去
		//找到特定時間內買家已完成的訂單
		
		JSONArray ListofOrderid = new JSONArray();
		if (ob != null) {
			for (OrdersBean bean : ob) {
				if (bean != null) {
					ListofOrderid.put(bean.toJsonObject());
				}
			}

		}
		
		int length = ListofOrderid.length();
		orderid = new String[length];
		buyerid = new int[length];
		for (int i = 0; i < ListofOrderid.length(); i++) {
			orderid[i] = (String) ListofOrderid.getJSONObject(i).get("orderid");
			buyerid[i] = (int) ListofOrderid.getJSONObject(i).get("buyerid");
		}
		
		
		JSONArray ListofOrderdetail = new JSONArray();
		ListofOrderdetail.clear();
		for (int b = 0; b < ListofOrderid.length(); b++) {
			List<OrderDetailsBean> od = orderDetailsRepositrory.findAllByOrderid(orderid[b]); // 找到所有訂單的細項
			if (ob != null) {
				for (OrderDetailsBean bean : od) {
					if (bean != null) {
						ListofOrderdetail.put(bean.toJsonObject());
					}
				}

			}
		}
		int NumberofOderdatail = ListofOrderdetail.length();
		productid = new int[NumberofOderdatail];
		productnamespec = new String[NumberofOderdatail];
		companyname = new String[NumberofOderdatail];
		address = new String[NumberofOderdatail];
		taxid = new String[NumberofOderdatail];
		contactperson = new String[NumberofOderdatail];
		fax = new String[NumberofOderdatail];
		mobile = new String[NumberofOderdatail];
		ordertime1 = new String[NumberofOderdatail];
		orderidlist = new String[NumberofOderdatail];
		orderqty = new int[NumberofOderdatail];
		unitdealprice = new int[NumberofOderdatail];
		obj = new JSONObject[NumberofOderdatail];
		detail = new int[NumberofOderdatail];
		 fax= new String[NumberofOderdatail];
		 total=new int[NumberofOderdatail];
		 productid2=new int[NumberofOderdatail];
		for (int z = 0; z < ListofOrderdetail.length(); z++) {
			productid[z] = (int) ListofOrderdetail.getJSONObject(z).get("sellerproductid");
		}

		JSONArray ListofBuyer = new JSONArray();
		ListofBuyer.clear();

		List<AccountsBean> oa = accountsRepository.findAllByAccountid(buyerid[0]); // 找到買家的資料
		if (ob != null) {
			for (AccountsBean bean : oa) {
				if (bean != null) {
					ListofBuyer.put(bean.toJsonObject());
				}
			}
		}

		JSONArray ListofProduct = new JSONArray();
		ListofProduct.clear();
		for (int v = 0; v < ListofOrderdetail.length(); v++) {

			List<ProductBean> op = productRepository.findAllByProductid(productid[v]); // 找到商品的資料
			if (ob != null) {
				for (ProductBean bean : op) {
					if (bean != null) {
						ListofProduct.put(bean.toJsonObject());
					}
				}

			}

		}
		for (int i = 0; i < ListofOrderdetail.length(); i++) {
			productnamespec[i] = ListofProduct.getJSONObject(i).getString("productnamespec");
		}
		
		JSONArray ListofFindOrder = new JSONArray();
		ListofFindOrder.clear();
		for (int i = 0; i < ListofOrderid.length(); i++) {

			List<View_product_order_orderdetailsBean> vb = view_product_order_orderdetailsRepository
					.findAllByOrderid(orderid[i]); // 找到單筆訂單的商品資料
			if (vb != null) {
				for (View_product_order_orderdetailsBean bean : vb) {
					if (bean != null) {
						ListofFindOrder.put(bean.toJsonObject());
					}
				}

			}
		}
		
		
		
      for (int f=0;f<ListofOrderdetail.length();f++) {
		int a=(int) ListofOrderdetail.getJSONObject(f).get("unitdealprice");
		int b=(int) ListofOrderdetail.getJSONObject(f).get("orderqty");
		int c=a*b;
		detail[f]=c;	
		
      }
      for (int f=1;f<(ListofOrderdetail.length());f++) {
    	  int a=detail[0];
    	  
    	  total[0]=a;
    	  int b=detail[f];
    	  
    	  
    	  total[f]=b+ total[f-1];
    	  
    	  
      }
     
    
      
      
		for (int s = 0; s < ListofOrderdetail.length(); s++) {
			productid2[s]=(int) ListofFindOrder.getJSONObject(s).get("productid");
			companyname[s] = (String) ListofBuyer.getJSONObject(0).get("companyname");
			address[s] = (String) ListofBuyer.getJSONObject(0).get("address");
			taxid[s] = (String) ListofBuyer.getJSONObject(0).get("taxid");
			contactperson[s] = (String) ListofBuyer.getJSONObject(0).get("contactperson");
			fax[s] = (String) ListofBuyer.getJSONObject(0).get("fax");
			mobile[s] = (String) ListofBuyer.getJSONObject(0).get("mobile");
			ordertime1[s] = (String) ListofFindOrder.getJSONObject(s).get("ordertime");
			orderqty[s] = (int) ListofOrderdetail.getJSONObject(s).get("orderqty");
			unitdealprice[s] = (int) ListofOrderdetail.getJSONObject(s).get("unitdealprice");
			productnamespec[s] = (String) ListofProduct.getJSONObject(s).get("productnamespec");
			orderidlist[s] = (String) ListofFindOrder.getJSONObject(s).get("orderid");
		}

		for (int s = 0; s < ListofOrderdetail.length(); s++) {
			String a=ordertime1[s];
			ordertime1[s]=a.substring(0,10);			
		}
		

		
		for (int i = 0; i < ListofOrderdetail.length(); i++) {
			obj[i] = new JSONObject();
		}
		for (int i = 0; i < ListofOrderdetail.length(); i++) {
			obj[i].put("orderid", orderidlist[i]);
			obj[i].put("productnamespec", productnamespec[i]);
			obj[i].put("orderqty", orderqty[i]);
			obj[i].put("unitdealprice", unitdealprice[i]);
			obj[i].put("ordertime", ordertime1[i]);
			obj[i].put("contactperson", contactperson[i]);
			obj[i].put("address", address[i]);
			obj[i].put("companyname", companyname[i]);
			obj[i].put("mobile", mobile[i]);
			obj[i].put("taxid", taxid[i]);
			obj[i].put("detail", detail[i]);
			obj[i].put("fax", fax[i]);
			obj[i].put("total", total[i]);
			obj[i].put("ordertime", ordertime1[i]);
			ListShowAll.put(obj[i]);
		}

		
		

		return ListShowAll.toString();

	}
}
