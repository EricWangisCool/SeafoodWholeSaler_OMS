package tw.com.ispan.eeit48.mainFunction.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.service.NewsService;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

@RestController
@RequestMapping(path = {"/news"})
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PutMapping
    public ResponseEntity<?> updateUserMessages(@RequestBody List<Integer> messageIds) {
        try {
            newsService.updateMessagesToRead(messageIds);
            return ResponseEntity.ok().body(createSuccessResponse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.STOCK.getBusinessTypeCode() + "-0", e.toString())
            );
        }
    }
}
