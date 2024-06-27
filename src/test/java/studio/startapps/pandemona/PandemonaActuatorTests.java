package studio.startapps.pandemona;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.analytics.AnalyticsService;
import studio.startapps.pandemona.analytics.internal.AnalyticsServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles({"qa"})
public class PandemonaActuatorTests {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testMetricsEndpoint() {
    }
}
