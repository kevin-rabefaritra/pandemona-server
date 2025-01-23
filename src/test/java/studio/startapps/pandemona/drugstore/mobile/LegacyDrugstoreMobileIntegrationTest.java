package studio.startapps.pandemona.drugstore.mobile;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import studio.startapps.pandemona.util.DateUtils;
import studio.startapps.pandemona.util.SystemUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class LegacyDrugstoreMobileIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void destroy() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "onduty_drugstores_drugstore", "drugstore", "onduty_drugstores");
    }

    @Sql("/data/drugstores-on-duty-drugstores.sql")
    @Test
    void legacyUserFetchDataShouldBeOk() throws Exception {
        SystemUtils.SYSTEM_DATE_TIME = LocalDateTime.of(2024, 1, 2, 9, 0);

        String response = restTemplate.getForObject("/api/v3/fetch/pharmada/{lastUpdate}", String.class, Map.of("lastUpdate", "2024-01-01"));

        String expectedResponse = """
            {"cd":"2024-01-02","cl_r":[],"en_r":[],"an_dr_r":[],"en":[],"cl":[],"an_dr":[{"ct":10,"s":"2024-07-15","e":"2024-07-29","id":1,"dr":[1,2,3]}],"version":0,"dr":[{"a":"67 Ha, en face église luthérienne.","ln":-1.0,"c":"020 22 253 61","ci":10,"lt":-1.0,"id":3,"n":"Pharmacie 67 ha","o":0}],"dr_r":[]}""";
        assertEquals(expectedResponse, response);
    }

    @Sql("/data/multi-cities-drugstores-on-duty-drugstores.sql")
    @Test
    void legacyUserShouldFetchOnlyMainCityData() throws Exception {
        SystemUtils.SYSTEM_DATE_TIME = LocalDateTime.of(2024, 1, 2, 9, 0);

        String response = restTemplate.getForObject("/api/v3/fetch/pharmada/{lastUpdate}", String.class, Map.of("lastUpdate", "2024-01-01"));

        String expectedResponse = """
            {"cd":"2024-01-02","cl_r":[],"en_r":[],"an_dr_r":[],"en":[],"cl":[],"an_dr":[{"ct":10,"s":"2024-07-15","e":"2024-07-29","id":1,"dr":[1,2,3]},{"ct":10,"s":"2025-01-10","e":"2025-01-15","id":3,"dr":[1]},{"ct":10,"s":"2025-01-15","e":"2025-01-25","id":4,"dr":[1]},{"ct":10,"s":"2025-01-25","e":"2025-02-07","id":5,"dr":[1,2,3]}],"version":0,"dr":[{"a":"67 Ha, en face église luthérienne.","ln":-1.0,"c":"020 22 253 61","ci":10,"lt":-1.0,"id":3,"n":"Pharmacie 67 ha","o":0}],"dr_r":[]}""";
        assertEquals(expectedResponse, response);
    }
}
