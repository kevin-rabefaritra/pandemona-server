package studio.startapps.pandemona.feed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class FeedServiceTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    FeedService feedService;

    @BeforeEach
    void setup() {
    }

    @DisplayName("Fetch feed should return FeedPage")
    @Test
    @Disabled
    void fetchFeedShouldReturnFeedPage() throws Exception {

    }
}
