package tw.com.ispan.eeit48.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.OrdersBean;
import tw.com.ispan.eeit48.domain.SortComparator;
import tw.com.ispan.eeit48.domain.View_product_order_orderdetailsBean;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.repository.OrderDetailsRepositrory;
import tw.com.ispan.eeit48.repository.OrdersRepository;
import tw.com.ispan.eeit48.repository.ProductRepository;
import tw.com.ispan.eeit48.repository.SupplierProductForOwnerProductRepository;
import tw.com.ispan.eeit48.repository.View_product_order_orderdetailsRepository;

@Service
@Transactional
public class OrderDetailService {
	@Autowired
	private View_product_order_orderdetailsRepository view_product_order_orderdetailsRepository;
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierProductForOwnerProductRepository supplierProductForOwnerProductRepository;
	@Autowired
	private OrderDetailsRepositrory orderDetailsRepositrory;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private SystemNoticeMessageService messageService;
	@Autowired
	private EmailService emailService;

	public String ShowAll(Integer accountid) {
		JSONArray jsonArray = new JSONArray();
		List<JSONObject> sellingOrdersJsonList = new ArrayList();

		// 拿到帳號所有交易狀態為2~7的售出訂單
		List<View_product_order_orderdetailsBean> beans = view_product_order_orderdetailsRepository
				.findAllByOwneridAndOrderstatusBetween(accountid, 2, 7);

		if (beans != null && !beans.isEmpty()) {
			for (View_product_order_orderdetailsBean bean : beans) {
				if (bean != null) {
					JSONObject beanJsonObject = bean.toJsonObject();
					// 算出獲利(profit)及總售價(total)
					beanJsonObject.put("total", bean.getOrderqty() * bean.getUnitsellprice());
					beanJsonObject.put("profit", bean.getOrderqty() * (bean.getUnitsellprice() - bean.getUnitcost()));
					// 依產品ID找可出現貨
					beanJsonObject.put("cansell", productService.findStockOwnByProductId(bean.getProductid()));
					// 放入orderDateSerial，用來排序由大到小
					beanJsonObject.put("orderDateSerial", (int) Long
							.parseLong(bean.getOrdertime().replace(":", "").replace("-", "").replace(" ", "")));
					// 找出買家聯絡資訊
					AccountsBean buyer = accountsRepository.findOneByAccountid(bean.getBuyerid());
					beanJsonObject.put("contactperson", buyer.getContactperson());
					beanJsonObject.put("address", buyer.getAddress());
					beanJsonObject.put("mobile", buyer.getMobile());
					beanJsonObject.put("taxid", buyer.getTaxid());

					// 把前端無用資訊清掉
					beanJsonObject.remove("unitcost");
					beanJsonObject.remove("minsellqty");
					beanJsonObject.remove("autoorderconfirmfunctionstatus");
					beanJsonObject.remove("unitdealprice");
					beanJsonObject.remove("productdesc");
					beanJsonObject.remove("onshelf");
					beanJsonObject.remove("stockqty");
					beanJsonObject.remove("warningqty");
					beanJsonObject.remove("ownerid");
					beanJsonObject.remove("safeqty");
					beanJsonObject.remove("autoorderfunction");

					// 把所有交易紀錄放進去
					sellingOrdersJsonList.add(beanJsonObject);
				}
			}
			// 依照orderDateSerial，由最大到最小排列
			Collections.sort(sellingOrdersJsonList, new SortComparator("orderDateSerial", "int", "desc"));
			for (int i = 0; i < sellingOrdersJsonList.size(); i++) {
				jsonArray.put(sellingOrdersJsonList.get(i));
			}
		}
		return jsonArray.toString();
	}

	public String Update(int accountid, OrdersBean ordersBean) throws Exception {
		// 接單資訊
		String orderid = ordersBean.getOrderid();
		int orderstatus = ordersBean.getOrderstatus(), buyerid = ordersBean.getBuyerid(),
				sellerId = ordersBean.getSellerid();
		// 接單_賣家資訊
		String sellerCompanyName = accountsRepository.findCompanynameByAccountid(sellerId);
		// 接單_買家資訊
		String buyerEmail = accountsRepository.findEmailByAccountid(buyerid);
		
		// 通知買家用屬性，包括寄信(emailService)，以及系統內訊息提示(messageService)
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateSdFormat = sdFormat.format(date);
		String mailText = String.format("賣家%s，於%s，已將訂單編號:%s，狀態調整為=>  ", sellerCompanyName, dateSdFormat, orderid);
		String specialText, systemMessage = "叫貨管理通知: " + mailText;

		switch (orderstatus) {
		case 3:
			ordersBean.setAcceptordertime(date);
			ordersRepository.save(ordersBean);

			specialText = "<訂購商品已通過>";
			emailService.sendMail(buyerEmail, specialText, mailText + specialText);
			messageService.saveNewMessage(systemMessage + specialText, buyerid);

			// 當賣家接單後，系統監控賣家所有開啟自動叫貨的productId是否有達到叫貨條件，有的話就自動叫貨
			List<View_product_order_orderdetailsBean> viewBeans = view_product_order_orderdetailsRepository
					.findAllByOrderidAndOwnerid(orderid, accountid);
			for (View_product_order_orderdetailsBean viewBean : viewBeans) {
//				this.checkAutoOrderPorduct(viewBean.getProductid());
			}
			return "OK";
		case 4:
			ordersBean.setExporttime(date);
			ordersRepository.save(ordersBean);

			specialText = "<訂購商品已出貨>";
			emailService.sendMail(buyerEmail, specialText, mailText + specialText);
			messageService.saveNewMessage(systemMessage + specialText, buyerid);
			return "OK";
		case 5:
			ordersBean.setArriveordertime(date);
			ordersRepository.save(ordersBean);

			specialText = "<訂購商品已送達指定地址>";
			emailService.sendMail(buyerEmail, specialText, mailText + specialText);
			messageService.saveNewMessage(systemMessage + specialText, buyerid);
			return "OK";
		case 7:
			ordersBean.setCancelordertime(date);
			ordersRepository.save(ordersBean);

			specialText = "<訂購商品已取消>";
			emailService.sendMail(buyerEmail, specialText, mailText + specialText);
			messageService.saveNewMessage(systemMessage + specialText, buyerid);
			return "OK";
		}
		return "NG";
	}

