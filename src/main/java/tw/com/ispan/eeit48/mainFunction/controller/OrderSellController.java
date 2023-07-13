package tw.com.ispan.eeit48.mainFunction.controller;

import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.model.table.Order;
import tw.com.ispan.eeit48.mainFunction.service.OrderSellService;

@RestController
@RequestMapping(path = {"/orderSell"})
public class OrderSellController {
    @Autowired
    private OrderSellService orderSellService;

    @GetMapping
    public ResponseEntity<?> getUserSellingOrdersInfo() {
        try {
           
            return ResponseEntity.ok().body(createSuccessResponse( orderSellService.getUserSellingOrdersInfo()));
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.ORDER_SELL.getBusinessTypeCode() + "-0", e.toString())
            );
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Order order) {
        try {
        	orderSellService.update(order);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.ORDER_SELL.getBusinessTypeCode() + "-1", e.toString())
            );
        }
    }
}
