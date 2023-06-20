package tw.com.ispan.eeit48.mainfunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.util.dto.request.ProductRequest;
import tw.com.ispan.eeit48.mainfunction.service.StockService;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.*;

/**
 *  將庫存資訊 & 庫存補貨廠商資訊 進行CRUD
 */
@RestController
@RequestMapping(path = {"/views/addStock"})
public class StockAPIController {
    @Autowired
    private StockService stockService;

    @PostMapping(path = {"/insert"})
    public ResponseEntity<?> insertNewProduct(@RequestBody ProductRequest request) {
        try {
            stockService.insertNewStock(request);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS, RequestResponseUtil.BusinessType.STOCK, e.toString())
            );
        }
    }

    @PostMapping(path = {"/update"})
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest request) {
        try {
            stockService.updateStock(request);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS, RequestResponseUtil.BusinessType.STOCK, e.toString())
            );
        }
    }

    @PostMapping(path = {"/delete"})
    public ResponseEntity<?> deleteProduct(@RequestBody Integer productId) {
        try {
            stockService.deleteStock(productId);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS, RequestResponseUtil.BusinessType.STOCK, e.toString())
            );
        }
    }
}
