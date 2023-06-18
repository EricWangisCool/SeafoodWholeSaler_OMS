package tw.com.ispan.eeit48.mainfunction.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@IdClass(SupplierProductForOwnerProductPK.class)
@DynamicUpdate
@Table(name = "t_supplier_product_for_owner_product")
public class SupplierProductForOwnerProductBean implements Serializable {
	@Id
	private Integer productId;
	@Id
	private Integer supplierProductId;
	private Integer supplierId;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getSupplierProductId() {
		return supplierProductId;
	}
	public void setSupplierProductId(Integer supplierProductId) {
		this.supplierProductId = supplierProductId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}


}
