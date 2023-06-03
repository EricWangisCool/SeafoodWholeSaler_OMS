package tw.com.ispan.eeit48.mainfunction.requestResponse.model.request;

import tw.com.ispan.eeit48.mainfunction.model.ProductBean;
import tw.com.ispan.eeit48.mainfunction.model.SupplierProductForOwnerProductBean;

public class ProductRequest {
    ProductBean productInfo;
    SupplierProductForOwnerProductBean supplierInfo;

    public ProductBean getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductBean productInfo) {
        this.productInfo = productInfo;
    }

    public SupplierProductForOwnerProductBean getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(SupplierProductForOwnerProductBean supplierInfo) {
        this.supplierInfo = supplierInfo;
    }
}
