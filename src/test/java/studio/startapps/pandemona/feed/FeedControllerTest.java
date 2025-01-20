package studio.startapps.pandemona.feed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.configuration.SecurityConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MobileFeedController.class)
@Import(SecurityConfig.class)
public class FeedControllerTest {

    @MockBean
    FeedService feedService;

    @MockBean
    AuthenticationService authenticationService;

    @Autowired
    MockMvc mockMvc;

    @DisplayName("Fetch feed should be ok")
    @Test
    void fetchFeedShouldBeOk() throws Exception {
        mockMvc.perform(
            get("/api/mobile/v1/feed")
                    .header(HttpHeaders.ACCEPT_LANGUAGE, "fr")
        ).andExpect(status().isOk());

        verify(feedService).findAll(eq("fr"), any(Pageable.class));
    }
}
