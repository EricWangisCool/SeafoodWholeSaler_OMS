package tw.com.ispan.eeit48.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.View_companyfollowinglist_accountsBean;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.repository.View_companyfollowinglist_accountsRepository;

@Service
@Transactional
public class AccountsService {
	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private View_companyfollowinglist_accountsRepository view_companyfollowinglist_accountsrepository;

	@Transactional(readOnly = true)
	public AccountsBean login(String account, String password) {
		Optional<AccountsBean> result = accountsRepository.findOneByaccount(account);
		if (result.isPresent()) {
			if (result.get().getPassw().equals(password)) {
				System.out.println("Enter Successfull");
				return result.get();
			} else {
				System.out.println("Wrong Password");
			}
		} else {
			System.out.println("select bean not exist");
		}
		return null;
	}

	@Transactional(readOnly = true)
	public AccountsBean thirdPartylogin(String email) {
		Optional<AccountsBean> result = accountsRepository.findOneByEmail(email);
		if (result.isPresent()) {
			return result.get();
		} else {
			System.out.println("select bean not exist");
		}
		return null;
	}

	public List<View_companyfollowinglist_accountsBean> findSupplierDataByUserId(int userId) {
		List<View_companyfollowinglist_accountsBean> suppliersData = view_companyfollowinglist_accountsrepository
				.findAllByBuyerid(userId);
		if (suppliersData != null) {
			return suppliersData;
		}
		return null;
	}

}
