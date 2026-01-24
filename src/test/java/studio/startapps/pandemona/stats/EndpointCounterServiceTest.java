package studio.startapps.pandemona.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import studio.startapps.pandemona.core.IntegrationTest;
import studio.startapps.pandemona.stats.internal.EndpointCounter;
import studio.startapps.pandemona.stats.internal.EndpointCounterRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class EndpointCounterServiceTest {

    @Autowired
    EndpointCounterRepository endpointCounterRepository;

    @Autowired
    EndpointCounterService counterService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void destroy() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "endpoint_counter");
    }

    @Test
    void logRequestShouldSaveCounter() {
        long counterCount = endpointCounterRepository.count();

        assertThat(counterCount).isZero();

        LocalDate requestDate = LocalDate.of(2025, 3, 10);
        String endpoint = "/api/some-endpoint";
        counterService.logRequest(requestDate, endpoint);

        counterCount = endpointCounterRepository.count();
        assertThat(counterCount).isOne();

        List<EndpointCounter> endpointCounterList = endpointCounterRepository.findAll();
        assertThat(endpointCounterList).hasSize(1);

        EndpointCounter endpointCounter = endpointCounterList.getFirst();
        assertThat(endpointCounter.getRequestDate()).isEqualTo(requestDate);
        assertThat(endpointCounter.getRequestCount()).isOne();
    }

    @Test
    void logSameRequestShouldUpdateCounter() {
        long counterCount = endpointCounterRepository.count();

        assertThat(counterCount).isZero();

        LocalDate requestDate = LocalDate.of(2025, 3, 10);
        String endpoint = "/api/some-endpoint";

        // log 2 requests
        counterService.logRequest(requestDate, endpoint);
        counterService.logRequest(requestDate, endpoint);

        counterCount = endpointCounterRepository.count();
        assertThat(counterCount).isOne();

        List<EndpointCounter> endpointCounterList = endpointCounterRepository.findAll();
        assertThat(endpointCounterList).hasSize(1);

        EndpointCounter endpointCounter = endpointCounterList.getFirst();
        assertThat(endpointCounter.getRequestDate()).isEqualTo(requestDate);
        assertThat(endpointCounter.getRequestCount()).isEqualTo(2L);
    }

    @Test
    void logRequestsShouldUpdateCounter() {
        long counterCount = endpointCounterRepository.count();

        assertThat(counterCount).isZero();

        // log 2 requests
        counterService.logRequest(LocalDate.of(2025, 3, 10), "/api/endpoint-1");
        counterService.logRequest(LocalDate.of(2025, 3, 10), "/api/endpoint-1");
        counterService.logRequest(LocalDate.of(2025, 3, 10), "/api/endpoint-2");

        counterCount = endpointCounterRepository.count();
        assertThat(counterCount).isEqualTo(2L);

        List<EndpointCounter> endpointCounterList = endpointCounterRepository.findAll();
        assertThat(endpointCounterList).hasSize(2);
    }
}
