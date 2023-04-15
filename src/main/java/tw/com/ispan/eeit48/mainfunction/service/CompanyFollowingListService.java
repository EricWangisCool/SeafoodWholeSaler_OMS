package tw.com.ispan.eeit48.mainfunction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.eeit48.mainfunction.model.CompanyFollowingListBean;
import tw.com.ispan.eeit48.mainfunction.repository.CompanyFollowingListRepository;

@Service
@Transactional
public class CompanyFollowingListService {
	@Autowired
	private CompanyFollowingListRepository companyFollowingListRepository;

	public List<CompanyFollowingListBean> SelectAll(Integer accontid) {

		List<CompanyFollowingListBean> beans = companyFollowingListRepository.findAllByBuyerid(accontid);
		System.out.println(beans);

		return null;
	}
}
