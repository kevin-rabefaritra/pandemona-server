package studio.startapps.pandemona.stats;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.stats.internal.EndpointCounter;
import studio.startapps.pandemona.stats.internal.EndpointCounterRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EndpointCounterIntegrationTest {

    @Autowired
    EndpointCounterRepository endpointCounterRepository;

    @Autowired
    EndpointCounterService counterService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

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

        EndpointCounter endpointCounter = endpointCounterList.get(0);
        assertThat(endpointCounter.getRequestEndpoint()).isEqualTo("api/mobile/v1/on-duty-drugstores");
        assertThat(endpointCounter.getRequestCount()).isOne();
    }
}
