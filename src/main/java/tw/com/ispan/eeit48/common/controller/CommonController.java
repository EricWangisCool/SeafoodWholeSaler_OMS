package tw.com.ispan.eeit48.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/common"})
public class CommonController {
	@Value("${server.servlet.context-path}")
	private String contextPath;

	/**
	 * 因為正式台與本地端使用不同context-path，使用此function取得當前contextPath
	 * @return contextPath
	 */
	@GetMapping(path = {"/contextPath"})
	public String getContextPath() {
		return contextPath;
	}
}
