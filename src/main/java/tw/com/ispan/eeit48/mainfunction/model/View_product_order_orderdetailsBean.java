package tw.com.ispan.eeit48.mainfunction.model;

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
@Table(name = "v_product_order_order_details")
@IdClass(View_product_order_orderdetailsPK.class)
@DynamicUpdate
public class View_product_order_orderdetailsBean implements Serializable {
	@Id
	private Integer product_id;
	@Column(columnDefinition = "char")
	private String order_id;
	private Integer order_status;
	private Integer stock_qty;
	private Integer warning_qty;
	private Integer order_qty;
	private Integer buyer_id;
	private Integer owner_id;
	private Integer unit_deal_price;
	private Integer safe_qty;
	@Column(columnDefinition = "char")
	private String company_name;
	private Integer unit_cost;
	@Column(columnDefinition = "char")
	private String payment_terms;
	@Column(columnDefinition = "char")
	private String product_name_spec;
	@Column(columnDefinition = "char")
	private String delivery_order_id;
	@Column(columnDefinition = "char")
	private String delivery_order_remark;
	@Column(columnDefinition = "datetime")
	private String order_time;
	@Column(columnDefinition = "datetime")
	private String accept_order_time;
	@Column(columnDefinition = "datetime")
	private String export_time;
	@Column(columnDefinition = "datetime")
	private String arrive_order_time;
	@Column(columnDefinition = "datetime")
	private String complete_order_time;
	@Column(columnDefinition = "datetime")
	private String cancel_order_time;
	@Column(columnDefinition = "char")
	private String auto_order_function;
	private Integer unit_sell_price;
	private Integer min_sell_qty;
	@Column(columnDefinition = "char")
	private String product_desc;
	private Integer on_shelf;
	private Integer auto_order_confirm_function_status;
	private Integer reserved_qty;

	



	@Override
	public String toString() {
		return "View_product_order_orderdetailsBean [product_id=" + product_id + ", order_id=" + order_id
				+ ", order_status=" + order_status + ", stock_qty=" + stock_qty + ", warning_qty=" + warning_qty
				+ ", order_qty=" + order_qty + ", buyer_id=" + buyer_id + ", owner_id=" + owner_id
				+ ", unit_deal_price=" + unit_deal_price + ", safe_qty=" + safe_qty + ", company_name=" + company_name
				+ ", unit_cost=" + unit_cost + ", payment_terms=" + payment_terms + ", product_name_spec="
				+ product_name_spec + ", delivery_order_id=" + delivery_order_id + ", delivery_order_remark="
				+ delivery_order_remark + ", order_time=" + order_time + ", accept_order_time=" + accept_order_time
				+ ", export_time=" + export_time + ", arrive_order_time=" + arrive_order_time + ", complete_order_time="
				+ complete_order_time + ", cancel_order_time=" + cancel_order_time + ", auto_order_function="
				+ auto_order_function + ", unit_sell_price=" + unit_sell_price + ", min_sell_qty=" + min_sell_qty
				+ ", product_desc=" + product_desc + ", on_shelf=" + on_shelf + ", auto_order_confirm_function_status="
				+ auto_order_confirm_function_status + ", reserved_qty=" + reserved_qty + "]";
	}

	public String getCompanyname() {
		return company_name;
	}

	public void setCompanyname(String company_name) {
		this.company_name = company_name;
	}

	public Integer getUnitcost() {
		return unit_cost;
	}

	public void setUnitcost(Integer unit_cost) {
		this.unit_cost = unit_cost;
	}

	public String getPaymentterms() {
		return payment_terms;
	}

	public void setPaymentterms(String payment_terms) {
		this.payment_terms =payment_terms;
	}

	public Integer getOwnerid() {
		return owner_id;
	}

	public void setOwnerid(Integer  owner_id) {
		this. owner_id=  owner_id;
	}

	public Integer getProductid() {
		return product_id;
	}

	public void setProductid(Integer product_id) {
		this.product_id = product_id;
	}

	public String getOrderid() {
		return order_id;
	}

	public void setOrderid(String order_id) {
		this.order_id = order_id;
	}

	public Integer getOrderstatus() {
		return order_status;
	}

	public void setOrderstatus(Integer order_status) {
		this.order_status = order_status;
	}

	public Integer getStockqty() {
		return stock_qty;
	}

	public void setStockqty(Integer stock_qty) {
		this.stock_qty = stock_qty;
	}

	public Integer getWarningqty() {
		return warning_qty;
	}

	public void setWarningqty(Integer warning_qty) {
		this.warning_qty = warning_qty;
	}

	public Integer getOrderqty() {
		return order_qty;
	}

	public void setOrderqty(Integer order_qty) {
		this.order_qty = order_qty;
	}

	public Integer getBuyerid() {
		return buyer_id;
	}

	public void setBuyerid(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}

	public Integer getUnitdealprice() {
		return unit_deal_price;
	}

	public void setUnitdealprice(Integer unit_deal_price) {
		this.unit_deal_price = unit_deal_price;
	}

	public Integer getSafeqty() {
		return safe_qty;
	}

	public void setSafeqty(Integer safe_qty) {
		this.safe_qty = safe_qty;
	}

	public String getProductnamespec() {
		return product_name_spec;
	}

	public void setProductnamespec(String product_name_spec) {
		this.product_name_spec = product_name_spec;
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

	public String getOrdertime() {
		return order_time;
	}

	public void setOrdertime(String order_time) {
		this.order_time = order_time;
	}

	public String getAcceptordertime() {
		return accept_order_time;
	}

	public void setAcceptordertime(String accept_order_time) {
		this.accept_order_time = accept_order_time;
	}

	public String getExporttime() {
		return export_time;
	}

	public void setExporttime(String export_time) {
		this.export_time = export_time;
	}

	public String getArriveordertime() {
		return arrive_order_time;
	}

	public void setArriveordertime(String arrive_order_time) {
		this.arrive_order_time = arrive_order_time;
	}

	public String getCompleteordertime() {
		return complete_order_time;
	}

	public void setCompleteordertime(String complete_order_time) {
		this.complete_order_time = complete_order_time;
	}

	public String getCancelordertime() {
		return cancel_order_time;
	}

	public void setCancelordertime(String cancel_order_time) {
		this.cancel_order_time = cancel_order_time;
	}


	public String getAutoorderfunction() {
		return auto_order_function;
	}

	public void setAutoorderfunction(String auto_order_function) {
		this.auto_order_function = auto_order_function;
	}

	public Integer getUnitsellprice() {
		return unit_sell_price;
	}

	public void setUnitsellprice(Integer unit_sell_price) {
		this.unit_sell_price = unit_sell_price;
	}

	public Integer getMinsellqty() {
		return min_sell_qty;
	}

	public void setMinsellqty(Integer min_sell_qty) {
		this.min_sell_qty = min_sell_qty;
	}

	public String getProductdesc() {
		return product_desc;
	}

	public void setProductdesc(String product_desc) {
		this.product_desc = product_desc;
	}

	public Integer getOnshelf() {
		return on_shelf;
	}

	public void setOnshelf(Integer on_shelf) {
		this.on_shelf = on_shelf;
	}

	public Integer getAutoorderconfirmfunctionstatus() {
		return auto_order_confirm_function_status;
	}

	public void setAutoorderconfirmfunctionstatus(Integer auto_order_confirm_function_status) {
		this.auto_order_confirm_function_status = auto_order_confirm_function_status;
	}

	public Integer getReservedqty() {
		return reserved_qty;
	}

	public void setReservedqty(Integer reserved_qty) {
		this.reserved_qty = reserved_qty;
	}
}
