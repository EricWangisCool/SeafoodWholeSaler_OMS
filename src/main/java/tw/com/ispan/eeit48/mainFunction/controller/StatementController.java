package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.mainFunction.service.StatementService;

@RestController
@RequestMapping(path = { "/statement" })
public class StatementController {
	@Autowired
    private StatementService statementService;

	@GetMapping
	public void getStatement(
			@RequestParam String orderTime, @RequestParam String completeOrderTime, @RequestParam int buyerId) {
		try {
			statementService.getStatement(orderTime, completeOrderTime, buyerId);
		} catch (Exception e) {

		}
	}
}
