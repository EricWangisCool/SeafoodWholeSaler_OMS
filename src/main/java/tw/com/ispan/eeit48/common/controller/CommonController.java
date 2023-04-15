package tw.com.ispan.eeit48.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/common"})
public class CommonController {
	@Value("${server.servlet.context-path:noContext}")
	private String contextPath;

	/**
	 * 因為正式台url使用prefix，使用此function回傳當前contextPath，本地端部署時則回傳空字串
	 * @return contextPath
	 */
	@PostMapping(path = {"/getContextPath"})
	public String getContextPath() {
		return "noContext".equals(contextPath) ? "" : contextPath;
	}
}
