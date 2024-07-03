package studio.startapps.pandemona.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AnalyticsService {
    List<Map.Entry<String, Integer>> getEndpointsCalls(String[] endpoints);

    LocalDateTime getStartupDateTime();

    List<Map.Entry<String, String>> getApplicationInfo();
}
