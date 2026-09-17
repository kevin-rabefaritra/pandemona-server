package studio.startapps.pandemona.report.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(ClientReportMobileController.class)
class ClientReportMobileControllerTest extends AbstractControllerTest {

    @MockitoBean
    ClientReportMobileService reportService;

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
