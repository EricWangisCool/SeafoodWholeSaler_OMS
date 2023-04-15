package tw.com.ispan.eeit48.mainfunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.model.OrdersBean;
import tw.com.ispan.eeit48.mainfunction.service.OrderSellService;
import tw.com.ispan.eeit48.mainfunction.service.AuthService;

@RestController
@RequestMapping(path = {"/views/ordersell"})
public class OrderSellController {
    @Autowired
    OrderSellService orderSellService;
    @Autowired
    AuthService authService;
    int userId;

    @PostMapping
    public ResponseEntity<?> ShowAll() {
        userId = authService.getCurrentUserId();
        String result = orderSellService.ShowAll(userId);

        return result != null ?  ResponseEntity.ok().body(result) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("NG");
    }

    @PostMapping(path = {"/update"})
    public ResponseEntity<?>  Update(@RequestBody OrdersBean ordersBean) {
        userId = authService.getCurrentUserId();
        String result = orderSellService.Update(userId, ordersBean);

        return "OK".equals(result) ? ResponseEntity.ok().body(result) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}