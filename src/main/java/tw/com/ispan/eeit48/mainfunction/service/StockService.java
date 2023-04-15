package tw.com.ispan.eeit48.mainfunction.service;

import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainfunction.model.ProductBean;
import tw.com.ispan.eeit48.mainfunction.model.SupplierProductForOwnerProductBean;
import tw.com.ispan.eeit48.mainfunction.repository.ProductRepository;
import tw.com.ispan.eeit48.mainfunction.repository.SupplierProductForOwnerProductRepository;

@Service
@Transactional
public class StockService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;

	public String insertNewStock(Integer accountId, ProductBean productBean,
			SupplierProductForOwnerProductBean supplierProductForOwnerProductBean) {
		Integer lastOldProductId = null, newProductId = null;
		
		// 找尋舊productId，如果找到，將就最新一筆productId+1作為newProductId，<否則就將(int)"accountId"+"00001">做為newProductId
		List<ProductBean> productBeans = productRepository.findAllByOwnerid(accountId);
		if (!productBeans.isEmpty() && productBeans != null) {
			for (ProductBean bean : productBeans) {
				lastOldProductId = bean.getProductid();
			}
			newProductId = lastOldProductId + 1;
		} else {
			newProductId = Integer.parseInt(String.format("%d%05d", accountId, 1));
		}
		// 有了newProductId就可以設值
		productBean.setProductid(newProductId);
		supplierProductForOwnerProductBean.setProductid(newProductId);

		// 新庫存保存成功就保存它的供應商資訊，都保存成功回傳OK，否則回傳NG
		if (productRepository.save(productBean) != null) {
			if (supplierProductForOwnerProductRepository.save(supplierProductForOwnerProductBean) != null) {
				return "OK";
			}
		}
		return "NG";
	}

	public String updateStock(JSONObject bodyObj) {
		Integer productId = (Integer) bodyObj.get("productid");
		if (productId != null) {
			ProductBean productBean = productRepository.findOneByProductid(productId);
			if (productBean != null) {
				productBean = this.setProductBean(bodyObj, productBean);
				if (productRepository.save(productBean) != null) {
					return "OK";
				}
			}
		}
		return "NG";
	}

	public String deleteStock(Integer productid) {
		productRepository.deleteByProductid(productid);
		return "OK";
	}

	public ProductBean setProductBean(JSONObject bodyObj, ProductBean productBean) {
		productBean.setStockqty((Integer) bodyObj.get("stockqty"));
		productBean.setUnitcost((Integer) bodyObj.get("unitcost"));
		productBean.setWarningqty((Integer) bodyObj.get("warningqty"));
		productBean.setSafeqty((Integer) bodyObj.get("safeqty"));
		productBean.setProductclassification((Integer) bodyObj.get("productclassification"));
		productBean.setOnshelf((Integer) bodyObj.get("onshelf"));
		productBean.setMinsellqty((Integer) bodyObj.get("minsellqty"));
		productBean.setUnitsellprice((Integer) bodyObj.get("unitsellprice"));
		productBean.setAutoorderconfirmfunctionstatus((Integer) bodyObj.get("autoorderconfirmfunctionstatus"));
		productBean.setReservedqty((Integer) bodyObj.get("reservedqty"));
		productBean.setProductdesc((String) bodyObj.get("productdesc"));
		productBean.setProductnamespec((String) bodyObj.get("productnamespec"));
		productBean.setAutoorderfunction((String) bodyObj.get("autoorderfunction"));
		productBean.setProductpic((String) bodyObj.get("productpic"));
		return productBean;
	}
}
