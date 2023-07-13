package tw.com.ispan.eeit48.mainFunction.controller;

import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.dto.request.UpdateOrderRequest;
import tw.com.ispan.eeit48.common.dto.response.CommonResponse;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.service.OrderBuyService;

@RestController
@RequestMapping(path = {"/orderBuy"})
public class OrderBuyController {
    @Autowired
    private OrderBuyService orderBuyService;

    @GetMapping
    public ResponseEntity<?> findUserRequestedOrders() {
        try {
            
            return ResponseEntity.ok().body(createSuccessResponse(orderBuyService.findUserRequestedOrders()));
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.ORDER_BUY.getBusinessTypeCode()+ "-0",e.toString())
            );
        }
		
    }

    @GetMapping(path = "/orderDetail")
    public ResponseEntity<?> findOrderDetailByOrderId(@RequestParam String orderId) {
        try {
            
            return ResponseEntity.ok().body(createSuccessResponse(orderBuyService.findOrderDetailByOrderId(orderId)));
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.ORDER_BUY.getBusinessTypeCode()+ "-1",e.toString())
            );
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody UpdateOrderRequest request) {
        try {
           orderBuyService.updateOrder(request);        
           return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.ORDER_BUY.getBusinessTypeCode()+ "-2",e.toString())
            );
        }
		
    }
}
