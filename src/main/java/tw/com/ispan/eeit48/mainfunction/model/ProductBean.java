package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "t_product")
public class ProductBean {

	@Id
	private Integer product_id;
	@Column(columnDefinition = "longtext")
	private String product_pic;
	private Integer product_classification;
	private Integer unit_cost;
	private Integer stock_qty;
	private Integer warning_qty;
	@Column(columnDefinition = "char")
	private String auto_order_function;
	private Integer safe_qty;
	private Integer unit_sell_price;
	private Integer min_sell_qty;
	@Column(columnDefinition = "char")
	private String product_desc;
	private Integer on_shelf;
	private Integer auto_order_confirm_function_status;
	private Integer reserved_qty;
	private Integer owner_id;
	@Column(columnDefinition = "char")
	private String product_name_spec;



	@Override
	public String toString() {
		return "ProductBean [product_id=" + product_id + ", product_pic=" + product_pic + ", product_classification="
				+ product_classification + ", unit_cost=" + unit_cost + ", stock_qty=" + stock_qty + ", warning_qty="
				+ warning_qty + ", auto_order_function=" + auto_order_function + ", safe_qty=" + safe_qty
				+ ", unit_sell_price=" + unit_sell_price + ", min_sell_qty=" + min_sell_qty + ", product_desc="
				+ product_desc + ", on_shelf=" + on_shelf + ", auto_order_confirm_function_status="
				+ auto_order_confirm_function_status + ", reserved_qty=" + reserved_qty + ", owner_id=" + owner_id
				+ ", product_name_spec=" + product_name_spec + "]";
	}

	public Integer getProductid() {
		return product_id;
	}

	public void setProductid(Integer product_id) {
		this.product_id = product_id;
	}

	public String getProductpic() {
		return product_pic;
	}

	public void setProductpic(String product_pic) {
		this.product_pic = product_pic;
	}

	public Integer getProductclassification() {
		return product_classification;
	}

	public void setProductclassification(Integer product_classification) {
		this.product_classification = product_classification;
	}

	public Integer getUnitcost() {
		return unit_cost;
	}

	public void setUnitcost(Integer unit_cost) {
		this.unit_cost = unit_cost;
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

	public String getAutoorderfunction() {
		return auto_order_function;
	}

	public void setAutoorderfunction(String auto_order_function) {
		this.auto_order_function = auto_order_function;
	}

	public Integer getSafeqty() {
		return safe_qty;
	}

	public void setSafeqty(Integer safe_qty) {
		this.safe_qty = safe_qty;
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

	public Integer getOwnerid() {
		return owner_id;
	}

	public void setOwnerid(Integer owner_id) {
		this.owner_id = owner_id;
	}

	public String getProductnamespec() {
		return product_name_spec;
	}

	public void setProductnamespec(String product_name_spec) {
		this.product_name_spec = product_name_spec;
	}

}
