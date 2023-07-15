package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.service.DataAnalyzeService;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

@RestController
@RequestMapping(path = {"/analyze"})
public class DataAnalyzeController {
    @Autowired
    private DataAnalyzeService analyzeService;

    @GetMapping
    public ResponseEntity<?> getUserOrdersByTime(@RequestParam String orderTime, @RequestParam String completeOrderTime) {
        try {
            return ResponseEntity.ok().body(createSuccessResponse(analyzeService.getUserOrdersByTime(orderTime, completeOrderTime)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.DATA_ANALYZE.getBusinessTypeCode() + "-0", e.toString())
            );
        }
    }
}
