package tw.com.ispan.eeit48.common.dto.request;

import org.springframework.lang.NonNull;

public class OrderRequest {
    @NonNull
    private String orderId;
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