	// 當賣家接單後，系統監控賣家所有開啟自動叫貨的productId是否有達到叫貨條件，有的話就自動叫貨
//	public void checkAutoOrderPorduct(int productId) {
//		List<ProductBean> autoBeans = productRepository.findAllByAutoorderfunctionAndOwnerid("Y", accountid);
//		if (autoBeans != null && !autoBeans.isEmpty()) {
//			for (ProductBean autoBean : autoBeans) {
//				// 有開啟自動叫貨的productId
//				int autoProductId = autoBean.getProductid();
//				// 警示庫存數
//				int warningQty = autoBean.getWarningqty();
//				// 安全庫存數
//				int safeQty = autoBean.getSafeqty();
//				// 可出現貨數
//				int stockOwn = productService.findStockOwnByProductId(autoProductId);
//				// 已叫現貨數
//				int callShipping = productService.findCallshippingByProductId(autoProductId);
//
//				// 如果監控到(可出現貨 + 已叫現貨數)<警示庫存，就向上游廠商叫貨
//				if ((stockOwn + callShipping) < warningQty) {
//					// 缺貨數
//					int lackQty = safeQty - (stockOwn + callShipping);
//
//					// 貨品上游ID和對應產品ID
//					int supplierId = supplierProductForOwnerProductRepository.findOneByProductid(autoProductId)
//							.getSupplierid();
//					int supplierProductId = supplierProductForOwnerProductRepository.findOneByProductid(autoProductId)
//							.getSupplierproductid();
//					// 上游產品價格
//					int supplierProductUnitPrice = productRepository.findOneByProductid(supplierProductId)
//							.getUnitsellprice();
//					// 新增一筆新order
//					OrdersBean ob = new OrdersBean();
//					String AutoOdredId = ordersService.createNewBookingOrderId(accountid);
//					ob.setOrderid(AutoOdredId);
//					ob.setOrderstatus(2);
//					ob.setPaymentterms("月結");
//					ob.setOrdertime(new java.util.Date());
//					ob.setBuyerid(accountid);
//					ob.setSellerid(supplierId);
//					ordersRepository.save(ob);
//					// 設定OrderDetails
//					OrderDetailsBean odb = new OrderDetailsBean();
//					odb.setOrderid(AutoOdredId);
//					odb.setOrderqty(lackQty);
//					odb.setSellerproductid(supplierProductId);
//					odb.setUnitdealprice(supplierProductUnitPrice);
//					orderDetailsRepositrory.save(odb);
//
//					// 自動叫貨的賣家
//					AccountsBean autoSeller = accountsRepository.findOneByAccountid(supplierId);
//					String autoSellerEmail = autoSeller.getEmail();
//					String autoSellerCompanyName = autoSeller.getCompanyname();
//
//					// 自動叫貨的買家
//					AccountsBean autoBuyer = accountsRepository.findOneByAccountid(accountid);
//					String autoBuyerEmail = autoBuyer.getEmail();
//					String autoBuyerCompanyName = autoBuyer.getCompanyname();
//
//					// 寄信
//					emailService.sendMail(autoSellerEmail, "傳送新訂單",
//							String.format("買家%s已送出訂單編號%s，請盡快通過訂單，謝謝", autoBuyerCompanyName, AutoOdredId));
//
//					emailService.sendMail(autoBuyerEmail, "傳送新訂單", String.format("您%s已自動送出一筆編號:%s新訂單，訂購商品為%s，訂購數量:%d",
//							autoBuyerCompanyName, AutoOdredId, "秋刀魚", lackQty));
//
//					// 系統內通知訊息
//					messageService.saveNewMessage("接單管理通知" + autoBuyerCompanyName + "已送出訂單編號" + AutoOdredId + "請盡快通過訂單"
//							+ sdFormat.format(new Date()), supplierId);
//
//					messageService
//							.saveNewMessage(
//									"叫貨管理通知" + "你已送向" + autoSellerCompanyName + "送出一筆訂單,訂單編號:" + AutoOdredId + "訂購商品為"
//											+ supplierProductId + "訂購數量:" + lackQty + sdFormat.format(new Date()),
//									accountid);
//				}
//
//			}
//		}
//
//	}
}
