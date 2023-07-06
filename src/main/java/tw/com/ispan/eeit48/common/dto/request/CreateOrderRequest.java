package tw.com.ispan.eeit48.common.dto.request;

import java.util.List;

public class CreateOrderRequest {
    Integer sellerId;
    List<Product> products;

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
