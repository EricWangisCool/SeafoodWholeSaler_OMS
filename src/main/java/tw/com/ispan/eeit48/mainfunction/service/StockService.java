package tw.com.ispan.eeit48.mainfunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainfunction.model.table.Product;
import tw.com.ispan.eeit48.mainfunction.model.table.SupplierProductForOwnerProduct;
import tw.com.ispan.eeit48.mainfunction.requestResponse.model.request.ProductRequest;
import tw.com.ispan.eeit48.mainfunction.repository.ProductRepository;
import tw.com.ispan.eeit48.mainfunction.repository.SupplierProductForOwnerProductRepository;
import static tw.com.ispan.eeit48.mainfunction.service.AuthService.getCurrentUserId;

@Service
@Transactional
public class StockService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;

	public void insertNewStock(ProductRequest request) {
		Product productInfo = request.getProductInfo();
		SupplierProductForOwnerProduct supplierInfo = request.getSupplierInfo();
		int userId = getCurrentUserId();

		int newProductId;
		Integer lastProductId = productRepository.findLastProductIdByOwnerId(userId);
		if (lastProductId != null) {
			newProductId = lastProductId + 1;
		} else {
			// 預設以 userId + 00001 作為第一筆productId
			newProductId = Integer.parseInt(String.format("%d%05d", userId, 1));
		}

		productInfo.setOwnerId(userId);
		productInfo.setProductId(newProductId);
		supplierInfo.setProductid(newProductId);

		productRepository.save(productInfo);
		supplierProductForOwnerProductRepository.save(supplierInfo);
	}

	public void updateStock(ProductRequest request) {
		Product productInfo = request.getProductInfo();
		Integer productId = productInfo.getProductId();

		if (productId != null) {
			if (productRepository.existsById(productId)) {
				productInfo.setOwnerId(getCurrentUserId());
				productRepository.save(productInfo);
			}
			throw new RuntimeException("ProductId does not exist");
		}
		throw new RuntimeException("ProductId is null");
	}

	public void deleteStock(Integer productId) {
		if (productId != null) {
			if (productRepository.existsById(productId)) {
				if (productRepository.deleteByProductId(productId) < 1) {
					throw new RuntimeException("Delete quantity does not match request");
				}
			}
			throw new RuntimeException("ProductId does not exist");
		}
		throw new RuntimeException("ProductId is null");
	}
}
