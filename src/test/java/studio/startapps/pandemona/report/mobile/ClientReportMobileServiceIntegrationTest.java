package studio.startapps.pandemona.report.mobile;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import studio.startapps.pandemona.report.internal.ClientReport;
import studio.startapps.pandemona.report.internal.ClientReportRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.within;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientReportMobileServiceIntegrationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ClientReportRepository clientReportRepository;

    @AfterEach
    void destroy() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "onduty_drugstores_drugstore", "drugstore", "onduty_drugstores", "report");
    }

    @Test
    @Sql("/data/drugstores-on-duty-drugstores.sql")
    void reportWithCorrectSignatureShouldBeCreated() {
        long totalCount = this.clientReportRepository.count();
        Assertions.assertEquals(0L, totalCount, "There should be no reports");

        // 1. Create report
        SaveReportRequest saveReportRequest = new SaveReportRequest(
                "Drugstore A",
                "Drugstore location incorrect",
                "914c1b472927f030d61edcb3bce499fa"
        );
        ResponseEntity<String> submitReportResponse = this.testRestTemplate.exchange(
            "/api/mobile/v1/report",
                HttpMethod.POST,
                new HttpEntity<>(saveReportRequest),
                String.class
        );

        Assertions.assertEquals(HttpStatus.CREATED, submitReportResponse.getStatusCode(), "Status should be created");

        // 2. Check count
        totalCount = this.clientReportRepository.count();
        Assertions.assertEquals(1L, totalCount, "There should be 1 report");

        ClientReport clientReport = this.clientReportRepository.findAll().get(0);
        Assertions.assertEquals("Drugstore A", clientReport.getTitle());
        Assertions.assertEquals("Drugstore location incorrect", clientReport.getComment());
        Assertions.assertNotNull(clientReport.getCreatedAt());
        org.assertj.core.api.Assertions.assertThat(clientReport.getCreatedAt()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));
    }

    @Test
    @Sql("/data/drugstores-on-duty-drugstores.sql")
    void reportWithIncorrectSignatureShouldBeNotCreated() {
        long totalCount = this.clientReportRepository.count();
        Assertions.assertEquals(0L, totalCount, "There should be no reports");

        // 1. Create report
        SaveReportRequest saveReportRequest = new SaveReportRequest(
                "Drugstore A",
                "Drugstore location incorrect",
                "someBadSignature1234"
        );
        ResponseEntity<String> submitReportResponse = this.testRestTemplate.exchange(
                "/api/mobile/v1/report",
                HttpMethod.POST,
                new HttpEntity<>(saveReportRequest),
                String.class
        );

        Assertions.assertEquals(HttpStatus.CREATED, submitReportResponse.getStatusCode(), "Status should be created (but actually isn't really)");

        // 2. Check count
        totalCount = this.clientReportRepository.count();
        Assertions.assertEquals(0L, totalCount, "There should still be 0 report");
    }
}
