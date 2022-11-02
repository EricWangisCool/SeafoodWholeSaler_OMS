package tw.com.ispan.eeit48.service;

import java.util.List;
import javax.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.domain.SupplierProductForOwnerProductBean;
import tw.com.ispan.eeit48.domain.View_product_order_orderdetailsBean;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.repository.ProductRepository;
import tw.com.ispan.eeit48.repository.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.repository.View_product_order_orderdetailsRepository;

@Service
@Transactional
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private View_product_order_orderdetailsRepository view_product_order_orderdetailsRepository;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;

	// 以商品OwnerId找: 產品資訊
	public List<ProductBean> findProductByOwnerid(int OwnerId) {
		List<ProductBean> beans = productRepository.findAllByOwneridByOrderByOwneridDesc(OwnerId);
		if (!beans.isEmpty()) {
			return beans;
		}
		return null;
	}

	// 以商品ID找: 被訂購量(orderedQty)(接單狀態為3~5的總數)
	public int findOrderedQtyByProductId(int productId) {
		List<View_product_order_orderdetailsBean> beans = view_product_order_orderdetailsRepository
				.findAllByProductidAndOrderstatusBetween(productId, 3, 5);
		// 裝所有被訂購的數量
		int a = 0;
		if (!beans.isEmpty()) {
			JSONArray lista = new JSONArray();
			for (View_product_order_orderdetailsBean bean : beans) {
				lista.put(bean.toJsonObject());
			}
			for (int i = 0; i < lista.length(); i++) {
				int b = lista.getJSONObject(i).getInt("orderqty");
				a = a + b;
			}
			return a;
		}
		return a;
	}

	// 以使用者商品ID找: 可出現貨(stockown)(產品庫存數 - 被訂購量)
	public int findStockOwnByProductId(int productId) {
		int stockOwn = 0;
		ProductService ps = new ProductService(productRepository, view_product_order_orderdetailsRepository,
				supplierProductForOwnerProductRepository);
		// 產品庫存數
		int stockQty = productRepository.findStockqtyByProductid(productId);
		// 被訂購量
		int orderedQty = ps.findOrderedQtyByProductId(productId);
		// 可出現貨
		stockOwn = stockQty - orderedQty;
		return stockOwn;
	}

	// 以商品ID找: 已叫現貨數(callshipping)(叫貨狀態為2~5的總數)
	public int findCallshippingByProductId(int productId) {
		// 裝所有訂購的數量
		int a = 0;
		// 以使用者產品ID搜出供應商產品ID
		List<SupplierProductForOwnerProductBean> supplierProductIdBeans = supplierProductForOwnerProductRepository
				.findAllByProductid(productId);

		if (!supplierProductIdBeans.isEmpty()) {
			for (SupplierProductForOwnerProductBean supplierProductIdbean : supplierProductIdBeans) {
				if (supplierProductIdbean.getSupplierproductid() != null) {
					// 以供應商產品ID搜尋訂購數量
					List<View_product_order_orderdetailsBean> beans = view_product_order_orderdetailsRepository
							.findAllByProductidAndOrderstatusBetween(supplierProductIdbean.getSupplierproductid(), 2,
									5);
					if (!beans.isEmpty()) {
						JSONArray lista = new JSONArray();
						for (View_product_order_orderdetailsBean bean : beans) {
							if (bean != null) {
								lista.put(bean.toJsonObject());
							}
						}
						for (int i = 0; i < lista.length(); i++) {
							int b = lista.getJSONObject(i).getInt("orderqty");
							a = a + b;
						}
						return a;
					}
					return a;
				}
			}
		}
		return a;
	}

	// 以商品ID找: 缺貨數(outstock)(安全庫存 - 可出現貨 - 已叫現貨)
	public int findOutStockByProductId(Integer productId) {
		int outStock = 0;
		ProductService ps = new ProductService(productRepository, view_product_order_orderdetailsRepository,
				supplierProductForOwnerProductRepository);
		// 安全庫存
		int safeQty = productRepository.findSafeQtyByProductid(productId) == null ? 0
				: productRepository.findSafeQtyByProductid(productId);
		// 可出現貨
		int stockOwn = ps.findStockOwnByProductId(productId);
		// 已叫現貨
		int callshipping = ps.findCallshippingByProductId(productId);
		// 缺貨數
		outStock = safeQty - stockOwn - callshipping;
		return outStock;
	}

	// 以使用者產品ID查找: 供應商資訊
	public JSONObject findSupplierObjectByOwnerProductId(int ownerProductId) {
		SupplierProductForOwnerProductBean supplier = supplierProductForOwnerProductRepository
				.findOneByProductid(ownerProductId);
		JSONObject supplierJsonObject = null;
		if (supplier != null) {
			// 使用供應商ID: 找供應商公司名稱
			String supplierCompanyName = accountsRepository.findCompanynameByAccountid(supplier.getSupplierid());
			// 使用供應商產品ID: 找供應商產品名稱
			String supplierProductName = productRepository.findProductNameByProductid(supplier.getSupplierproductid());
			// 將供應商資訊物件化, 並放入剛查找出來的供應商名稱
			supplierJsonObject = supplier.toJsonObject();
			supplierJsonObject.put("suppliercompanyname", supplierCompanyName);
			supplierJsonObject.put("supplierproductname", supplierProductName);
		}
		return supplierJsonObject;
	}

	// 給別人建構用以使用ProductService方法
	public ProductService(ProductRepository productRepository,
			View_product_order_orderdetailsRepository view_product_order_orderdetailsRepository,
			SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository) {
		this.productRepository = productRepository;
		this.view_product_order_orderdetailsRepository = view_product_order_orderdetailsRepository;
		this.supplierProductForOwnerProductRepository = supplierProductForOwnerProductRepository;
	}
}
