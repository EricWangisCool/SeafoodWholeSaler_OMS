package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.dto.request.StatementRequest;
import tw.com.ispan.eeit48.mainFunction.service.StatementService;

@RestController
@RequestMapping(path = { "/statement" })
public class StatementController {
	@Autowired
    private StatementService statementService;

	@GetMapping
	public void getStatement(@RequestBody StatementRequest request) {
		try {
			statementService.getStatement(
					request.getOrderTime(),
					request.getCompleteOrderTime(),
					request.getBuyerId()
			);
		} catch (Exception e) {

		}
	}
}
