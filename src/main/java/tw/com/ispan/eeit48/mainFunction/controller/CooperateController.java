package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.mainFunction.service.CooperateService;

@RestController
@RequestMapping(path = { "/cooperate" })
public class CooperateController {
	@Autowired
    private CooperateService cooperateService;

	@GetMapping
	public void getUserFollowList() {
		try {
			cooperateService.getUserFollowList();
		} catch (Exception e) {

		}
	}

	@PostMapping
	public void userFollowNewCompany(@RequestBody String companyName) {
		try {
			cooperateService.userFollowNewCompany(companyName);
		} catch (Exception e) {

		}
	}
}
