package tw.com.ispan.eeit48.mainFunction.controller;

import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.service.StatementService;

@RestController
@RequestMapping(path = { "/statement" })
public class StatementController {
	@Autowired
    private StatementService statementService;

	@GetMapping
	public ResponseEntity<?> getStatement(
			@RequestParam String orderTime, @RequestParam String completeOrderTime, @RequestParam int buyerId) {
		try {
			return ResponseEntity.ok().body(createSuccessResponse(statementService.getStatement(orderTime, completeOrderTime, buyerId)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.STATEMENT.getBusinessTypeCode() + "-0", e.toString())
            );
		}
	}
}
