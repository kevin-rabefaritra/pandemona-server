package studio.startapps.pandemona.drugstore.mobile;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.drugstore.internal.DrugstoreRepository;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstores;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LegacyDrugstoreMobileServiceTest {

    @Autowired
    LegacyDrugstoreMobileService legacyDrugstoreMobileService;

    @Autowired
    DrugstoreRepository drugstoreRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @AfterEach
    void destroy() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "onduty_drugstores_drugstore", "drugstore", "onduty_drugstores");
    }

    @Sql("/data/multi-cities-drugstores-on-duty-drugstores.sql")
    @Test
    @Transactional // Added to fetch on-duty drugstores' drugstores
    void legacyClientFetchDrugstoresShouldOnlyIncludeMainCity() {
        CityEnum expectedCity = CityEnum.ANTANANARIVO;
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 31, 0, 0);
        Iterable<OnDutyDrugstores> onDutyDrugstores = legacyDrugstoreMobileService.findOnDutyDrugstoresByUpdatedAtGreaterThanEqual(localDateTime, expectedCity);

        onDutyDrugstores.forEach((onDutyDrugstoresItem) -> {
            assertFalse(onDutyDrugstoresItem.getDrugstores().isEmpty());
            onDutyDrugstoresItem.getDrugstores().forEach((drugstore) -> {
                assertEquals(expectedCity, drugstore.getCity(), "City should be the main city only");
            });
        });
    }

}
