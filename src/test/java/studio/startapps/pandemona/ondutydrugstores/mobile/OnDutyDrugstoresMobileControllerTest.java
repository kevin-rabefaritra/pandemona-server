package studio.startapps.pandemona.ondutydrugstores.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.configuration.SecurityConfig;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = OnDutyDrugstoresMobileController.class)
@Import(SecurityConfig.class)
public class OnDutyDrugstoresMobileControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    OnDutyDrugstoresMobileService onDutyDrugstoresMobileService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void fetchOnDutyDrugstoresShouldBeOk() throws Exception {
        given(onDutyDrugstoresMobileService.findAll(any(Pageable.class))).willReturn(List.of(
            new OnDutyDrugstoresItem("1", LocalDate.of(2024, 1, 1), LocalDate.of(2024,  6, 1), List.of("1", "2", "3", "4")),
            new OnDutyDrugstoresItem("2", LocalDate.of(2024, 2, 1), LocalDate.of(2024,  5, 1), List.of("7", "8"))
        ));

        mockMvc.perform(
            get("/api/mobile/v1/on-duty-drugstores")
        ).andExpect(status().isOk())
        .andExpect(content().json("""
                [{'id': '1', 'startDate': '2024-01-01', 'endDate': '2024-06-01', 'drugstoreIds': ['1', '2', '3', '4']},
                {'id': '2', 'startDate': '2024-02-01', 'endDate': '2024-05-01', 'drugstoreIds': ['7', '8']}
                ]"""));
    }
}
