package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.dto.request.ProductRequest;
import tw.com.ispan.eeit48.mainFunction.service.StockService;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.*;


@RestController
@RequestMapping(path = {"/stock"})
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<?> getProduct() {
        try {
            return ResponseEntity.ok().body(createSuccessResponse(stockService.getProduct()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.STOCK.getBusinessTypeCode() + "-0", e.toString())
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> insertNewProduct(@RequestBody ProductRequest request) {
        try {
            stockService.insertNewStock(request);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.STOCK.getBusinessTypeCode() + "-1", e.toString())
            );
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest request) {
        try {
            stockService.updateStock(request);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.STOCK.getBusinessTypeCode() + "-2", e.toString())
            );
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        try {
            stockService.deleteStock(productId);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.STOCK.getBusinessTypeCode() + "-3", e.toString())
            );
        }
    }
}
