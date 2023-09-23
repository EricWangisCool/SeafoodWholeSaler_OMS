package tw.com.ispan.eeit48.mainFunction.model.table;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import tw.com.ispan.eeit48.mainFunction.model.table.pk.OrderDetailsPK;

@Entity
@Table(name = "t_order_details")
@IdClass(OrderDetailsPK.class)
@DynamicUpdate
public class OrderDetail implements Serializable {
	@Id
	@Column(columnDefinition = "char")
	private String orderId;
	private String sellerProductId;
	private Integer orderQty;
	private Integer unitDealPrice;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSellerProductId() {
		return sellerProductId;
	}
	public void setSellerProductId(String sellerProductId) {
		this.sellerProductId = sellerProductId;
	}
	public Integer getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}
	public Integer getUnitDealPrice() {
		return unitDealPrice;
	}
	public void setUnitDealPrice(Integer unitDealPrice) {
		this.unitDealPrice = unitDealPrice;
	}

}