package tw.com.ispan.eeit48.controller;

import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.domain.CompanyFollowingListBean;
import tw.com.ispan.eeit48.domain.View_companyfollowinglist_accountsBean;
import tw.com.ispan.eeit48.service.View_companyfollowinglist_accountsService;

@RestController
@RequestMapping(path = { "/viwes/cooperate" })

public class CooperateAPIController {
	@Autowired
	View_companyfollowinglist_accountsService view_companyfollowinglist_accountsService;

	@PostMapping
	public String ShowAllCompanyFollowingList(@RequestBody CompanyFollowingListBean dataRequest, HttpSession session) {

		AccountsBean beanofaccount = (AccountsBean) session.getAttribute("user");

		String beans = view_companyfollowinglist_accountsService.SelectAll(beanofaccount.getAccountid());
		return beans;
	}

	@PostMapping(path = { "/insert" })
	public void InsertNewFollowingList(@RequestBody View_companyfollowinglist_accountsBean dataRequest,
			HttpSession session) {
		AccountsBean beanofaccount = (AccountsBean) session.getAttribute("user");

		JSONArray beans = view_companyfollowinglist_accountsService.InsertNew(beanofaccount.getAccountid(),
				dataRequest.getCompanyname());

	}

}
