package tw.com.ispan.eeit48.mainfunction.controller;

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
import tw.com.ispan.eeit48.mainfunction.model.table.Account;
import tw.com.ispan.eeit48.mainfunction.repository.AccountsRepository;
import tw.com.ispan.eeit48.mainfunction.service.AuthService;
import tw.com.ispan.eeit48.springsecurity.filter.JWTUtil;
import static tw.com.ispan.eeit48.mainfunction.service.AuthService.getCurrentUserId;

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
	public ResponseEntity<?> login(@RequestBody Account authRequest) {
		// 驗證用戶，並將userID和authority加入token裡回傳
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getAccount(), authRequest.getPassW()));
			Account user = authService.findUserByAccount(authRequest.getAccount());
			String token = jwtUtil.createToken(user.getAccountId().toString(), user.getAuthority(), false);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			return ResponseEntity.ok().headers(headers).body(user.getCompanyName());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PostMapping(path = ("/thirdParty"))
	public ResponseEntity<?> loginByThirdParty(@RequestBody Account authRequest) {
		// 第三方登入_接收前端回傳email
		if (authRequest.getEmail() == null || authRequest.getEmail().length() == 0) {
			System.out.println("login.email.required");
		} else {
			Account bean = authService.findUserByEmail(authRequest.getEmail());
			if (bean != null) {
				return ResponseEntity.ok().body(bean);
			}
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("NG");
	}

	@PostMapping(path = ("/getUserAccount"))
	public ResponseEntity<?> getUserAccount() {
		try {
			return ResponseEntity.ok().body(accountsRepository.findOneByAccountid(getCurrentUserId()).getAccount());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
		}
	}
}
