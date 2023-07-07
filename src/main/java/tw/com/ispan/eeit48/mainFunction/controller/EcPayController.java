package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ecpay.payment.integration.ExampleAllInOne;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import java.util.HashMap;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

@RestController
@RequestMapping(path = { "/ecPay" })
public class EcPayController {

	@GetMapping
	public ResponseEntity<?> ecPay() {
		try {
			return ResponseEntity.ok().body(createSuccessResponse(new HashMap<>(){{
				put("key", ExampleAllInOne.genAioCheckOutALL());
			}}));
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
							RequestResponseUtil.BusinessType.EC_PAY.getBusinessTypeCode() + "-0", e.toString())
			);
		}
	}
}
