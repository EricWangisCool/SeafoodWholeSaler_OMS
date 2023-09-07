package tw.com.ispan.eeit48.special.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tw.com.ispan.eeit48.special.service.PingService;

@Component
public class PingScheduler {
    private final PingService pingService;

    @Autowired
    public PingScheduler(PingService pingService) {
        this.pingService = pingService;
    }

    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void pingApiEndpoint() {
        String response = pingService.pingApi();
        System.out.println(response);
    }
}
