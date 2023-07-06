package tw.com.ispan.eeit48.mainFunction.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainFunction.model.table.Account;
import tw.com.ispan.eeit48.mainFunction.model.view.Product_Order_OrderDetail;
import tw.com.ispan.eeit48.mainFunction.repository.table.AccountRepository;
import tw.com.ispan.eeit48.mainFunction.repository.view.Product_Order_OrderDetailRepository;
import static tw.com.ispan.eeit48.mainFunction.service.AuthService.getCurrentUserId;

@Service
public class StatementService {
	@Autowired
	private Product_Order_OrderDetailRepository productOrderOrderDetailRepository;
	@Autowired
	private AccountRepository accountRepository;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<Map<String, Object>> getStatement(String orderTime, String completeOrderTime, int buyerId) throws ParseException {
		int userId = getCurrentUserId();
		List<Map<String, Object>> list = new ArrayList<>();
		Date dateOfOrderTime = sdf.parse(orderTime);
		Date dateOfCompleteOrderTime = sdf.parse(completeOrderTime);

		List<Product_Order_OrderDetail> orders =
				productOrderOrderDetailRepository.findAllByOwnerIdAndBuyerIdAndOrderStatusAndOrderTimeBetweenAndCompleteOrderTimeBetween(
						userId, buyerId, 6, dateOfOrderTime, dateOfCompleteOrderTime, dateOfOrderTime, dateOfCompleteOrderTime);

		if (orders != null && !orders.isEmpty()) {
			int total = 0;
			for (Product_Order_OrderDetail order : orders) {
				Account buyerAccount = accountRepository.findOneByAccountId(order.getBuyerId()).get();
				int detail = order.getUnitDealPrice() * order.getOrderQty();

				total = detail + total;
				int finalTotal = total;
				list.add(new HashMap<>() {{
					put("orderId", order.getOrderId());
					put("productNameSpec", order.getProductNameSpec());
					put("orderQty", order.getOrderQty());
					put("unitDealPrice", order.getUnitSellPrice());
					put("orderTime", order.getOrderTime());
					put("contactPerson", buyerAccount.getContactPerson());
					put("address", buyerAccount.getAddress());
					put("companyName", buyerAccount.getCompanyName());
					put("companyPhone", buyerAccount.getCompanyPhone());
					put("taxId", buyerAccount.getTaxId());
					put("fax", buyerAccount.getFax());
					put("detail", detail);
					put("total", finalTotal);
				}});
			}
		}
		return list;
	}
}
