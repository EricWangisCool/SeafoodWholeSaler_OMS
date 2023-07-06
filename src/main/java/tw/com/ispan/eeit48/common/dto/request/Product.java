package tw.com.ispan.eeit48.common.dto.request;

import javax.persistence.criteria.CriteriaBuilder;

public class Product {
    Integer sellerProductId;
    Integer orderQty;
    Integer unitDealPrice;

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
