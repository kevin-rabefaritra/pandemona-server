package studio.startapps.pandemona.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.repository.entity.OnDutyDrugstores;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest()
@ActiveProfiles({"test"})
class OnDutyDrugstoresServiceTest {

    @Autowired
    private OnDutyDrugstoresService onDutyDrugstoresService;

    @Test
    void testSaveAndCheckByNextStartDateOnDutyDrugstoreIsCorrect() {
        LocalDate today = LocalDate.now();

        OnDutyDrugstores onDutyDrugstores = new OnDutyDrugstores();
        onDutyDrugstores.setStartDate(today);
        onDutyDrugstores.setEndDate(today.plusDays(7));

        onDutyDrugstoresService.save(onDutyDrugstores);

        LocalDate nextStartDate = onDutyDrugstoresService.getNextStartDate();
        assertThat(nextStartDate).isAfterOrEqualTo(today);
    }
}
