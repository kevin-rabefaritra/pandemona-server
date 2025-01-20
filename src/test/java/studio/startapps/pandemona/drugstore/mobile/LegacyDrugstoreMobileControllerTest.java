package studio.startapps.pandemona.drugstore.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.configuration.SecurityConfig;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.drugstore.admin.DrugstoreService;
import studio.startapps.pandemona.ondutydrugstores.admin.OnDutyDrugstoresService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LegacyDrugstoreMobileController.class)
@Import(SecurityConfig.class)
@ActiveProfiles({"test"})
class LegacyDrugstoreMobileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DrugstoreService drugstoreService;

    @MockBean
    LegacyDrugstoreMobileService drugstoreMobileService;

    @MockBean
    AuthenticationService authenticationService;

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
