package tw.com.ispan.eeit48.mainfunction.model.table;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import tw.com.ispan.eeit48.mainfunction.model.table.pk.OrderDetailsPK;

@Entity
@Table(name = "t_order_details")
@IdClass(OrderDetailsPK.class)
@DynamicUpdate
public class OrderDetail implements Serializable {
	@Id
	@Column(columnDefinition = "char")
	private String orderId;
	private Integer sellerProductId;
	private Integer orderQty;
	private Integer unitDealPrice;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getSellerProductId() {
		return sellerProductId;
	}
	public void setSellerProductId(Integer sellerProductId) {
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