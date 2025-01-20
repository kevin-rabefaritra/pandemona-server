package studio.startapps.pandemona.ondutydrugstores.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.configuration.SecurityConfig;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.drugstore.admin.DrugstorePreview;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstores;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresDetails;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresItemPreview;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresPreview;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = OnDutyDrugstoreController.class)
@Import(SecurityConfig.class)
@ActiveProfiles({"test"})
class OnDutyDrugstoresControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OnDutyDrugstoresService onDutyDrugstoresService;

    @MockBean
    AuthenticationService authenticationService;

    @WithMockUser
    @Test
    void getOnDutyDrugstoresIsOk() throws Exception {
        mockMvc.perform(get("/api/onduty-drugstores"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void getOnDutyDrugstoresByIdIsOk() throws Exception {
        OnDutyDrugstoresDetails onDutyDrugstoresDetails = new OnDutyDrugstoresDetails(
            10L,
            LocalDate.of(2022, 1, 1),
            LocalDate.of(2022, 1, 8),
            List.of(
                new DrugstorePreview(1, "Drugstore A", "some address", CityEnum.ANTANANARIVO),
                new DrugstorePreview(2, "Drugstore B", "some address", CityEnum.ANTANANARIVO)
            )
        );
        given(onDutyDrugstoresService.findById(10L)).willReturn(onDutyDrugstoresDetails);

        mockMvc.perform(
                get("/api/onduty-drugstores/10")
        ).andExpect(status().isOk())
        .andExpect(content().json("{'id': 10, 'startDate': '2022-01-01', 'endDate': '2022-01-08', 'drugstores': [{'id': 1, 'name': 'Drugstore A', 'address': 'some address', 'city': 'ANTANANARIVO'}, {'id': 2, 'name': 'Drugstore B', 'address': 'some address', 'city': 'ANTANANARIVO'}]}"));

        verify(onDutyDrugstoresService).findById(10L);
    }

    @WithMockUser
    @Test
    void saveOnDutyDrugstoresShouldBeCreated() throws Exception {
        OnDutyDrugstoresRequest request = new OnDutyDrugstoresRequest(
            LocalDate.of(2022, 1, 1),
            LocalDate.of(2022, 1, 8),
            List.of(1L, 2L, 3L, 4L, 5L)
        );

        mockMvc.perform(
            post("/api/onduty-drugstores")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {"startDate": "2022-01-01", "endDate": "2022-01-08", "drugstores": [1, 2, 3, 4, 5]}
                            """)
        ).andExpect(status().isCreated());

        verify(onDutyDrugstoresService).save(request);
    }
}
