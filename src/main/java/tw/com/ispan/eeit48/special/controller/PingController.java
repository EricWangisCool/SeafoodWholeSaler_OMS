package tw.com.ispan.eeit48.special.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ping")
public class PingController {
    @GetMapping
    public String ping() {
        return "pong";
    }
}
