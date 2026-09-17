package studio.startapps.pandemona.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.auth.internal.AuthTokenSet;
import studio.startapps.pandemona.core.IntegrationTest;
import studio.startapps.pandemona.stats.internal.EndpointCounter;
import studio.startapps.pandemona.stats.internal.EndpointCounterAggregate;
import studio.startapps.pandemona.stats.internal.EndpointCounterRepository;
import studio.startapps.pandemona.stats.internal.EndpointCounterUsage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class EndpointCounterIntegrationTest {

    @Autowired
    EndpointCounterRepository endpointCounterRepository;

    @Autowired
    EndpointCounterService counterService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @Value("${pandemonium.username}") String masterUsername;
    @Value("${pandemonium.password}") String masterPassword;

    @BeforeEach
    void destroy() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "endpoint_counter");
    }

    @Test
    void callTrackedEndpointShouldUpdateEndpointCounter() throws Exception {
        long endpointCounterCount = endpointCounterRepository.count();
        assertThat(endpointCounterCount).isZero();

        mockMvc.perform(get("/api/mobile/v1/on-duty-drugstores")).andExpect(status().isOk());

        List<EndpointCounter> endpointCounterList = endpointCounterRepository.findAll();
        assertThat(endpointCounterList).isNotEmpty();

        EndpointCounter endpointCounter = endpointCounterList.getFirst();
        assertThat(endpointCounter.getRequestEndpoint()).isEqualTo("api/mobile/v1/on-duty-drugstores");
        assertThat(endpointCounter.getRequestCount()).isOne();
    }

    @Test
    void findSummaryByDateShouldBeOk() {
        // save endpoint usages
        List<EndpointCounter> endpointCounterList = List.of(
                EndpointCounter.builder().requestEndpoint("/api/some-endpoint").requestCount(10L).requestDate(LocalDate.of(2025, 1, 1)).build(),
                EndpointCounter.builder().requestEndpoint("/api/some-endpoint").requestCount(7L).requestDate(LocalDate.of(2025, 1, 3)).build(),
                EndpointCounter.builder().requestEndpoint("/api/another-endpoint").requestCount(20L).requestDate(LocalDate.of(2025, 1, 1)).build(),
                EndpointCounter.builder().requestEndpoint("/api/another-endpoint").requestCount(10L).requestDate(LocalDate.of(2025, 1, 2)).build(),
                EndpointCounter.builder().requestEndpoint("/api/some-endpoint").requestCount(99L).requestDate(LocalDate.of(2025, 1, 4)).build()
        );
        endpointCounterRepository.saveAll(endpointCounterList);

        // when
        EndpointCounterAggregate endpointCounterAggregate = counterService.findSummary(LocalDate.of(2025, 1, 1));

        EndpointCounterAggregate expectedAggregate = EndpointCounterAggregate.builder()
                .period(LocalDate.of(2025, 1, 1))
                .usage(List.of(
                        EndpointCounterUsage.builder().endpoint("/api/some-endpoint").count(10L).build(),
                        EndpointCounterUsage.builder().endpoint("/api/another-endpoint").count(20L).build()
                ))
                .build();

        assertThat(endpointCounterAggregate).isEqualTo(expectedAggregate);
    }

    @Test
    void findSummaryWithinPeriodShouldBeOk() throws Exception {
        // save endpoint usages
        List<EndpointCounter> endpointCounterList = List.of(
            EndpointCounter.builder().requestEndpoint("/api/some-endpoint").requestCount(10L).requestDate(LocalDate.of(2025, 1, 1)).build(),
            EndpointCounter.builder().requestEndpoint("/api/some-endpoint").requestCount(7L).requestDate(LocalDate.of(2025, 1, 3)).build(),
            EndpointCounter.builder().requestEndpoint("/api/another-endpoint").requestCount(20L).requestDate(LocalDate.of(2025, 1, 1)).build(),
            EndpointCounter.builder().requestEndpoint("/api/another-endpoint").requestCount(10L).requestDate(LocalDate.of(2025, 1, 2)).build(),
            EndpointCounter.builder().requestEndpoint("/api/some-endpoint").requestCount(99L).requestDate(LocalDate.of(2025, 1, 4)).build()
        );
        endpointCounterRepository.saveAll(endpointCounterList);

        // when
        AuthTokenSet authTokenSet = this.authenticationService.authenticate(this.masterUsername, this.masterPassword);
        mockMvc.perform(
                    get("/api/endpoints/summary")
                            .queryParam("start", "2025-01-01")
                            .queryParam("end", "2025-01-03")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + authTokenSet.accessToken())
                            .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {"period": "2025-01-01", "usage": [{"endpoint": "/api/some-endpoint", "count": 10}, {"endpoint": "/api/another-endpoint", "count": 20}]},
                            {"period": "2025-01-02", "usage": [{"endpoint": "/api/another-endpoint", "count": 10}]},
                            {"period": "2025-01-03", "usage": [{"endpoint": "/api/some-endpoint", "count": 7}]}
                        ]
                        """));
    }
}
