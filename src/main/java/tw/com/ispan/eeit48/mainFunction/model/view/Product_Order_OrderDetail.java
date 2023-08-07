package tw.com.ispan.eeit48.mainFunction.model.view;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;
import tw.com.ispan.eeit48.mainFunction.model.view.pk.Product_Order_OrderDetailPK;

@Entity
@Immutable
@Table(name = "v_product_order_order_details")
@IdClass(Product_Order_OrderDetailPK.class)
@DynamicUpdate
public class Product_Order_OrderDetail implements Serializable {
	@Id
	private String productId;
	@Column(columnDefinition = "char")
	private String orderId;
	private Integer orderStatus;
	private Integer stockQty;
	private Integer warningQty;
	private Integer orderQty;
	private Integer buyerId;
	private Integer ownerId;
	private Integer unitDealPrice;
	private Integer safeQty;
	@Column(columnDefinition = "char")
	private String companyName;
	private Integer unitCost;
	@Column(columnDefinition = "char")
	private String paymentTerms;
	@Column(columnDefinition = "char")
	private String productNameSpec;
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
	@Column(columnDefinition = "char")
	private String autoOrderFunction;
	private Integer unitSellPrice;
	private Integer minSellQty;
	@Column(columnDefinition = "char")
	private String productDesc;
	private Integer onShelf;
	private Integer autoOrderConfirmFunctionStatus;
	private Integer reservedQty;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}
	public Integer getWarningQty() {
		return warningQty;
	}
	public void setWarningQty(Integer warningQty) {
		this.warningQty = warningQty;
	}
	public Integer getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public Integer getUnitDealPrice() {
		return unitDealPrice;
	}
	public void setUnitDealPrice(Integer unitDealPrice) {
		this.unitDealPrice = unitDealPrice;
	}
	public Integer getSafeQty() {
		return safeQty;
	}
	public void setSafeQty(Integer safeQty) {
		this.safeQty = safeQty;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Integer unitCost) {
		this.unitCost = unitCost;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getProductNameSpec() {
		return productNameSpec;
	}
	public void setProductNameSpec(String productNameSpec) {
		this.productNameSpec = productNameSpec;
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
	public String getAutoOrderFunction() {
		return autoOrderFunction;
	}
	public void setAutoOrderFunction(String autoOrderFunction) {
		this.autoOrderFunction = autoOrderFunction;
	}
	public Integer getUnitSellPrice() {
		return unitSellPrice;
	}
	public void setUnitSellPrice(Integer unitSellPrice) {
		this.unitSellPrice = unitSellPrice;
	}
	public Integer getMinSellQty() {
		return minSellQty;
	}
	public void setMinSellQty(Integer minSellQty) {
		this.minSellQty = minSellQty;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public Integer getOnShelf() {
		return onShelf;
	}
	public void setOnShelf(Integer onShelf) {
		this.onShelf = onShelf;
	}
	public Integer getAutoOrderConfirmFunctionStatus() {
		return autoOrderConfirmFunctionStatus;
	}
	public void setAutoOrderConfirmFunctionStatus(Integer autoOrderConfirmFunctionStatus) {
		this.autoOrderConfirmFunctionStatus = autoOrderConfirmFunctionStatus;
	}
	public Integer getReservedQty() {
		return reservedQty;
	}
	public void setReservedQty(Integer reservedQty) {
		this.reservedQty = reservedQty;
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
