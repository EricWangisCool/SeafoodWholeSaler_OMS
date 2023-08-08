package tw.com.ispan.eeit48.mainFunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainFunction.model.table.Product;
import tw.com.ispan.eeit48.mainFunction.model.table.SupplierProductForOwnerProduct;
import tw.com.ispan.eeit48.common.dto.request.ProductRequest;
import tw.com.ispan.eeit48.mainFunction.repository.table.AccountRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.ProductRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.SupplierProductForOwnerProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static tw.com.ispan.eeit48.common.util.CommonUtil.convertObjectToMap;
import static tw.com.ispan.eeit48.mainFunction.service.AuthService.getCurrentUserId;

@Service
public class StockService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;
	@Autowired
	private AccountRepository accountRepository;

	public List<Map<String, Object>> getProduct() throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		List<Product> products = productRepository.findAllByOwnerIdByOrderByOwnerIdDesc(getCurrentUserId());
		for (Product product : products) {
			// 運用尋訪出的單個產品資訊, 找出productId, 用來找底下三個數量, 和供應商相關資訊
			String productId = product.getProductId();
			// 1. 被訂購量
			int OrderedQty = productService.findOrderedQtyByProductId(productId);
			// 2. 可出現貨數
			int sellableQty = productService.findSellableQtyByProductId(productId);
			// 3. 已叫現貨數
			int requestedQty = productService.findRequestedQtyByProductId(productId);
			// 4. 供應商相關資訊
			Map<String, Object> supplierMap = findSupplierObjectByOwnerProductId(productId);

			// 將尋訪出的單個產品資訊轉成JSON物件後, 放入已訂未出數 & 可出現貨數 & 已叫現貨數 & 供應商相關資訊
			Map<String, Object> productMap =  convertObjectToMap(product);
			// 原本功能為"已定未出(noShipping)", 後來改成"被訂購量(orderedQty)"
			// 因為前端沒有時間更改key所以維持"noShipping"
			productMap.put("orderedQty", OrderedQty);
			productMap.put("sellableQty", sellableQty);
			productMap.put("requestedQty", requestedQty);
			// 供應商相關資訊
			if (supplierMap != null) {
				productMap.put("supplierId", supplierMap.get("supplierId"));
				productMap.put("supplierCompanyName", supplierMap.get("supplierCompanyName"));
				productMap.put("supplierProductId", supplierMap.get("supplierProductId"));
				productMap.put("supplierProductName", supplierMap.get("supplierProductName"));
			} else {
				productMap.put("supplierId", "null");
				productMap.put("supplierCompanyName", "null");
				productMap.put("supplierProductId", "null");
				productMap.put("supplierProductName", "null");
			}
			productMap.putIfAbsent("reservedQty", "null");
			productMap.putIfAbsent("productPic", "null");
			list.add(productMap);
		}
		return list;
	}

	private Map<String, Object> findSupplierObjectByOwnerProductId(String ownerProductId) {
		SupplierProductForOwnerProduct supplier = supplierProductForOwnerProductRepository
				.findOneByProductId(ownerProductId);
		Map<String, Object> supplierMap = null;
		if (supplier != null) {
			// 使用供應商ID: 找供應商公司名稱
			String supplierCompanyName = accountRepository.findCompanyNameByAccountId(supplier.getSupplierId());
			// 使用供應商產品ID: 找供應商產品名稱
			String supplierProductName = productRepository.findProductNameSpecByProductId(supplier.getSupplierProductId());
			// 將供應商資訊物件化, 並放入剛查找出來的供應商名稱
			supplierMap = convertObjectToMap(supplier);
			supplierMap.put("supplierCompanyName", supplierCompanyName);
			supplierMap.put("supplierProductName", supplierProductName);
		}
		return supplierMap;
	}

	@Transactional
	public void insertNewStock(ProductRequest request) {
		Product productInfo = request.getProductInfo();
		SupplierProductForOwnerProduct supplierInfo = request.getSupplierInfo();
		int userId = getCurrentUserId();

		String newProductId;
		String lastProductId = productRepository.findLastProductIdByOwnerId(userId);
		if (lastProductId != null) {
			newProductId = String.valueOf(Integer.parseInt(lastProductId) + 1);
		} else {
			// 預設以 userId + 00001 作為第一筆productId
			newProductId = String.format("%d%05d", userId, 1);
		}

		if (productInfo.getOnShelf() == 0) {
			productInfo.setMinSellQty(0);
			productInfo.setUnitSellPrice(0);
			productInfo.setProductDesc("");
		}
		productInfo.setOwnerId(userId);
		productInfo.setProductId(newProductId);
		Product savedProduct = productRepository.save(productInfo);

		supplierInfo.setProductId(savedProduct.getProductId());
		supplierProductForOwnerProductRepository.save(supplierInfo);
	}

	@Transactional
	public void updateStock(ProductRequest request) {
		Product productInfo = request.getProductInfo();
		String productId = productInfo.getProductId();

		if (productId != null) {
			if (productRepository.existsByProductId(productId)) {
				productRepository.save(productInfo);
				supplierProductForOwnerProductRepository.save(request.getSupplierInfo());
			} else {
				throw new RuntimeException("Requested product id does not exist");
			}
		} else {
			throw new RuntimeException("Requested product id is null");
		}
	}

	@Transactional
	public void deleteStock(String productId) {
		if (productId != null) {
			if (productRepository.existsByProductId(productId)) {
				productRepository.deleteByProductId(productId);
			} else {
				throw new RuntimeException("Requested product id does not exist");
			}
		} else {
			throw new RuntimeException("Requested product id is null");
		}
	}
}
