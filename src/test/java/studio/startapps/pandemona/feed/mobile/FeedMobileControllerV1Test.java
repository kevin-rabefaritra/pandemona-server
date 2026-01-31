package studio.startapps.pandemona.feed.mobile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(FeedMobileControllerV1.class)
class FeedMobileControllerV1Test extends AbstractControllerTest {

    @DisplayName("Fetch feed v1 should be ok")
    @Test
    void fetchFeedShouldBeOk() throws Exception {
        mockMvc.perform(
            get("/api/mobile/v1/feed")
                    .header(HttpHeaders.ACCEPT_LANGUAGE, "fr")
        ).andExpect(status().isOk());
    }
}
