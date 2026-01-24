package studio.startapps.pandemona.stats;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;
import studio.startapps.pandemona.stats.internal.EndpointCounterAggregate;
import studio.startapps.pandemona.stats.internal.EndpointCounterUsage;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(EndpointController.class)
class EndpointCounterControllerTest extends AbstractControllerTest {

    @MockitoBean
    EndpointCounterService endpointCounterService;

    @Test
    @WithMockUser
    void adminFetchEndpointCountSummaryShouldBeOk() throws Exception {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 1);
        given(endpointCounterService.findSummary(startDate, endDate)).willReturn(
            List.of(
                EndpointCounterAggregate.builder().period(LocalDate.of(2025, 1, 1)).usage(
                    List.of(new EndpointCounterUsage("/api/some-endpoint", 10L), new EndpointCounterUsage("/api/another-endpoint", 20L))
                ).build(),
                EndpointCounterAggregate.builder().period(LocalDate.of(2025, 1, 15)).usage(
                        List.of(new EndpointCounterUsage("/api/some-endpoint", 0L), new EndpointCounterUsage("/api/another-endpoint", 10L))
                ).build()
            )
        );

        mockMvc.perform(
                get("/api/endpoints/summary")
                        .queryParam("start", "2025-01-01")
                        .queryParam("end", "2025-02-01")
        ).andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {"period": "2025-01-01", "usage": [{"endpoint": "/api/some-endpoint", "count": 10}, {"endpoint": "/api/another-endpoint", "count": 20}]},
                            {"period": "2025-01-15", "usage": [{"endpoint": "/api/some-endpoint", "count": 0}, {"endpoint": "/api/another-endpoint", "count": 10}]}
                        ]
                        """));

        verify(endpointCounterService).findSummary(startDate, endDate);
    }
}
