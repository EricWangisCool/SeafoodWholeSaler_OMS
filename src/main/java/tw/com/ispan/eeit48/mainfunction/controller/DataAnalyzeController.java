package tw.com.ispan.eeit48.mainfunction.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.ispan.eeit48.mainfunction.service.DataAnalyzeService;

@RestController
@RequestMapping(path = {"views/analyze"})
public class DataAnalyzeController {
    @Autowired
    DataAnalyzeService analyzeService;

    @PostMapping
    public String ShowAll(@RequestBody String request) {
        try {
            JSONObject jsonObject = new JSONObject(request);
            return analyzeService.getUserOrdersByTime(
                    (String) jsonObject.get("ordertime"),
                    (String) jsonObject.get("completeordertime")
            );
        } catch (Exception e) {
            return "NG";
        }
    }
}
