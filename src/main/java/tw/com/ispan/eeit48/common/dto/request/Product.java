package tw.com.ispan.eeit48.common.dto.request;


public class Product {
    private Integer sellerProductId;
    private Integer orderQty;
    private Integer unitDealPrice;

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
