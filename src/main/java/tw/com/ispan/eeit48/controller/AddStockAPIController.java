package tw.com.ispan.eeit48.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.domain.SupplierProductForOwnerProductBean;
import tw.com.ispan.eeit48.service.SetNewStockService;

@RestController
@RequestMapping(path = { "/views/addstock" })
public class AddStockAPIController {
	@Autowired
	SetNewStockService setNewStockService;

	@PostMapping(path = { "/insert" })
	public String InsertNewProduct(@RequestBody String body, HttpSession session) {

		// List<SupplierProductForOwnerProductBean>
		// beans2=setNewStockService.InsertNewstock2(dataRequest2.getProductid(),dataRequest2.getSupplierid(),dataRequest2.getSupplierproductid());
		AccountsBean beanofaccount = (AccountsBean) session.getAttribute("user");
		JSONObject jsonObject = new JSONObject(body);
		ProductBean newProductBean = new ProductBean();
		SupplierProductForOwnerProductBean newSupplierProductForOwnerProductBean = new SupplierProductForOwnerProductBean();

		int stockqty = (Integer) jsonObject.get("stockqty");
		int unitcost = (Integer) jsonObject.get("unitcost");
		int warningqty = (Integer) jsonObject.get("warningqty");
		int safeqty = (Integer) jsonObject.get("safeqty");
		int productclassification = (Integer) jsonObject.get("productclassification");
		int onshelf = (Integer) jsonObject.get("onshelf");
		int minsellqty = (Integer) jsonObject.get("minsellqty");
		int unitsellprice = (Integer) jsonObject.get("unitsellprice");
		int autoorderconfirmfunctionstatus = (Integer) jsonObject.get("autoorderconfirmfunctionstatus");
		int reservedqty = (Integer) jsonObject.get("reservedqty");
		String productdesc = (String) jsonObject.get("productdesc");
		String productnamespec = (String) jsonObject.get("productnamespec");
		String autoorderfunction = (String) jsonObject.get("autoorderfunction");
		int supplierid = (Integer) jsonObject.get("supplierid");
		String productpic = (String) jsonObject.get("productpic");
		int supplierproductid = (Integer) jsonObject.get("supplierproductid");
		String beans = setNewStockService.InsertNewstock(beanofaccount.getAccountid(), stockqty, supplierid,
				productdesc, unitcost, warningqty, safeqty, productclassification, productpic, onshelf, minsellqty,
				unitsellprice, productnamespec, autoorderconfirmfunctionstatus, autoorderfunction, reservedqty,
				supplierproductid);

//				dataRequest.getProductdesc(),dataRequest.getUnitcost(),dataRequest.getWarningqty(),
//				dataRequest.getSafeqty(),dataRequest.getProductclassification(),dataRequest.getProductpic(),
//				dataRequest.getOnshelf(),dataRequest.getMinsellqty(),dataRequest.getUnitsellprice(),
//				dataRequest.getProductnamespec(),dataRequest.getAutoorderconfirmfunctionstatus(),
//				dataRequest.getAutoorderfunction(),dataRequest.getReservedqty(),beanofaccount.getAccountid()
//				
//		List<SupplierProductForOwnerProductBean> InsertNewstock2=setNewStockService.InsertNewstock(beanofaccount.getAccountid(),a,b){
//			
//		}
		return beans;
	}

	@PostMapping(path = { "/update" })
	public String UpdateProduct(@RequestBody String body, HttpSession session) {

		AccountsBean beanofaccount = (AccountsBean) session.getAttribute("user");
		JSONObject jsonObject = new JSONObject(body);
		ProductBean newProductBean = new ProductBean();
		SupplierProductForOwnerProductBean newSupplierProductForOwnerProductBean = new SupplierProductForOwnerProductBean();
		int stockqty = (Integer) jsonObject.get("stockqty");
		int unitcost = (Integer) jsonObject.get("unitcost");
		int warningqty = (Integer) jsonObject.get("warningqty");
		int safeqty = (Integer) jsonObject.get("safeqty");
		int productclassification = (Integer) jsonObject.get("productclassification");
		int onshelf = (Integer) jsonObject.get("onshelf");
		int minsellqty = (Integer) jsonObject.get("minsellqty");
		int unitsellprice = (Integer) jsonObject.get("unitsellprice");
		int autoorderconfirmfunctionstatus = (Integer) jsonObject.get("autoorderconfirmfunctionstatus");
		int reservedqty = (Integer) jsonObject.get("reservedqty");
		String productdesc = (String) jsonObject.get("productdesc");
		String productnamespec = (String) jsonObject.get("productnamespec");
		String autoorderfunction = (String) jsonObject.get("autoorderfunction");
		int supplierid = (Integer) jsonObject.get("supplierid");
		String productpic = (String) jsonObject.get("productpic");
		int supplierproductid = (Integer) jsonObject.get("supplierproductid");
		int productid = (Integer) jsonObject.get("productid");

		String beans = setNewStockService.Updatestock(beanofaccount.getAccountid(), stockqty, supplierid, productdesc,
				unitcost, warningqty, safeqty, productclassification, productpic, onshelf, minsellqty, unitsellprice,
				productnamespec, autoorderconfirmfunctionstatus, autoorderfunction, reservedqty, supplierproductid,
				productid);
		return beans;
	}

	@PostMapping(path = { "/delete" })
	public String DeleteProduct(@RequestBody ProductBean dataRequest, HttpSession session) {
		AccountsBean beanofaccount = (AccountsBean) session.getAttribute("user");
		String beans = setNewStockService.Deletestock(dataRequest.getProductid(), beanofaccount.getAccountid());
		return beans;
	}



}
