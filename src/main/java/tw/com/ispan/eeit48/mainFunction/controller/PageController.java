package tw.com.ispan.eeit48.mainFunction.controller;

import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.service.PageService;

@RestController
@RequestMapping(path = {"/page"})
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping
    public ResponseEntity<?> getUserMainPageInfo() {
        try {
            return ResponseEntity.ok().body(createSuccessResponse(pageService.getUserMainPageInfo()));
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
                            RequestResponseUtil.BusinessType.PAGE.getBusinessTypeCode()+"0",e.toString())
            );
        }
    }
}
