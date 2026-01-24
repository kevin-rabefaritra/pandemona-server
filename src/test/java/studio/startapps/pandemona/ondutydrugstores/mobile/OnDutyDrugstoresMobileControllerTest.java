package studio.startapps.pandemona.ondutydrugstores.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Pageable;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(OnDutyDrugstoresMobileController.class)
class OnDutyDrugstoresMobileControllerTest extends AbstractControllerTest {

    @MockitoBean
    OnDutyDrugstoresMobileService onDutyDrugstoresMobileService;

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
