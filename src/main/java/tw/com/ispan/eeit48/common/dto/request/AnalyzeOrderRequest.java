package tw.com.ispan.eeit48.common.dto.request;

public class AnalyzeOrderRequest {
    String orderTime;
    String completeOrderTime;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCompleteOrderTime() {
        return completeOrderTime;
    }

    public void setCompleteOrderTime(String completeOrderTime) {
        this.completeOrderTime = completeOrderTime;
    }
}
