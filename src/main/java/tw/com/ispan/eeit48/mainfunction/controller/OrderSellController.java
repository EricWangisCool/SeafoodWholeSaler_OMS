package tw.com.ispan.eeit48.mainfunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.model.table.Order;
import tw.com.ispan.eeit48.mainfunction.service.OrderSellService;

@RestController
@RequestMapping(path = {"/views/orderSell"})
public class OrderSellController {
    @Autowired
    OrderSellService orderSellService;

    @PostMapping
    public ResponseEntity<?> ShowAll() {
        String result = orderSellService.ShowAll();
        return result != null ?  ResponseEntity.ok().body(result) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("NG");
    }

    @PostMapping(path = {"/update"})
    public ResponseEntity<?>  Update(@RequestBody Order order) {
        String result = orderSellService.Update(order);
        return "OK".equals(result) ? ResponseEntity.ok().body(result) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}
