package studio.startapps.pandemona.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/analytics")
public class AnalyticsController {

    private final static int TOP_RESULTS = 10;

    private Logger logger;

    @Value("#{'${pandemonium.actuator.metrics.uris}'.split(',')}")
    private List<String> observableEndpoints;

    private AnalyticsService analyticsService;

    public AnalyticsController(RestTemplateBuilder restTemplateBuilder, AnalyticsService analyticsService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.analyticsService = analyticsService;
    }

    @GetMapping(path = "")
    public String analytics(Model model) {
        List<Map.Entry<String, Integer>> endpoints = this.analyticsService.getEndpointsCalls(this.observableEndpoints);
        LocalDateTime startupDateTime = this.analyticsService.getStartupDateTime();
        List<Map.Entry<String, String>> applicationInfo = this.analyticsService.getApplicationInfo();

        model.addAttribute("endpointsUsage", endpoints);
        model.addAttribute("startupDateTime", startupDateTime);
        model.addAttribute("applicationInfo", applicationInfo);
        return "analytics/index";
    }
}
