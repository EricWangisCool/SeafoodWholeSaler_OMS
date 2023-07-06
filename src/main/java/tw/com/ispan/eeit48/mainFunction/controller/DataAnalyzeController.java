package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.dto.request.AnalyzeOrderRequest;
import tw.com.ispan.eeit48.mainFunction.service.DataAnalyzeService;

@RestController
@RequestMapping(path = {"/analyze"})
public class DataAnalyzeController {
    @Autowired
    private DataAnalyzeService analyzeService;

    @GetMapping
    public void getUserOrdersByTime(@RequestBody AnalyzeOrderRequest request) {
        try {
            analyzeService.getUserOrdersByTime(request.getOrderTime(), request.getCompleteOrderTime());
        } catch (Exception e) {

        }
    }
}
