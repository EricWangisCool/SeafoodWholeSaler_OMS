package tw.com.ispan.eeit48.mainfunction.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "t_orders")
public class OrdersBean {
	
	@Id
	@Column(columnDefinition = "char")
	private String order_id;
	private Integer buyer_id;
	private Integer seller_id;
	private Integer order_status;
	@Column(columnDefinition = "char")
	private String payment_terms;
	@Column(columnDefinition = "char")
	private String delivery_order_id;
	@Column(columnDefinition = "char")
	private String delivery_order_remark;
	@Column(columnDefinition = "datetime")
	private Date order_time;
	@Column(columnDefinition = "datetime")
	private Date accept_order_time;
	@Column(columnDefinition = "datetime")
	private Date export_time;
	@Column(columnDefinition = "datetime")
	private Date arrive_order_time;
	@Column(columnDefinition = "datetime")
	private Date complete_order_time;
	@Column(columnDefinition = "datetime")
	private Date cancel_order_time;



	

	@Override
	public String toString() {
		return "OrdersBean [order_id=" + order_id + ", buyer_id=" + buyer_id + ", seller_id=" + seller_id
				+ ", order_status=" + order_status + ", payment_terms=" + payment_terms + ", delivery_order_id="
				+ delivery_order_id + ", delivery_order_remark=" + delivery_order_remark + ", order_time=" + order_time
				+ ", accept_order_time=" + accept_order_time + ", export_time=" + export_time + ", arrive_order_time="
				+ arrive_order_time + ", complete_order_time=" + complete_order_time + ", cancel_order_time="
				+ cancel_order_time + "]";
	}

	public String getOrderid() {
		return order_id;
	}

	public void setOrderid(String order_id) {
		this.order_id = order_id;
	}

	public Integer getBuyerid() {
		return buyer_id;
	}

	public void setBuyerid(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}

	public Integer getSellerid() {
		return seller_id;
	}

	public void setSellerid(Integer seller_id) {
		this.seller_id = seller_id;
	}

	public Integer getOrderstatus() {
		return order_status;
	}

	public void setOrderstatus(Integer order_status) {
		this.order_status = order_status;
	}

	public String getPaymentterms() {
		return payment_terms;
	}

	public void setPaymentterms(String payment_terms) {
		this.payment_terms = payment_terms;
	}

	public String getDeliveryorderid() {
		return delivery_order_id;
	}

	public void setDeliveryorderid(String delivery_order_id) {
		this.delivery_order_id = delivery_order_id;
	}

	public String getDeliveryorderremark() {
		return delivery_order_remark;
	}

	public void setDeliveryorderremark(String delivery_order_remark) {
		this.delivery_order_remark = delivery_order_remark;
	}

	public Date getOrdertime() {
		return order_time;
	}

	public void setOrdertime(Date order_time) {
		this.order_time = order_time;
	}

	public Date getAcceptordertime() {
		return accept_order_time;
	}

	public void setAcceptordertime(Date accept_order_time) {
		this.accept_order_time = accept_order_time;
	}

	public Date getExporttime() {
		return export_time;
	}

	public void setExporttime(Date export_time) {
		this.export_time = export_time;
	}

	public Date getArriveordertime() {
		return arrive_order_time;
	}

	public void setArriveordertime(Date arrive_order_time) {
		this.arrive_order_time = arrive_order_time;
	}

	public Date getCompleteordertime() {
		return complete_order_time;
	}

	public void setCompleteordertime(Date complete_order_time) {
		this.complete_order_time = complete_order_time;
	}

	public Date getCancelordertime() {
		return cancel_order_time;
	}

	public void setCancelordertime(Date cancel_order_time) {
		this.cancel_order_time = cancel_order_time;
	}
}
