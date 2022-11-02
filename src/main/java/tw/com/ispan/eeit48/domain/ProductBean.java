package tw.com.ispan.eeit48.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "product")
public class ProductBean {

	@Id
	private Integer productid;
	@Column(columnDefinition = "longtext")
	private String productpic;
	private Integer productclassification;
	private Integer unitcost;
	private Integer stockqty;
	private Integer warningqty;
	@Column(columnDefinition = "char")
	private String autoorderfunction;
	private Integer safeqty;
	private Integer unitsellprice;
	private Integer minsellqty;
	@Column(columnDefinition = "char")
	private String productdesc;
	private Integer onshelf;
	private Integer autoorderconfirmfunctionstatus;
	private Integer reservedqty;
	private Integer ownerid;
	@Column(columnDefinition = "char")
	private String productnamespec;

	@Override
	public String toString() {
		return "ProductBean [productid=" + productid + ", productpic=" + productpic + ", productclassification="
				+ productclassification + ", unitcost=" + unitcost + ", stockqty=" + stockqty + ", warningqty="
				+ warningqty + ", autoorderfunction=" + autoorderfunction + ", safeqty=" + safeqty + ", unitsellprice="
				+ unitsellprice + ", minsellqty=" + minsellqty + ", productdesc=" + productdesc + ", onshelf=" + onshelf
				+ ", autoorderconfirmfunctionstatus=" + autoorderconfirmfunctionstatus + ", reservedqty=" + reservedqty
				+ ", ownerid=" + ownerid + ", productnamespec=" + productnamespec + "]";
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public String getProductpic() {
		return productpic;
	}

	public void setProductpic(String productpic) {
		this.productpic = productpic;
	}

	public Integer getProductclassification() {
		return productclassification;
	}

	public void setProductclassification(Integer productclassification) {
		this.productclassification = productclassification;
	}

	public Integer getUnitcost() {
		return unitcost;
	}

	public void setUnitcost(Integer unitcost) {
		this.unitcost = unitcost;
	}

	public Integer getStockqty() {
		return stockqty;
	}

	public void setStockqty(Integer stockqty) {
		this.stockqty = stockqty;
	}

	public Integer getWarningqty() {
		return warningqty;
	}

	public void setWarningqty(Integer warningqty) {
		this.warningqty = warningqty;
	}

	public String getAutoorderfunction() {
		return autoorderfunction;
	}

	public void setAutoorderfunction(String autoorderfunction) {
		this.autoorderfunction = autoorderfunction;
	}

	public Integer getSafeqty() {
		return safeqty;
	}

	public void setSafeqty(Integer safeqty) {
		this.safeqty = safeqty;
	}

	public Integer getUnitsellprice() {
		return unitsellprice;
	}

	public void setUnitsellprice(Integer unitsellprice) {
		this.unitsellprice = unitsellprice;
	}

	public Integer getMinsellqty() {
		return minsellqty;
	}

	public void setMinsellqty(Integer minsellqty) {
		this.minsellqty = minsellqty;
	}

	public String getProductdesc() {
		return productdesc;
	}

	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}

	public Integer getOnshelf() {
		return onshelf;
	}

	public void setOnshelf(Integer onshelf) {
		this.onshelf = onshelf;
	}

	public Integer getAutoorderconfirmfunctionstatus() {
		return autoorderconfirmfunctionstatus;
	}

	public void setAutoorderconfirmfunctionstatus(Integer autoorderconfirmfunctionstatus) {
		this.autoorderconfirmfunctionstatus = autoorderconfirmfunctionstatus;
	}

	public Integer getReservedqty() {
		return reservedqty;
	}

	public void setReservedqty(Integer reservedqty) {
		this.reservedqty = reservedqty;
	}

	public Integer getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Integer ownerid) {
		this.ownerid = ownerid;
	}

	public String getProductnamespec() {
		return productnamespec;
	}

	public void setProductnamespec(String productnamespec) {
		this.productnamespec = productnamespec;
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("productid", productid);
		obj.put("productpic", productpic == null ? "null" : productpic);
		obj.put("productclassification", productclassification);
		obj.put("unitcost", unitcost);
		obj.put("stockqty", stockqty);
		obj.put("warningqty", warningqty);
		obj.put("autoorderfunction", autoorderfunction);
		obj.put("safeqty", safeqty);
		obj.put("unitsellprice", unitsellprice);
		obj.put("minsellqty", minsellqty);
		obj.put("productdesc", productdesc);
		obj.put("onshelf", onshelf);
		obj.put("autoorderconfirmfunctionstatus", autoorderconfirmfunctionstatus);
		obj.put("reservedqty", reservedqty == null ? "null" : reservedqty);
		obj.put("ownerid", ownerid);
		obj.put("productnamespec", productnamespec);
		return obj;
	}
}
