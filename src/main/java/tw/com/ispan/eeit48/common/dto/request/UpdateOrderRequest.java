package tw.com.ispan.eeit48.common.dto.request;

import org.springframework.lang.NonNull;

public class UpdateOrderRequest {
    @NonNull
    private String orderId;
    @NonNull
    private Integer orderStatus;
    private String deliveryOrderRemark;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryOrderRemark() {
        return deliveryOrderRemark;
    }

    public void setDeliveryOrderRemark(String deliveryOrderRemark) {
        this.deliveryOrderRemark = deliveryOrderRemark;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
