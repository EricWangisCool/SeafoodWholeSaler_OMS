package tw.com.ispan.eeit48.mainFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.ispan.eeit48.common.dto.request.CooperateRequest;
import tw.com.ispan.eeit48.common.util.RequestResponseUtil;
import tw.com.ispan.eeit48.mainFunction.service.CooperateService;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createErrorResponse;
import static tw.com.ispan.eeit48.common.util.RequestResponseUtil.createSuccessResponse;

@RestController
@RequestMapping(path = { "/cooperate" })
public class CooperateController {
	@Autowired
    private CooperateService cooperateService;

	@GetMapping
	public ResponseEntity<?> getUserFollowList() {
		try {
			return ResponseEntity.ok().body(createSuccessResponse(cooperateService.getUserFollowList()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
							RequestResponseUtil.BusinessType.COOPERATE.getBusinessTypeCode() + "-0", e.toString())
			);
		}
	}

	@PostMapping
	public ResponseEntity<?> userFollowNewCompany(@RequestBody CooperateRequest cooperateRequest) {
		try {
			cooperateService.userFollowNewCompany(cooperateRequest.getCompanyName());
			return ResponseEntity.ok().body(createSuccessResponse(null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					createErrorResponse(RequestResponseUtil.ErrorFrom.BACKEND_OR_BUSINESS.getErrorFromCode(),
							RequestResponseUtil.BusinessType.COOPERATE.getBusinessTypeCode() + "-1", e.toString())
			);
		}
	}
}
