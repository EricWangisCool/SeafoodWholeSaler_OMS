package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.mainFunction.service.DataAnalyzeService;

@RestController
@RequestMapping(path = {"/analyze"})
public class DataAnalyzeController {
    @Autowired
    private DataAnalyzeService analyzeService;

    @GetMapping
    public void getUserOrdersByTime(@RequestParam String orderTime, @RequestParam String completeOrderTime) {
        try {
            analyzeService.getUserOrdersByTime(orderTime, completeOrderTime);
        } catch (Exception e) {

        }
    }
}
