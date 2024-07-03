package studio.startapps.pandemona.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.config.SecurityConfig;
import studio.startapps.pandemona.service.DrugstoreService;
import studio.startapps.pandemona.controller.api.DrugstoreAPIControllerV3;
import studio.startapps.pandemona.service.OnDutyDrugstoresService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = DrugstoreAPIControllerV3.class)
@Import(SecurityConfig.class)
@ActiveProfiles({"test"})
class APIControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DrugstoreService drugstoreService;

    @MockBean
    OnDutyDrugstoresService onDutyDrugstoresService;

    @Test
    void testHelloOk() throws Exception {
        mockMvc.perform(get("/api/v3/hello")).andExpect(status().isOk());
    }

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
