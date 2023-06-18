package tw.com.ispan.eeit48.mainfunction.model;

import java.io.Serializable;

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
@Table(name = "t_supplier_product_for_owner_product")
public class SupplierProductForOwnerProductBean implements Serializable {
	@Id
	private Integer product_id;
	@Id
	private Integer supplier_product_id;
	private Integer supplier_id;


	

	@Override
	public String toString() {
		return "SupplierProductForOwnerProductBean [product_id=" + product_id + ", supplier_product_id="
				+ supplier_product_id + ", supplier_id=" + supplier_id + "]";
	}

	public Integer getProductid() {
		return product_id;
	}

	public void setProductid(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getSupplierproductid() {
		return supplier_product_id;
	}

	public void setSupplierproductid(Integer supplier_product_id) {
		this.supplier_product_id = supplier_product_id;
	}

	public Integer getSupplierid() {
		return supplier_id;
	}

	public void setSupplierid(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
}
