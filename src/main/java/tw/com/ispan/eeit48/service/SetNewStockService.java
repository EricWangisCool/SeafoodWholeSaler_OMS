package tw.com.ispan.eeit48.service;

import java.util.List;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.domain.SupplierProductForOwnerProductBean;
import tw.com.ispan.eeit48.repository.ProductRepository;
import tw.com.ispan.eeit48.repository.SupplierProductForOwnerProductRepository;

@Service
@Transactional
public class SetNewStockService {
	@Autowired
	private ProductRepository productrepository;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;

	public String InsertNewstock(Integer accountid, Integer stockqty, Integer supplierid, String productdesc,
			Integer unitcost, Integer warningqty, Integer safeqty, Integer productclassification, String productpic,
			Integer onshelf, Integer minsellqty, Integer unitsellprice, String productnamespec,
			Integer autoorderconfirmfunctionstatus, String autoorderfunction, Integer reservedqty,
			Integer supplierproductid) {
		List<ProductBean> beanofproduct = productrepository.findAllByOwnerid(accountid);
		int  productid= 0; // 拿來接productid
		if (beanofproduct != null) {
			JSONArray list = new JSONArray();
			for (ProductBean bean : beanofproduct) {
				list.put(bean.toJsonObject());
			}
			for (int i = 0; i < list.length(); i++) {

				int TempofProductid = list.getJSONObject(i).getInt("productid");
				productid = TempofProductid + 1; // 自動把productid+1號
			}
			
		}
		ProductBean Pb = new ProductBean(); // 設定庫存資訊
		Pb.setProductid(productid);
		Pb.setOwnerid(1);
		Pb.setStockqty(stockqty);
		Pb.setProductdesc(productdesc);
		Pb.setUnitcost(unitcost);
		Pb.setWarningqty(warningqty);
		Pb.setSafeqty(safeqty);
		Pb.setProductclassification(productclassification);
		Pb.setProductpic(productpic);
		Pb.setOnshelf(onshelf);
		Pb.setMinsellqty(minsellqty);
		Pb.setUnitsellprice(unitsellprice);
		Pb.setProductnamespec(productnamespec);
		Pb.setAutoorderconfirmfunctionstatus(autoorderconfirmfunctionstatus);
		Pb.setAutoorderfunction(autoorderfunction);
		Pb.setReservedqty(reservedqty);
		productrepository.save(Pb);
		SupplierProductForOwnerProductBean Sp = new SupplierProductForOwnerProductBean(); // 設定補貨對象
		Sp.setProductid(productid);
		Sp.setSupplierid(supplierid);
		Sp.setSupplierproductid(supplierproductid);
		supplierProductForOwnerProductRepository.save(Sp);

		return "OK";
	}

	public String Updatestock(Integer accountid, Integer stockqty, Integer supplierid, String productdesc,
			Integer unitcost, Integer warningqty, Integer safeqty, Integer productclassification, String productpic,
			Integer onshelf, Integer minsellqty, Integer unitsellprice, String productnamespec,
			Integer autoorderconfirmfunctionstatus, String autoorderfunction, Integer reservedqty,
			Integer supplierproductid, Integer productid) {

		ProductBean Pb = new ProductBean();
		Pb.setProductid(productid);
		Pb.setOwnerid(accountid);
		Pb.setStockqty(stockqty);
		Pb.setProductdesc(productdesc);
		Pb.setUnitcost(unitcost);
		Pb.setWarningqty(warningqty);
		Pb.setSafeqty(safeqty);
		Pb.setProductclassification(productclassification);
		Pb.setProductpic(productpic);
		Pb.setOnshelf(onshelf);
		Pb.setMinsellqty(minsellqty);
		Pb.setUnitsellprice(unitsellprice);
		Pb.setProductnamespec(productnamespec);
		Pb.setAutoorderconfirmfunctionstatus(autoorderconfirmfunctionstatus);
		Pb.setAutoorderfunction(autoorderfunction);
		Pb.setReservedqty(reservedqty);
		productrepository.save(Pb);
		return "OK";
	}

	public String Deletestock(Integer productid, Integer accountid) {
		//Delete(productid) ;
//		for(int i=0;i<ListofProduct.length();i++) {	
	productrepository.deleteByProductid(productid);  //刪除此商品	
//		}
		
		
		return "OK";

	}
	public void Delete(Integer productid) {
		List<SupplierProductForOwnerProductBean> beanofproduct = supplierProductForOwnerProductRepository.findAllBySupplierproductid(productid);
		JSONArray ListofProduct = new JSONArray();
		for (SupplierProductForOwnerProductBean bean : beanofproduct) {
			ListofProduct.put(bean.toJsonObject());
		}
		
		for(int i=0;i<ListofProduct.length();i++) {	
		supplierProductForOwnerProductRepository.deleteAllBySupplierproductidAndProductid(productid,(Integer)ListofProduct.getJSONObject(i).get("productid"));  //先刪掉供貨的上游關係
		
		}
	}
	

}
