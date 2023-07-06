package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.mainFunction.model.table.Order;
import tw.com.ispan.eeit48.mainFunction.service.OrderSellService;

@RestController
@RequestMapping(path = {"/orderSell"})
public class OrderSellController {
    @Autowired
    private OrderSellService orderSellService;

    @GetMapping
    public void getUserSellingOrdersInfo() {
        try {
            orderSellService.getUserSellingOrdersInfo();
        } catch (Exception e) {

        }
    }

    @PutMapping
    public void update(@RequestBody Order order) {
        try {
            orderSellService.update(order);
        } catch (Exception e) {

        }
    }
}
