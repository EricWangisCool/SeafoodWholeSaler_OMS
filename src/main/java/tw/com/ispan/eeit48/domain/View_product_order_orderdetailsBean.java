package tw.com.ispan.eeit48.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "view_product_order_orderdetails")
@IdClass(View_product_order_orderdetailsPK.class)
@DynamicUpdate
public class View_product_order_orderdetailsBean implements Serializable {
	@Id
	private Integer productid;
	@Column(columnDefinition = "char")
	private String orderid;
	private Integer orderstatus;
	private Integer stockqty;
	private Integer warningqty;
	private Integer orderqty;
	private Integer buyerid;
	private Integer ownerid;
	private Integer unitdealprice;
	private Integer safeqty;
	@Column(columnDefinition = "char")
	private String companyname;
	private Integer unitcost;
	@Column(columnDefinition = "char")
	private String paymentterms;
	@Column(columnDefinition = "char")
	private String productnamespec;
	@Column(columnDefinition = "char")
	private String deliveryorderid;
	@Column(columnDefinition = "char")
	private String deliveryorderremark;
	@Column(columnDefinition = "datetime")
	private String ordertime;
	@Column(columnDefinition = "datetime")
	private String acceptordertime;
	@Column(columnDefinition = "datetime")
	private String exporttime;
	@Column(columnDefinition = "datetime")
	private String arriveordertime;
	@Column(columnDefinition = "datetime")
	private String completeordertime;
	@Column(columnDefinition = "datetime")
	private String cancelordertime;
	@Column(columnDefinition = "char")
	private String autoorderfunction;
	private Integer unitsellprice;
	private Integer minsellqty;
	@Column(columnDefinition = "char")
	private String productdesc;
	private Integer onshelf;
	private Integer autoorderconfirmfunctionstatus;
	private Integer reservedqty;

	

	@Override
	public String toString() {
		return "View_product_order_orderdetailsBean [productid=" + productid + ", orderid=" + orderid + ", orderstatus="
				+ orderstatus + ", stockqty=" + stockqty + ", warningqty=" + warningqty + ", orderqty=" + orderqty
				+ ", buyerid=" + buyerid + ", ownerid=" + ownerid + ", unitdealprice=" + unitdealprice + ", safeqty="
				+ safeqty + ", companyname=" + companyname + ", unitcost=" + unitcost + ", paymentterms=" + paymentterms
				+ ", productnamespec=" + productnamespec + ", deliveryorderid=" + deliveryorderid
				+ ", deliveryorderremark=" + deliveryorderremark + ", ordertime=" + ordertime + ", acceptordertime="
				+ acceptordertime + ", exporttime=" + exporttime + ", arriveordertime=" + arriveordertime
				+ ", completeordertime=" + completeordertime + ", cancelordertime=" + cancelordertime
				+ ", autoorderfunction=" + autoorderfunction + ", unitsellprice=" + unitsellprice + ", minsellqty="
				+ minsellqty + ", productdesc=" + productdesc + ", onshelf=" + onshelf
				+ ", autoorderconfirmfunctionstatus=" + autoorderconfirmfunctionstatus + ", reservedqty=" + reservedqty
				+ "]";
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public Integer getUnitcost() {
		return unitcost;
	}

	public void setUnitcost(Integer unitcost) {
		this.unitcost = unitcost;
	}

	public String getPaymentterms() {
		return paymentterms;
	}

	public void setPaymentterms(String paymentterms) {
		this.paymentterms = paymentterms;
	}

	public Integer getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Integer ownerid) {
		this.ownerid = ownerid;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
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

	public Integer getOrderqty() {
		return orderqty;
	}

	public void setOrderqty(Integer orderqty) {
		this.orderqty = orderqty;
	}

	public Integer getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(Integer buyerid) {
		this.buyerid = buyerid;
	}

	public Integer getUnitdealprice() {
		return unitdealprice;
	}

	public void setUnitdealprice(Integer unitdealprice) {
		this.unitdealprice = unitdealprice;
	}

	public Integer getSafeqty() {
		return safeqty;
	}

	public void setSafeqty(Integer safeqty) {
		this.safeqty = safeqty;
	}

	public String getProductnamespec() {
		return productnamespec;
	}

	public void setProductnamespec(String productnamespec) {
		this.productnamespec = productnamespec;
	}

	public String getDeliveryorderid() {
		return deliveryorderid;
	}

	public void setDeliveryorderid(String deliveryorderid) {
		this.deliveryorderid = deliveryorderid;
	}

	public String getDeliveryorderremark() {
		return deliveryorderremark;
	}

	public void setDeliveryorderremark(String deliveryorderremark) {
		this.deliveryorderremark = deliveryorderremark;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getAcceptordertime() {
		return acceptordertime;
	}

	public void setAcceptordertime(String acceptordertime) {
		this.acceptordertime = acceptordertime;
	}

	public String getExporttime() {
		return exporttime;
	}

	public void setExporttime(String exporttime) {
		this.exporttime = exporttime;
	}

	public String getArriveordertime() {
		return arriveordertime;
	}

	public void setArriveordertime(String arriveordertime) {
		this.arriveordertime = arriveordertime;
	}

	public String getCompleteordertime() {
		return completeordertime;
	}

	public void setCompleteordertime(String completeordertime) {
		this.completeordertime = completeordertime;
	}

	public String getCancelordertime() {
		return cancelordertime;
	}

	public void setCancelordertime(String cancelordertime) {
		this.cancelordertime = cancelordertime;
	}


	public String getAutoorderfunction() {
		return autoorderfunction;
	}

	public void setAutoorderfunction(String autoorderfunction) {
		this.autoorderfunction = autoorderfunction;
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

	public JSONObject toJsonObject() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		obj.put("productid", productid);
		obj.put("orderid", orderid);
		obj.put("orderstatus", orderstatus);
		obj.put("stockqty", stockqty);
		obj.put("warningqty", warningqty);
		obj.put("orderqty", orderqty);
		obj.put("buyerid", buyerid);
		obj.put("unitdealprice", unitdealprice);
		obj.put("safeqty", safeqty);
		obj.put("autoorderfunction", autoorderfunction);
		obj.put("productnamespec", productnamespec);
		obj.put("deliveryorderid", deliveryorderid);
		obj.put("deliveryorderremark", deliveryorderremark);
		obj.put("autoorderfunction", autoorderfunction);
		obj.put("unitsellprice", unitsellprice);
		obj.put("minsellqty", minsellqty);
		obj.put("productdesc", productdesc);
		obj.put("onshelf", onshelf);
		obj.put("autoorderconfirmfunctionstatus", autoorderconfirmfunctionstatus);
		obj.put("reservedqty", reservedqty);
		obj.put("ownerid", ownerid);
		obj.put("unitcost", unitcost);
		obj.put("paymentterms", paymentterms);
		obj.put("companyname", companyname);
		obj.put("ordertime", ordertime == null ? "null" : ordertime);
		obj.put("acceptordertime", acceptordertime == null ? "null" : acceptordertime);
		obj.put("exporttime", exporttime == null ? "null" : exporttime);
		obj.put("arriveordertime", arriveordertime == null ? "null" : arriveordertime);
		obj.put("completeordertime", completeordertime == null ? "null" : completeordertime);
		obj.put("cancelordertime", cancelordertime == null ? "null" : cancelordertime);
		return obj;
	}

}
