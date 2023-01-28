package tw.com.ispan.eeit48.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.model.OrdersBean;
import tw.com.ispan.eeit48.service.OrderDetailService;
import tw.com.ispan.eeit48.service.AuthService;

@RestController
@RequestMapping(path = {"/views/ordersell"})
public class OrderDetailConrtoller {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    AuthService authService;
    int userId;

    @PostMapping
    public String ShowAll() {
        userId = authService.getCurrentUserId();
        String result = "NG";
        if (userId > 0) {
            result = orderDetailService.ShowAll(userId);
        }
        return result;
    }

    @PostMapping(path = {"/update"})
    public String Update(@RequestBody OrdersBean ordersBean) throws Exception {
        userId = authService.getCurrentUserId();
        String result = "NG";
        if (userId > 0) {
            result = orderDetailService.Update(userId, ordersBean);
        }
        return result;
    }
}
