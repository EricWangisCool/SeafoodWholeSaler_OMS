package tw.com.ispan.eeit48.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "orders")
public class OrdersBean {
	
	@Id
	@Column(columnDefinition = "char")
	private String orderid;
	private Integer buyerid;
	private Integer sellerid;
	private Integer orderstatus;
	@Column(columnDefinition = "char")
	private String paymentterms;
	@Column(columnDefinition = "char")
	private String deliveryorderid;
	@Column(columnDefinition = "char")
	private String deliveryorderremark;
	@Column(columnDefinition = "datetime")
	private Date ordertime;
	@Column(columnDefinition = "datetime")
	private Date acceptordertime;
	@Column(columnDefinition = "datetime")
	private Date exporttime;
	@Column(columnDefinition = "datetime")
	private Date arriveordertime;
	@Column(columnDefinition = "datetime")
	private Date completeordertime;
	@Column(columnDefinition = "datetime")
	private Date cancelordertime;

	@Override
	public String toString() {
		return "OrdersBean [orderid=" + orderid + ", buyerid=" + buyerid + ", sellerid=" + sellerid + ", orderstatus="
				+ orderstatus + ", paymentterms=" + paymentterms + ", deliveryorderid=" + deliveryorderid
				+ ", deliveryorderremark=" + deliveryorderremark + ", ordertime=" + ordertime + ", acceptordertime="
				+ acceptordertime + ", exporttime=" + exporttime + ", arriveordertime=" + arriveordertime
				+ ", completeordertime=" + completeordertime + ", cancelordertime=" + cancelordertime + "]";
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(Integer buyerid) {
		this.buyerid = buyerid;
	}

	public Integer getSellerid() {
		return sellerid;
	}

	public void setSellerid(Integer sellerid) {
		this.sellerid = sellerid;
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getPaymentterms() {
		return paymentterms;
	}

	public void setPaymentterms(String paymentterms) {
		this.paymentterms = paymentterms;
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

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public Date getAcceptordertime() {
		return acceptordertime;
	}

	public void setAcceptordertime(Date acceptordertime) {
		this.acceptordertime = acceptordertime;
	}

	public Date getExporttime() {
		return exporttime;
	}

	public void setExporttime(Date exporttime) {
		this.exporttime = exporttime;
	}

	public Date getArriveordertime() {
		return arriveordertime;
	}

	public void setArriveordertime(Date arriveordertime) {
		this.arriveordertime = arriveordertime;
	}

	public Date getCompleteordertime() {
		return completeordertime;
	}

	public void setCompleteordertime(Date completeordertime) {
		this.completeordertime = completeordertime;
	}

	public Date getCancelordertime() {
		return cancelordertime;
	}

	public void setCancelordertime(Date cancelordertime) {
		this.cancelordertime = cancelordertime;
	}

	public JSONObject toJsonObject() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		obj.put("orderid", orderid);
		obj.put("buyerid", buyerid);
		obj.put("sellerid", sellerid);
		obj.put("orderstatus", orderstatus);
		obj.put("paymentterms", paymentterms);
		obj.put("deliveryorderid", deliveryorderid);
		obj.put("deliveryorderremark", deliveryorderremark);
		obj.put("ordertime", ordertime == null ? "null" : sdFormat.format(ordertime));
		obj.put("acceptordertime", acceptordertime == null ? "null" : sdFormat.format(acceptordertime));
		obj.put("exporttime", exporttime == null ? "null" : sdFormat.format(exporttime));
		obj.put("arriveordertime", arriveordertime == null ? "null" : sdFormat.format(arriveordertime));
		obj.put("completeordertime", completeordertime == null ? "null" : sdFormat.format(completeordertime));
		obj.put("cancelordertime", cancelordertime == null ? "null" : sdFormat.format(cancelordertime));
		return obj;
	}
}
