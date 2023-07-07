package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.dto.request.CreateOrderRequest;
import tw.com.ispan.eeit48.mainFunction.service.GoodsService;

@RestController
@RequestMapping(path = {"/goods"})
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping
    public void getSupplierProductInfo(@RequestParam Integer supplierAccountId) {
        try {
            goodsService.getSupplierProductInfo(supplierAccountId);
        } catch (Exception e) {

        }
    }

    @PostMapping
    public void createNewOrder(@RequestBody CreateOrderRequest request) {
        try {
            goodsService.createNewOrder(request);
        } catch (Exception e) {

        }
    }
}
