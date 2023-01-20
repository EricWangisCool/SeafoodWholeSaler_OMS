package tw.com.ispan.eeit48.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;
import org.json.JSONObject;

@Entity
@Immutable
@IdClass(SupplierProductForOwnerProductPK.class)
@DynamicUpdate
@Table(name = "supplierproductforownerproduct")
public class SupplierProductForOwnerProductBean implements Serializable {
	@Id
	private Integer productid;
	@Id
	private Integer supplierproductid;
	private Integer supplierid;

	@Override
	public String toString() {
		return "SupplierProductForOwnerProductBean [productid=" + productid + ", supplierproductid=" + supplierproductid
				+ ", supplierid=" + supplierid + "]";
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public Integer getSupplierproductid() {
		return supplierproductid;
	}

	public void setSupplierproductid(Integer supplierproductid) {
		this.supplierproductid = supplierproductid;
	}

	public Integer getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Integer supplierid) {
		this.supplierid = supplierid;
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("productid", productid == null ? "null" : productid);
		obj.put("supplierproductid", supplierproductid == null ? "null" : supplierproductid);
		obj.put("supplierid", supplierid == null ? "null" : supplierid);
		return obj;
	}

}
