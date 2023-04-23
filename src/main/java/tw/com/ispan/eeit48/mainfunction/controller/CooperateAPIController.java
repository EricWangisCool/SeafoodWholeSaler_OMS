package tw.com.ispan.eeit48.mainfunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.model.View_companyfollowinglist_accountsBean;
import tw.com.ispan.eeit48.mainfunction.service.View_companyfollowinglist_accountsService;

@RestController
@RequestMapping(path = { "/viwes/cooperate" })
public class CooperateAPIController {
	@Autowired
	View_companyfollowinglist_accountsService view_companyfollowinglist_accountsService;

	@PostMapping
	public String ShowAllCompanyFollowingList() {
		try {
			return view_companyfollowinglist_accountsService.selectUserFollowListForCompany();
		} catch (Exception e) {
			return "NG";
		}
	}

	@PostMapping(path = { "/insert" })
	public void InsertNewFollowingList(@RequestBody View_companyfollowinglist_accountsBean dataRequest) {
		view_companyfollowinglist_accountsService.userFollowNewCompany(dataRequest.getCompanyname());
	}
}
