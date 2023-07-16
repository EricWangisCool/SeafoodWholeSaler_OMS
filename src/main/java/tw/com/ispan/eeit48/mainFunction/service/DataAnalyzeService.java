package tw.com.ispan.eeit48.mainFunction.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainFunction.model.table.*;
import tw.com.ispan.eeit48.mainFunction.model.view.Product_Order_OrderDetail;
import tw.com.ispan.eeit48.mainFunction.repository.table.AccountRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.ProductClassificationRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.ProductRepository;
import tw.com.ispan.eeit48.mainFunction.repository.view.Product_Order_OrderDetailRepository;
import static tw.com.ispan.eeit48.mainFunction.service.AuthService.getCurrentUserId;

@Service
public class DataAnalyzeService {
	@Autowired
	private Product_Order_OrderDetailRepository product_Order_OrderDetailRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductClassificationRepository productClassificationRepository;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<Map<String, Object>> getUserOrdersByTime(String orderTime, String completeOrderTime) throws Exception {
		int userId = getCurrentUserId();
		List<Map<String, Object>> userOrders = new ArrayList<>();
		Date dateOfOrderTime = sdf.parse(orderTime);
		Date dateOfCompleteOrderTime = sdf.parse(completeOrderTime);

		List<Product_Order_OrderDetail> orders = product_Order_OrderDetailRepository.
				findAllByOwnerIdAndOrderStatusAndOrderTimeBetweenAndCompleteOrderTimeBetween(
						userId, 6, dateOfOrderTime, dateOfCompleteOrderTime, dateOfOrderTime, dateOfCompleteOrderTime);

		if (orders != null && !orders.isEmpty()) {
			orders.forEach(order -> {
				Product product = productRepository.findOneByProductId(order.getProductId());
				Optional<ProductClassification> productClassificationOptional =
						productClassificationRepository.findOneByClassId(product.getProductClassification());

				String buyerCompanyName = accountRepository.findCompanyNameByAccountId(order.getBuyerId());
				userOrders.add(new HashMap<>() {{
					// Seller info
					put("productClassification", product.getProductClassification());
					put("className", productClassificationOptional.isPresent()? productClassificationOptional.get().getClassDesc() : "");
					put("orderQty", order.getOrderQty());
					put("unitSellPrice", order.getUnitSellPrice());
					// Buyer info
					put("buyerId", order.getBuyerId());
					put("companyName", buyerCompanyName);
				}});
			});
		}
		return userOrders;
	}
}
