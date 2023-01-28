package tw.com.ispan.eeit48.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.model.View_companyfollowinglist_accountsBean;
import tw.com.ispan.eeit48.service.View_companyfollowinglist_accountsService;
import tw.com.ispan.eeit48.service.AuthService;

@RestController
@RequestMapping(path = { "/viwes/cooperate" })
public class CooperateAPIController {
	@Autowired
	View_companyfollowinglist_accountsService view_companyfollowinglist_accountsService;

	@Autowired
	AuthService authService;

	int userId;

	@PostMapping
	public String ShowAllCompanyFollowingList() {
		userId = authService.getCurrentUserId();
		String beans = view_companyfollowinglist_accountsService.SelectAll(userId);
		return beans;
	}

	@PostMapping(path = { "/insert" })
	public void InsertNewFollowingList(@RequestBody View_companyfollowinglist_accountsBean dataRequest) {
		userId = authService.getCurrentUserId();
		view_companyfollowinglist_accountsService.InsertNew(userId, dataRequest.getCompanyname());
	}
}
