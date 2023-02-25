package tw.com.ispan.eeit48.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.model.AccountsBean;
import tw.com.ispan.eeit48.repository.AccountsRepository;
import tw.com.ispan.eeit48.service.AuthService;
import tw.com.ispan.eeit48.springsecurity.filter.JWTUtil;

@RestController
@RequestMapping(path = { "/login" })
public class LoginApiController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthService authService;
	@Autowired
	private AccountsRepository accountsRepository;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody AccountsBean authRequest) {
		// 驗證用戶，並將userID和authority加入token裡回傳
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getAccount(), authRequest.getPassw()));
			AccountsBean user = authService.findUserByaccount(authRequest.getAccount());
			String token = jwtUtil.createToken(user.getAccountid().toString(), user.getAuthority(), false);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			return ResponseEntity.ok().headers(headers).body(user.getCompanyname());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PostMapping(path = ("/thirdParty"))
	public ResponseEntity<?> loginByThirdParty(@RequestBody AccountsBean authRequest) {
		// 第三方登入_接收前端回傳email
		if (authRequest.getEmail() == null || authRequest.getEmail().length() == 0) {
			System.out.println("login.email.required");
		} else {
			AccountsBean bean = authService.findUserByEmail(authRequest.getEmail());
			if (bean != null) {
				return ResponseEntity.ok().body(bean.toJsonObject().toString());
			}
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("NG");
	}

	@PostMapping(path = ("/getUserAccount"))
	public ResponseEntity<?> getUserAccount() {
		int userId = authService.getCurrentUserId();
		String account = accountsRepository.findOneByAccountid(userId).getAccount();
		return ResponseEntity.ok().body(account);
	}
}
