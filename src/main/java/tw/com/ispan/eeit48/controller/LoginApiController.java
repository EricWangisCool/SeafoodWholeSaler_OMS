package tw.com.ispan.eeit48.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.domain.AccountsBean;
import tw.com.ispan.eeit48.service.AccountsService;

//登入帳號頁面
@RestController
@RequestMapping(path = { "/login" })
public class LoginApiController {
	@Autowired
	AccountsService accountsService;

	// 接收前端帳密資訊, 登入成功就將該user的bean紀錄在session裡, key = "user"
	@PostMapping
	public String checkAccountPass(@RequestBody AccountsBean dataRequest, HttpSession session) {
		// 接收資料, 驗證資料
		if (dataRequest.getAccount() == null || dataRequest.getAccount().length() == 0) {
			System.out.println("login.username.required");
		} else if (dataRequest.getPassw() == null || dataRequest.getPassw().length() == 0) {
			System.out.println("login.password.required");
			// 確認有收到前端輸入的帳密後, 呼叫model
		} else {
			AccountsBean bean = accountsService.login(dataRequest.getAccount(), dataRequest.getPassw());
			// model執行如果有結果，就將使用者bean以"user"為key紀錄在session裡, 並回傳使用者JSON字串給前端
			if (bean != null) {
				session.setAttribute("user", bean);
				return bean.toJsonObject().toString();
			}
		}
		return "NG";
	}

	// 第三方登入_接收前端回傳email, 登入成功就將該user的bean紀錄在session裡, key = "user"
	@PostMapping(path = ("/thirdParty"))
	public String checkEmail(@RequestBody AccountsBean dataRequest, HttpSession session) {
		// 接收資料, 驗證資料
		if (dataRequest.getEmail() == null || dataRequest.getEmail().length() == 0) {
			System.out.println("login.email.required");
		}
		// 確認有收到前端回傳的email後, 呼叫model
		else {
			AccountsBean bean = accountsService.thirdPartylogin(dataRequest.getEmail());
			// model執行如果有結果，就將使用者bean以"user"為key紀錄在session裡, 並回傳使用者JSON字串給前端
			if (bean != null) {
				session.setAttribute("user", bean);
				return bean.toJsonObject().toString();
			}
		}
		return "NG";
	}
}
