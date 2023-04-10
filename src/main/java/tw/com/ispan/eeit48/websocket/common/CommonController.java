package tw.com.ispan.eeit48.websocket.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/common"})
public class CommonController {

	@Value("${server.servlet.context-path:nocontext}")
	private String contextPath;
	
	@PostMapping(path = {"/getContextPath"})
	public String getContextPath() {
		return contextPath;
	}
}
