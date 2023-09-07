package tw.com.ispan.eeit48.special.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PingService {
    private final RestTemplate restTemplate;

    @Autowired
    public PingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String pingApi() {
        ResponseEntity<String> response = restTemplate.getForEntity("https://nsy.onrender.com/ping", String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("API request failed with status code: " + response.getStatusCode());
        }
    }
}
