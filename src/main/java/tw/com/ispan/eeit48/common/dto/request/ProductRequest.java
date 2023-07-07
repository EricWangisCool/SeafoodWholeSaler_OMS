package tw.com.ispan.eeit48.common.dto.request;

import tw.com.ispan.eeit48.mainFunction.model.table.Product;
import tw.com.ispan.eeit48.mainFunction.model.table.SupplierProductForOwnerProduct;

public class ProductRequest {
    private Product productInfo;
    private SupplierProductForOwnerProduct supplierInfo;

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public SupplierProductForOwnerProduct getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(SupplierProductForOwnerProduct supplierInfo) {
        this.supplierInfo = supplierInfo;
    }
}
