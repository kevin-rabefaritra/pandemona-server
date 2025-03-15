package studio.startapps.pandemona.report.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.configuration.SecurityConfig;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientReportMobileController.class)
@Import(SecurityConfig.class)
class ClientReportMobileControllerTest {

    @MockBean
    ClientReportMobileService reportService;

    @MockBean
    AuthenticationService authenticationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void reportContentShouldBeCreated() throws Exception {

        mockMvc.perform(
            post("/api/mobile/v1/report")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {"title": "Drugstore A - 143 Rd",
                            "comment": "Phone number is not valid anymore!",
                            "signature": "osidjosidjfosd484848"}
                            """)
        ).andExpect(status().isCreated());

        SaveReportRequest saveReportRequest = new SaveReportRequest("Drugstore A - 143 Rd", "Phone number is not valid anymore!", "osidjosidjfosd484848");
        verify(reportService).submit(saveReportRequest);
    }
}
