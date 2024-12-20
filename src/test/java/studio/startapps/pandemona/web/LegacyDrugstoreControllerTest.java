package studio.startapps.pandemona.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.configuration.SecurityConfig;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.drugstore.DrugstoreService;
import studio.startapps.pandemona.drugstore.LegacyDrugstoreRestController;
import studio.startapps.pandemona.drugstore.OnDutyDrugstoresService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = LegacyDrugstoreRestController.class)
@Import(SecurityConfig.class)
@ActiveProfiles({"test"})
class LegacyDrugstoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DrugstoreService drugstoreService;

    @MockBean
    OnDutyDrugstoresService onDutyDrugstoresService;

    @MockBean
    AuthenticationService authenticationService;

    @Test
    void testEndpointIsOk() throws Exception {
        LocalDate today = LocalDate.now();
        String todayStr = today.format(DateTimeFormatter.ISO_DATE);

        mockMvc.perform(
            get("/api/v3/fetch/pharmada/{lastUpdate}", todayStr)
            .accept("application/json")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));
    }
}
