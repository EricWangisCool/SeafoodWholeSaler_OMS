package tw.com.ispan.eeit48.mainfunction.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.json.JSONObject;

@Entity
@Table(name = "t_order_details")
@IdClass(OrderDetailsPK.class)
@DynamicUpdate
public class OrderDetailsBean implements Serializable {
	@Id
	@Column(columnDefinition = "char")
	private String order_id;
	private Integer seller_product_id;
	private Integer order_qty;
	private Integer unit_deal_price;




	

	

	@Override
	public String toString() {
		return "OrderDetailsBean [order_id=" + order_id + ", seller_product_id=" + seller_product_id + ", order_qty="
				+ order_qty + ", unit_deal_price=" + unit_deal_price + "]";
	}

	public String getOrderid() {
		return order_id;
	}

	public void setOrderid(String order_id) {
		this.order_id = order_id;
	}

	public Integer getSellerproductid() {
		return seller_product_id;
	}

	public void setSellerproductid(Integer seller_product_id) {
		this.seller_product_id = seller_product_id;
	}

	public Integer getOrderqty() {
		return order_qty;
	}

	public void setOrderqty(Integer order_qty) {
		this.order_qty = order_qty;
	}

	public Integer getUnitdealprice() {
		return unit_deal_price;
	}

	public void setUnitdealprice(Integer unit_deal_price) {
		this.unit_deal_price = unit_deal_price;
	}
}