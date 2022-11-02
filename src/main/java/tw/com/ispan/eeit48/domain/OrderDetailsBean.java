package tw.com.ispan.eeit48.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.json.JSONObject;

@Entity
@Table(name = "orderdetails")
@IdClass(OrderDetailsPK.class)
@DynamicUpdate
public class OrderDetailsBean implements Serializable {
	@Id
	@Column(columnDefinition = "char")
	private String orderid;
	private Integer sellerproductid;
	private Integer orderqty;
	private Integer unitdealprice;

	@Override
	public String toString() {
		return "OrderDetailsBean [orderid=" + orderid + ", sellerproductid=" + sellerproductid + ", orderqty="
				+ orderqty + ", unitdealprice=" + unitdealprice + "]";
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getSellerproductid() {
		return sellerproductid;
	}

	public void setSellerproductid(Integer sellerproductid) {
		this.sellerproductid = sellerproductid;
	}

	public Integer getOrderqty() {
		return orderqty;
	}

	public void setOrderqty(Integer orderqty) {
		this.orderqty = orderqty;
	}

	public Integer getUnitdealprice() {
		return unitdealprice;
	}

	public void setUnitdealprice(Integer unitdealprice) {
		this.unitdealprice = unitdealprice;
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("orderid", orderid);
		obj.put("orderqty", orderqty);
		obj.put("unitdealprice", unitdealprice == null ? 0 : unitdealprice);
		obj.put("sellerproductid", sellerproductid);
		return obj;
	}
}