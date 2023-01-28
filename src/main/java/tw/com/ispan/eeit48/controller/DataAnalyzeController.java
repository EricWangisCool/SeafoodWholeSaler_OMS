package tw.com.ispan.eeit48.controller;

import java.text.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.service.DataAnalyzeService;
import tw.com.ispan.eeit48.service.AuthService;

@RestController
@RequestMapping(path = {"views/analyze"})
public class DataAnalyzeController {
    @Autowired
    DataAnalyzeService analyzeService;
    @Autowired
    AuthService authService;

    @PostMapping
    public String ShowAll(@RequestBody String request) throws ParseException {
        int userId = authService.getCurrentUserId();

        JSONObject jsonObject = new JSONObject(request);
        String ordertime = (String) jsonObject.get("ordertime");
        String completeordertime = (String) jsonObject.get("completeordertime");
        String beans = analyzeService.ShowAll(userId, ordertime, completeordertime);
        return beans;
    }
}
