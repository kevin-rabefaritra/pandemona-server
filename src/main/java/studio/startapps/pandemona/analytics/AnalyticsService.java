package studio.startapps.pandemona.analytics;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AnalyticsService {
    List<Map.Entry<String, Integer>> getEndpointsCalls(List<String> endpoints);

    LocalDateTime getStartupDateTime();

    List<Map.Entry<String, String>> getApplicationInfo();
}
