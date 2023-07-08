package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.model.table.Account;
import tw.com.ispan.eeit48.mainFunction.service.AuthService;
import tw.com.ispan.eeit48.springSecurity.filter.JWTUtil;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

@RestController
@RequestMapping(path = { "/login" })
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthService authService;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody Account authRequest) {
		// 驗證用戶，並將userID和authority加入token裡回傳
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getAccount(), authRequest.getPassWord()));
			Account user = authService.findUserByAccount(authRequest.getAccount());
			String token = jwtUtil.createToken(user.getAccountId().toString(), user.getAuthority(), false);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			return ResponseEntity.ok().headers(headers).body(createSuccessResponse(user.getCompanyName()));
		} catch (Exception e) {
			if (e instanceof BadCredentialsException) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
						createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
								RequestResponseUtil.BusinessType.LOGIN.getBusinessTypeCode() + "-0", e.toString())
				);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
						createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
								RequestResponseUtil.BusinessType.LOGIN.getBusinessTypeCode() + "-1", e.toString())
				);
			}

		}
	}

	@PostMapping(path = ("/thirdParty"))
	public ResponseEntity<?> loginByThirdParty(@RequestBody Account authRequest) {
		try {
			return ResponseEntity.ok().body(createSuccessResponse(
					authService.thirdPartyLogin(authRequest.getEmail())
			));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
					createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
							RequestResponseUtil.BusinessType.LOGIN.getBusinessTypeCode() + "-2", e.toString())
			);
		}
	}

	@GetMapping
	public ResponseEntity<?> getUserAccount() {
		try {
			return ResponseEntity.ok().body(createSuccessResponse(authService.getUserAccount()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
							RequestResponseUtil.BusinessType.LOGIN.getBusinessTypeCode() + "-3", e.toString())
			);
		}
	}
}
