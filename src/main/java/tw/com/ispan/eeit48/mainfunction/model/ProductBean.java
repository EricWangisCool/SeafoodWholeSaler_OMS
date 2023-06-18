package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_product")
public class ProductBean {
	@Id
	private Integer productId;
	@Column(columnDefinition = "longtext")
	private String productPic;
	private Integer productClassification;
	private Integer unitCost;
	private Integer stockQty;
	private Integer warningQty;
	@Column(columnDefinition = "char")
	private String autoOrderFunction;
	private Integer safeQty;
	private Integer unitSellPrice;
	private Integer minSellQty;
	@Column(columnDefinition = "char")
	private String productDesc;
	private Integer onShelf;
	private Integer autoOrderConfirmFunctionStatus;
	private Integer reservedQty;
	private Integer ownerId;
	@Column(columnDefinition = "char")
	private String productNameSpec;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	public Integer getProductClassification() {
		return productClassification;
	}
	public void setProductClassification(Integer productClassification) {
		this.productClassification = productClassification;
	}
	public Integer getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Integer unitCost) {
		this.unitCost = unitCost;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}
	public Integer getWarningQty() {
		return warningQty;
	}
	public void setWarningQty(Integer warningQty) {
		this.warningQty = warningQty;
	}
	public String getAutoOrderFunction() {
		return autoOrderFunction;
	}
	public void setAutoOrderFunction(String autoOrderFunction) {
		this.autoOrderFunction = autoOrderFunction;
	}
	public Integer getSafeQty() {
		return safeQty;
	}
	public void setSafeQty(Integer safeQty) {
		this.safeQty = safeQty;
	}
	public Integer getUnitSellPrice() {
		return unitSellPrice;
	}
	public void setUnitSellPrice(Integer unitSellPrice) {
		this.unitSellPrice = unitSellPrice;
	}
	public Integer getMinSellQty() {
		return minSellQty;
	}
	public void setMinSellQty(Integer minSellQty) {
		this.minSellQty = minSellQty;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public Integer getOnShelf() {
		return onShelf;
	}
	public void setOnShelf(Integer onShelf) {
		this.onShelf = onShelf;
	}
	public Integer getAutoOrderConfirmFunctionStatus() {
		return autoOrderConfirmFunctionStatus;
	}
	public void setAutoOrderConfirmFunctionStatus(Integer autoOrderConfirmFunctionStatus) {
		this.autoOrderConfirmFunctionStatus = autoOrderConfirmFunctionStatus;
	}
	public Integer getReservedQty() {
		return reservedQty;
	}
	public void setReservedQty(Integer reservedQty) {
		this.reservedQty = reservedQty;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public String getProductNameSpec() {
		return productNameSpec;
	}
	public void setProductNameSpec(String productNameSpec) {
		this.productNameSpec = productNameSpec;
	}

}
