package tw.com.ispan.eeit48.common.dto.request;

import org.springframework.lang.NonNull;

public class StatementRequest extends AnalyzeOrderRequest {
    @NonNull
    Integer buyerId;

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }
}
