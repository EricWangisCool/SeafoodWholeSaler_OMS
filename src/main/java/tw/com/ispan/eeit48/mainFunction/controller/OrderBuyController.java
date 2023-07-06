package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.dto.request.OrderRequest;
import tw.com.ispan.eeit48.common.dto.request.UpdateOrderRequest;
import tw.com.ispan.eeit48.mainFunction.service.OrderBuyService;

@RestController
@RequestMapping(path = {"/orderBuy"})
public class OrderBuyController {
    @Autowired
    private OrderBuyService orderBuyService;

    @GetMapping
    public void findUserRequestedOrders() {
        try {
            orderBuyService.findUserRequestedOrders();
        } catch (Exception e) {

        }
    }

    @GetMapping(path = "/orderDetail")
    public void findOrderDetailByOrderId(@RequestBody OrderRequest request) {
        try {
            orderBuyService.findOrderDetailByOrderId(request.getOrderId());
        } catch (Exception e) {

        }
    }

    @PutMapping
    public void updateOrder(@RequestBody UpdateOrderRequest request) {
        try {
           orderBuyService.updateOrder(request);
        } catch (Exception e) {

        }
    }
}
