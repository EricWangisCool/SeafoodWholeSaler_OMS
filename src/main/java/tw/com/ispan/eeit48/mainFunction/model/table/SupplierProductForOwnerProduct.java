package tw.com.ispan.eeit48.mainFunction.model.table;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;
import tw.com.ispan.eeit48.mainFunction.model.table.pk.SupplierProductForOwnerProductPK;

@Entity
@Immutable
@IdClass(SupplierProductForOwnerProductPK.class)
@DynamicUpdate
@Table(name = "t_supplier_product_for_owner_product")
public class SupplierProductForOwnerProduct implements Serializable {
	@Id
	private String productId;
	@Id
	private String supplierProductId;
	private Integer supplierId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSupplierProductId() {
		return supplierProductId;
	}

	public void setSupplierProductId(String supplierProductId) {
		this.supplierProductId = supplierProductId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}


}
