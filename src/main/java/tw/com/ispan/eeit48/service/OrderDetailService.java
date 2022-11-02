package tw.com.ispan.eeit48.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.catalina.Contained;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aj.org.objectweb.asm.Type;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.CompanyFollowingListBean;
import tw.com.ispan.eeit48.domain.OrderDetailsBean;
import tw.com.ispan.eeit48.domain.OrderStatusBean;
import tw.com.ispan.eeit48.domain.OrdersBean;
import tw.com.ispan.eeit48.domain.ProductBean;
import tw.com.ispan.eeit48.domain.SortComparator;
import tw.com.ispan.eeit48.domain.SupplierProductForOwnerProductBean;
import tw.com.ispan.eeit48.domain.View_product_order_orderdetailsBean;
import tw.com.ispan.eeit48.domain.Email;
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
	private OrdersRepository ordersReposirory;
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

	

	public String ShowAll(Integer accountid) {
		JSONArray ListofOrder = new JSONArray();
		String[] productnamespec;
		int[] orderstatus;
		int[] productid;
		String[] orderid;
		int[] orderqty;
		int[] unitsellprice;
		int[] buyerid;
		int[] unitcost;
		int[] total;
		int[] profit;
		int[] stockqty;
		String[] ordertime;
		String[] acceptordertime;
		String[] exporttime;
		String[] arriveordertime;
		String[] completeordertime;
		String[] cancelordertime;
		String[] companyname;
		String[] paymentterms;
		String[] address;
		String[] mobile;
		String[] taxid;
		String[] contactperson;
		JSONArray ListofFinal = new JSONArray();
		JSONObject[] obj;
		int[] cansell; // 可出現貨
		int FirstLength = 0;
		ListofFinal.clear();
		ListofOrder.clear();

		List<View_product_order_orderdetailsBean> BeanofView = view_product_order_orderdetailsRepository
				.findAllByOwneridAndOrderstatusBetween(accountid, 2, 7);
		// 拿到帳號的所有交易狀態2-7的訂單

		if (BeanofView != null) {
			for (View_product_order_orderdetailsBean bean : BeanofView) {
				if (bean != null) {
					ListofOrder.put(bean.toJsonObject()); // 把所有交易的紀錄放進去

				}
			}
		}

		FirstLength = ListofOrder.length();
		orderid = new String[FirstLength];
		paymentterms = new String[FirstLength];
		productnamespec = new String[FirstLength];
		productid = new int[FirstLength];
		orderstatus = new int[FirstLength];
		orderqty = new int[FirstLength];
		unitsellprice = new int[FirstLength];
		unitcost = new int[FirstLength];
		buyerid = new int[FirstLength];
		companyname = new String[FirstLength];
		acceptordertime = new String[FirstLength];
		exporttime = new String[FirstLength];
		arriveordertime = new String[FirstLength];
		completeordertime = new String[FirstLength];
		cancelordertime = new String[FirstLength];
		ordertime = new String[FirstLength];
		stockqty = new int[FirstLength];
		cansell = new int[FirstLength];
		companyname = new String[FirstLength];
		paymentterms = new String[FirstLength];
		address = new String[FirstLength];
		mobile = new String[FirstLength];
		taxid = new String[FirstLength];
		contactperson = new String[FirstLength];
		obj = new JSONObject[ListofOrder.length()]; // 新增obj陣列

		for (int i = 0; i < ListofOrder.length(); i++) {
			obj[i] = new JSONObject();
		}
		for (int i = 0; i < ListofOrder.length(); i++) {
			// 把每個欄位的值放進各自的陣列
			paymentterms[i] = (String) ListofOrder.getJSONObject(i).get("paymentterms");
			productnamespec[i] = (String) ListofOrder.getJSONObject(i).get("productnamespec");
			productid[i] = ListofOrder.getJSONObject(i).getInt("productid");
			orderstatus[i] = ListofOrder.getJSONObject(i).getInt("orderstatus");
			orderid[i] = (String) ListofOrder.getJSONObject(i).get("orderid");
			orderqty[i] = ListofOrder.getJSONObject(i).getInt("orderqty");
			unitsellprice[i] = ListofOrder.getJSONObject(i).getInt("unitsellprice");
			unitcost[i] = ListofOrder.getJSONObject(i).getInt("unitcost");
			buyerid[i] = ListofOrder.getJSONObject(i).getInt("buyerid");
			ordertime[i] = (String) ListofOrder.getJSONObject(i).get("ordertime");
			companyname[i] = (String) ListofOrder.getJSONObject(i).get("companyname");
			acceptordertime[i] = (String) ListofOrder.getJSONObject(i).get("acceptordertime");
			exporttime[i] = (String) ListofOrder.getJSONObject(i).getString("exporttime");
			arriveordertime[i] = (String) ListofOrder.getJSONObject(i).getString("arriveordertime");
			completeordertime[i] = (String) ListofOrder.getJSONObject(i).getString("completeordertime");
			cancelordertime[i] = (String) ListofOrder.getJSONObject(i).getString("cancelordertime");
			stockqty[i] = ListofOrder.getJSONObject(i).getInt("stockqty");
			cansell[i] = productService.findStockOwnByProductId(ListofOrder.getJSONObject(i).getInt("productid"));
		}
		total = new int[FirstLength];
		profit = new int[FirstLength];

		for (int i = 0; i < ListofOrder.length(); i++) { // 算出獲利及總花費
			int a = orderqty[i] * unitsellprice[i];
			total[i] = a;
			int b = orderqty[i] * (unitsellprice[i] - unitcost[i]);
			profit[i] = b;

		}

		for (int f = 0; f < ListofOrder.length(); f++) {
			List<AccountsBean> BeanofAccount = accountsRepository.findAllByAccountid(buyerid[f]); // 用買家id找到他的聯絡資訊
			if (BeanofAccount != null) {
				JSONArray ListofAccount = new JSONArray();
				for (AccountsBean bean : BeanofAccount) {
					ListofAccount.put(bean.toJsonObject());
				}
				contactperson[f] = (String) ListofAccount.getJSONObject(0).get("contactperson");
				address[f] = (String) ListofAccount.getJSONObject(0).get("address");
				mobile[f] = (String) ListofAccount.getJSONObject(0).get("mobile");
				taxid[f] = (String) ListofAccount.getJSONObject(0).get("taxid");

			}
		}

		for (int i = 0; i < ListofOrder.length(); i++) {
			obj[i].put("productid", productid[i]);
			obj[i].put("orderid", orderid[i]);
			obj[i].put("productnamespec", productnamespec[i]);
			obj[i].put("orderqty", orderqty[i]);
			obj[i].put("unitsellprice", unitsellprice[i]);
			obj[i].put("total", total[i]);
			obj[i].put("orderstatus", orderstatus[i]);
			obj[i].put("ordertime", ordertime[i]);
			obj[i].put("acceptordertime", acceptordertime[i]);
			obj[i].put("exporttime", exporttime[i]);
			obj[i].put("arriveordertime", arriveordertime[i]);
			obj[i].put("completeordertime", completeordertime[i]);
			obj[i].put("contactperson", contactperson[i]);
			obj[i].put("address", address[i]);
			obj[i].put("companyname", companyname[i]);
			obj[i].put("profit", profit[i]);
			obj[i].put("mobile", mobile[i]);
			obj[i].put("paymentterms", paymentterms[i]);
			obj[i].put("taxid", taxid[i]);
			obj[i].put("cancelordertime", cancelordertime[i]);
			obj[i].put("buyerid", buyerid[i]);
			obj[i].put("cansell", cansell[i]);
			int orderDateSerial = (int) Long.parseLong(ordertime[i].replace(":", "").replace("-", "").replace(" ", ""));
			obj[i].put("orderDateSerial", orderDateSerial);
			ListofFinal.put(obj[i]);
		}
		List<JSONObject> sellingOrdersJsonList = new ArrayList();
		for (int number = 0; number < ListofFinal.length(); number++) {
			sellingOrdersJsonList.add((JSONObject) ListofFinal.get(number));
		}
		ListofFinal.clear();
		Collections.sort(sellingOrdersJsonList, new SortComparator("orderDateSerial", "int", "desc"));
		for (int i = 0; i < sellingOrdersJsonList.size(); i++) {
			ListofFinal.put(sellingOrdersJsonList.get(i));
		}

		System.out.println(ListofFinal);
		return ListofFinal.toString();

	}

	public String Update(Integer accountid, String orderid, int orderstatus, int buyerid, int sellerid,
			String paymentterms, Date acceptordertime, Date exporttime, Date arriveordertime, Date completeordertime,
			Date cancelordertime, Date ordertime) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String companyname;
		int[] product;
		int[] producthasauto;
		int[] stockqty;
		int[] warningqty;
		int[] safeqty;
		String newOrderId = null;
		JSONObject[] obj;
		JSONArray listc = new JSONArray();
		int CountofWarningqty = 0;
		int ProductHasAuto = 0;
		int NumberofSafeqty = 0;
		int CanSell = 0;
		int HasOrderqty = 0;
		int Total = 0;
		int[] productid;
		List<OrdersBean> BeanofOrder = ordersReposirory.findAllByOrderid(orderid); // 依訂單id拿到單筆訂單的全部資訊
		if (BeanofOrder != null) {
			JSONArray list = new JSONArray();
			for (OrdersBean bean : BeanofOrder) {
				list.put(bean.toJsonObject());
			}

			int TempofBuyerid = (int) list.getJSONObject(0).get("buyerid");

			List<AccountsBean> BeanofAccount = accountsRepository.findAllByAccountid(TempofBuyerid);
			if (BeanofAccount != null) {
				JSONArray lista = new JSONArray();
				for (AccountsBean bean : BeanofAccount) {
					lista.put(bean.toJsonObject());
				}

				int cc = list.length();

				productid = new int[cc];
				String email = (String) lista.getJSONObject(0).get("email");
				companyname = (String) lista.getJSONObject(0).get("companyname");

				Email e1 = new Email();
				Date SetOrderTime = ordertime;
				Date SetAcceptOrderTime = acceptordertime;
				Date SetExporTtime = exporttime;
				Date SetArriveOrderTime = arriveordertime;
				Date SetCompleteOrderTime = completeordertime;
				Date SetCancelOrderTime = cancelordertime;
				OrdersBean up = new OrdersBean();

				if (orderstatus == 3) {
					try {
						String mailText = "賣家" + companyname + "已通過訂單編號" + orderid + "將盡快出貨";
						e1.sendMail(email, "訂單通過", mailText);
						System.out.println("mision complete");

						messageService.saveNewMessage("系統公告" + mailText +sdFormat.format(new Date()), TempofBuyerid);
					} catch (MessagingException e11) {
						// TODO Auto-generated catch block
						e11.printStackTrace();
					}
					up.setOrderid(orderid);
					up.setOrderstatus(orderstatus);
					up.setBuyerid(buyerid);
					up.setSellerid(sellerid);
					up.setPaymentterms(paymentterms);
					up.setOrdertime(SetOrderTime);
					up.setAcceptordertime(new java.util.Date());
					ordersReposirory.save(up);
					JSONArray ListofAutoProducts = new JSONArray();
					List<ProductBean> AutoProducts = productRepository.findAllByAutoorderfunctionAndOwnerid("Y",
							accountid);
					if (AutoProducts != null) {

						for (ProductBean Product : AutoProducts) {
							ListofAutoProducts.put(Product.toJsonObject());
						}
						obj = new JSONObject[ListofAutoProducts.length()]; // 新增obj陣列
						for (int i = 0; i < ListofAutoProducts.length(); i++) {
							obj[i] = new JSONObject();
						}
						int LengthofListofAutoProducts = ListofAutoProducts.length();
						producthasauto = new int[LengthofListofAutoProducts];
						stockqty = new int[LengthofListofAutoProducts];
						warningqty = new int[LengthofListofAutoProducts];
						safeqty = new int[LengthofListofAutoProducts];

						for (int z = 0; z < ListofAutoProducts.length(); z++) {
							producthasauto[z] = ListofAutoProducts.getJSONObject(z).getInt("productid");
							stockqty[z] = ListofAutoProducts.getJSONObject(z).getInt("stockqty");
							warningqty[z] = ListofAutoProducts.getJSONObject(z).getInt("warningqty");
							safeqty[z] = ListofAutoProducts.getJSONObject(z).getInt("safeqty");
						}

						for (int i = 0; i < ListofAutoProducts.length(); i++) {
							obj[i].put("producthasauto", producthasauto[i]);
							obj[i].put("stockqty", stockqty[i]);
							obj[i].put("warningqty", warningqty[i]);
							obj[i].put("safeqty", safeqty[i]);
							listc.put(obj[i]);
						}
						String AutoOdredId = ordersService.createNewBookingOrderId(accountid);

						for (int z = 0; z < listc.length(); z++) {
							CountofWarningqty = (int) listc.getJSONObject(z).get("warningqty");
							ProductHasAuto = (int) listc.getJSONObject(z).get("producthasauto");
							NumberofSafeqty = (int) listc.getJSONObject(z).get("safeqty");
							CanSell = productService.findStockOwnByProductId(ProductHasAuto);
							HasOrderqty = productService.findCallshippingByProductId(ProductHasAuto);

							Total = CanSell + HasOrderqty;
							if (Total < CountofWarningqty) {
								List<SupplierProductForOwnerProductBean> FindReplenishment = supplierProductForOwnerProductRepository
										.findAllByProductid(ProductHasAuto); // 尋找上游的補貨對象

								ProductBean ppb = productRepository
										.findOneByProductid(FindReplenishment.get(0).getSupplierproductid()); // 找到
								OrdersBean ob = new OrdersBean();// 新增1筆order上去
								ob.setOrderid(AutoOdredId);
								ob.setOrderstatus(2);
								ob.setPaymentterms("月結");
								ob.setOrdertime(new java.util.Date());
								ob.setBuyerid(accountid);
								ob.setSellerid(2);
								ordersReposirory.save(ob);
								OrderDetailsBean odb = new OrderDetailsBean(); // 設定OrderDetails
								System.out.println("FindReplenishment.get(0).getSupplierproductid()"
										+ FindReplenishment.get(0).getSupplierproductid());
								int TempofSupplierid = FindReplenishment.get(0).getSupplierproductid();
								String TempofString = (TempofSupplierid + "").substring(0, 1);
								int i = Integer.parseInt(TempofString);
								List<AccountsBean> BeanofAutoSeller = accountsRepository.findAllByAccountid(i); // 自動叫貨的賣家
								List<AccountsBean> BeanofAutoBuyer = accountsRepository.findAllByAccountid(accountid); // 自動叫貨的買家
								JSONArray ListofBuyer = new JSONArray();
								JSONArray ListofSeller = new JSONArray();
								odb.setOrderid(AutoOdredId);
								odb.setOrderqty((NumberofSafeqty - Total));
								odb.setSellerproductid(FindReplenishment.get(0).getSupplierproductid());
								odb.setUnitdealprice(ppb.getUnitsellprice());
								orderDetailsRepositrory.save(odb);
								if (BeanofAutoBuyer != null) {
									for (AccountsBean bean : BeanofAutoBuyer) {
										ListofBuyer.put(bean.toJsonObject());
									}
								}
								if (BeanofAutoSeller != null) {

									for (AccountsBean bean : BeanofAutoSeller) {
										ListofSeller.put(bean.toJsonObject());
									}
								}
								try {
									System.out.println("AutoOdredId" + AutoOdredId);
									// xx是賣家 xc是買家
									e1.sendMail(ListofSeller.getJSONObject(0).getString("email"), "傳送新訂單",
											"買家" + ListofBuyer.getJSONObject(0).getString("companyname") + "已送出訂單編號"
													+ AutoOdredId + "請盡快通過訂單");
									e1.sendMail(ListofBuyer.getJSONObject(0).getString("email"), "傳送新訂單",
											"您" + ListofBuyer.getJSONObject(0).getString("companyname")
													+ "已自動送出一筆新訂單,編號:" + AutoOdredId + "訂購商品為"
													+ FindReplenishment.get(0).getSupplierproductid() + "訂購數量:"
													+ (NumberofSafeqty - Total));

									messageService.saveNewMessage(
											"接單管理通知" + ListofBuyer.getJSONObject(0).getString("companyname") + "已送出訂單編號"
													+ AutoOdredId + "請盡快通過訂單" + sdFormat.format(new Date()),
											i);

									messageService.saveNewMessage("叫貨管理通知" + "你已送向"
											+ ListofSeller.getJSONObject(0).getString("companyname") + "送出一筆訂單,訂單編號:"
											+ AutoOdredId + "訂購商品為" + FindReplenishment.get(0).getSupplierproductid()
											+ "訂購數量:" + (NumberofSafeqty - Total) + sdFormat.format(new Date()), accountid);
								} catch (MessagingException e11) {
									// TODO Auto-generated catch block
									e11.printStackTrace();
								}

							}
						}
					}

				} else if (orderstatus == 4) {
					up.setOrderid(orderid);
					up.setOrderstatus(orderstatus);
					up.setBuyerid(buyerid);
					up.setSellerid(sellerid);
					up.setPaymentterms(paymentterms);
					up.setOrdertime(SetOrderTime);
					up.setAcceptordertime(SetAcceptOrderTime);
					up.setExporttime(new java.util.Date());
					try {
						e1.sendMail(email, "訂購商品已出貨", "賣家" + companyname + "於" + sdFormat.format(new java.util.Date())
								+ "已將訂單編號:" + orderid + "出貨");
						messageService.saveNewMessage(
								"叫貨管理通知" + "賣家" + companyname + "於" + sdFormat.format(new java.util.Date()) + "已將訂單編號:"
										+ orderid + "出貨" + sdFormat.format(new Date()),
								TempofBuyerid);
					} catch (MessagingException e11) {
						// TODO Auto-generated catch block
						e11.printStackTrace();
					}
					ordersReposirory.save(up);
					return new java.util.Date().toString();
				} else if (orderstatus == 5) {
					up.setOrderid(orderid);
					up.setOrderstatus(orderstatus);
					up.setBuyerid(buyerid);
					up.setSellerid(sellerid);
					up.setPaymentterms(paymentterms);
					up.setOrdertime(SetOrderTime);
					up.setAcceptordertime(SetAcceptOrderTime);
					up.setExporttime(SetExporTtime);
					up.setArriveordertime(new java.util.Date());
					messageService
							.saveNewMessage("叫貨管理通知" + "賣家" + companyname + "於" + sdFormat.format(new java.util.Date())
									+ "已將訂單編號:" + orderid + "送達指定收地址" +sdFormat.format(new Date()), TempofBuyerid);
					ordersReposirory.save(up);
				} else if (orderstatus == 7) {

					up.setOrderid(orderid);
					up.setOrderstatus(orderstatus);
					up.setBuyerid(buyerid);
					up.setSellerid(sellerid);
					up.setPaymentterms(paymentterms);
					up.setOrdertime(SetOrderTime);
					up.setAcceptordertime(SetAcceptOrderTime);
					up.setExporttime(SetExporTtime);
					up.setArriveordertime(SetArriveOrderTime);
					up.setCancelordertime(new java.util.Date());
					try {
						e1.sendMail(email, "賣家取消訂單", "賣家" + companyname + "於" + sdFormat.format(new java.util.Date())
								+ "已將訂單編號:" + orderid + "取消");
						System.out.println("mision complete");
						messageService.saveNewMessage(
								"叫貨管理通知" + "賣家" + companyname + "於" + sdFormat.format(new java.util.Date()) + "已將訂單編號:"
										+ orderid + "取消訂單" + sdFormat.format(new Date()),
								TempofBuyerid);
					} catch (MessagingException e11) {
						// TODO Auto-generated catch block
						e11.printStackTrace();
					}
					ordersReposirory.save(up);
				}

			}
		}
		return "OK";

	}
}
