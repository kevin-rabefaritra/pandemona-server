package studio.startapps.pandemona.service.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.startup.StartupEndpoint;
import org.springframework.boot.info.JavaInfo;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.service.AnalyticsService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final Logger logger;
    private final MetricsEndpoint metricsEndpoint;
    private final StartupEndpoint startupEndpoint;
    private final InfoEndpoint infoEndpoint;

    private final String appVersion;

    public AnalyticsServiceImpl(
            MetricsEndpoint metricsEndpoint,
            StartupEndpoint startupEndpoint,
            InfoEndpoint infoEndpoint,
            @Value("${spring.application.version}") String appVersion) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.metricsEndpoint = metricsEndpoint;
        this.startupEndpoint = startupEndpoint;
        this.infoEndpoint = infoEndpoint;
        this.appVersion = appVersion;
    }

    @Override
    public List<Map.Entry<String, Integer>> getEndpointsCalls(String[] endpoints) {
        List<Map.Entry<String, Integer>> result = new ArrayList<>();

        List.of(endpoints).forEach((endpointURI) -> {
            MetricsEndpoint.MetricDescriptor metricDescriptor = this.metricsEndpoint.metric("http.server.requests", List.of("uri:" + endpointURI));
            if (metricDescriptor != null) {
                for (MetricsEndpoint.Sample sample : metricDescriptor.getMeasurements()) {
                    if (sample.getStatistic().name().equals("COUNT")) {
                        result.add(Map.entry(endpointURI, sample.getValue().intValue()));
                    }
                }
            }
        });
        return result.stream().sorted(Map.Entry.comparingByValue()).toList();
    }

    @Override
    public LocalDateTime getStartupDateTime() {
        StartupEndpoint.StartupDescriptor descriptor = this.startupEndpoint.startupSnapshot();
        Instant startTime = descriptor.getTimeline().getStartTime();
        return LocalDateTime.ofInstant(startTime, ZoneId.systemDefault());
    }

    @Override
    public List<Map.Entry<String, String>> getApplicationInfo() {
        List<Map.Entry<String, String>> result = new ArrayList<>();
        Map<String, Object> info = this.infoEndpoint.info();

        result.add(Map.entry("App version", this.appVersion));

        JavaInfo javaInfo = (JavaInfo) info.get("java");
        result.add(Map.entry("Java version", javaInfo.getVersion()));
        result.add(Map.entry("JVM", javaInfo.getJvm().getName()));

        return result;
    }
}
