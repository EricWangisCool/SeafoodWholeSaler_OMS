package tw.com.ispan.eeit48.mainFunction.model.table;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_orders")
public class Order {
	@Id
	@Column(columnDefinition = "char")
	private String orderId;
	private Integer buyerId;
	private Integer sellerId;
	private Integer orderStatus;
	@Column(columnDefinition = "char")
	private String paymentTerms;
	@Column(columnDefinition = "char")
	private String deliveryOrderId;
	@Column(columnDefinition = "char")
	private String deliveryOrderRemark;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date orderTime;
	@Column(columnDefinition = "datetime")
	private Date acceptOrderTime;
	@Column(columnDefinition = "datetime")
	private Date exportTime;
	@Column(columnDefinition = "datetime")
	private Date arriveOrderTime;
	@Column(columnDefinition = "datetime")
	private Date completeOrderTime;
	@Column(columnDefinition = "datetime")
	private Date cancelOrderTime;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getDeliveryOrderId() {
		return deliveryOrderId;
	}
	public void setDeliveryOrderId(String deliveryOrderId) {
		this.deliveryOrderId = deliveryOrderId;
	}
	public String getDeliveryOrderRemark() {
		return deliveryOrderRemark;
	}
	public void setDeliveryOrderRemark(String deliveryOrderRemark) {
		this.deliveryOrderRemark = deliveryOrderRemark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getAcceptOrderTime() {
		return acceptOrderTime;
	}
	public void setAcceptOrderTime(Date acceptOrderTime) {
		this.acceptOrderTime = acceptOrderTime;
	}
	public Date getExportTime() {
		return exportTime;
	}
	public void setExportTime(Date exportTime) {
		this.exportTime = exportTime;
	}
	public Date getArriveOrderTime() {
		return arriveOrderTime;
	}
	public void setArriveOrderTime(Date arriveOrderTime) {
		this.arriveOrderTime = arriveOrderTime;
	}
	public Date getCompleteOrderTime() {
		return completeOrderTime;
	}
	public void setCompleteOrderTime(Date completeOrderTime) {
		this.completeOrderTime = completeOrderTime;
	}
	public Date getCancelOrderTime() {
		return cancelOrderTime;
	}
	public void setCancelOrderTime(Date cancelOrderTime) {
		this.cancelOrderTime = cancelOrderTime;
	}

}
