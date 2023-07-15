package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.dto.request.CreateOrderRequest;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.service.GoodsService;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

@RestController
@RequestMapping(path = {"/goods"})
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping
    public ResponseEntity<?> getSupplierProductInfo(@RequestParam Integer supplierAccountId) {
        try {
            return ResponseEntity.ok().body(createSuccessResponse(goodsService.getSupplierProductInfo(supplierAccountId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.GOODS.getBusinessTypeCode() + "-0", e.toString())
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> createNewOrder(@RequestBody CreateOrderRequest request) {
        try {
            goodsService.createNewOrder(request);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.GOODS.getBusinessTypeCode() + "-1", e.toString())
            );
        }
    }
}
