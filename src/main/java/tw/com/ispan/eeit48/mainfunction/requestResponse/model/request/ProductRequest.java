package tw.com.ispan.eeit48.mainfunction.requestResponse.model.request;

import tw.com.ispan.eeit48.mainfunction.model.table.Product;
import tw.com.ispan.eeit48.mainfunction.model.table.SupplierProductForOwnerProduct;

public class ProductRequest {
    Product productInfo;
    SupplierProductForOwnerProduct supplierInfo;

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
