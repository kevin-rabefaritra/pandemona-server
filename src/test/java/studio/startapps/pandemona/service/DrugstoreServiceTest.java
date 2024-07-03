package studio.startapps.pandemona.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.repository.entity.Drugstore;
import studio.startapps.pandemona.repository.entity.City;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
@ActiveProfiles({"test"})
class DrugstoreServiceTest {

    @Autowired
    private DrugstoreService drugstoreService;

    @Test
    void checkServiceInstanceIsNotNull() {
        assertNotNull(drugstoreService);
    }

    @Test
    void testFindAllDrugstoresIsNotEmpty() {
        List<Drugstore> drugstoreList = drugstoreService.findAll();

        assertThat(drugstoreList.size()).isPositive();
    }

    @Test
    void testSaveDrugstoreIsConsistent() {
        Drugstore drugstore = new Drugstore("DrugstoreA", "AddressA", List.of(), -1, -1, List.of(), City.ANTANANARIVO);
        Drugstore savedDrugstore = drugstoreService.save(drugstore);

        assertEquals(savedDrugstore.hashCode(), savedDrugstore.hashCode());
    }
}
