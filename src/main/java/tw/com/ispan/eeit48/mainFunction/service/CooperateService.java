package tw.com.ispan.eeit48.mainFunction.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainFunction.model.table.Account;
import tw.com.ispan.eeit48.mainFunction.model.table.CompanyFollowingList;
import tw.com.ispan.eeit48.mainFunction.repository.table.AccountRepository;
import tw.com.ispan.eeit48.mainFunction.repository.table.CompanyFollowingListRepository;
import tw.com.ispan.eeit48.mainFunction.repository.view.CompanyFollowingList_AccountRepository;
import tw.com.ispan.eeit48.mainFunction.model.view.CompanyFollowingList_Account;
import static tw.com.ispan.eeit48.common.util.CommonUtil.convertObjectToMap;
import static tw.com.ispan.eeit48.mainFunction.service.AuthService.getCurrentUserId;

@Service
public class CooperateService {
	@Autowired
	private CompanyFollowingList_AccountRepository companyFollowingList_AccountRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CompanyFollowingListRepository companyFollowingListRepository;

	/**
	 * 找到使用者的關聯廠商
	 */
	public List<Map<String, Object>> getUserFollowList() throws Exception {
		int userId = getCurrentUserId();
		List<CompanyFollowingList_Account> beans = companyFollowingList_AccountRepository.findAllByBuyerId(userId);
		if (beans != null && !beans.isEmpty()) {
			List<Map<String, Object>> list = new ArrayList<>();
			beans.forEach(bean -> {
				Map<String, Object> beanMap = convertObjectToMap(bean);
				beanMap.put("supplierId", bean.getSellerId());
				beanMap.put("supplierCompanyName", bean.getCompanyName());
				list.add(beanMap);
			});
			return list;
		} else {
			throw new Exception("Following list not found!");
		}
	}

	public List<CompanyFollowingList_Account> userFollowNewCompany(String companyName) throws Exception {
		int userId = getCurrentUserId();
		List<Account> accounts = accountRepository.findAllByCompanyName(companyName);
		if (accounts != null && !accounts.isEmpty()) {
			int companyId = accounts.get(0).getAccountId();
			CompanyFollowingList newBean = new CompanyFollowingList();
			newBean.setBuyerId(userId);
			newBean.setSellerId(companyId);
			companyFollowingListRepository.save(newBean);
			return companyFollowingList_AccountRepository.findAllByCompanyName(companyName);
		} else {
			throw new Exception("Account not found for selected company!");
		}
	}
}
