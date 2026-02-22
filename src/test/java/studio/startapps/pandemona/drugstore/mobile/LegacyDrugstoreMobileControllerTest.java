package studio.startapps.pandemona.drugstore.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;
import studio.startapps.pandemona.drugstore.admin.DrugstoreService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(LegacyDrugstoreMobileController.class)
class LegacyDrugstoreMobileControllerTest extends AbstractControllerTest {

    @MockitoBean
    DrugstoreService drugstoreService;

    @MockitoBean
    LegacyDrugstoreMobileService drugstoreMobileService;


    @Test
    void testEndpointIsOk() throws Exception {
        LocalDate today = LocalDate.now();
        String todayStr = today.format(DateTimeFormatter.ISO_DATE);

        mockMvc.perform(
            get("/api/v3/fetch/pharmada/{lastUpdate}", todayStr)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));
    }
}
