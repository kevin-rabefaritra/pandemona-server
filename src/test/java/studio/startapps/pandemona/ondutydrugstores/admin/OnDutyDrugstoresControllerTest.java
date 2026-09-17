package studio.startapps.pandemona.ondutydrugstores.admin;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;
import studio.startapps.pandemona.drugstore.admin.DrugstorePreview;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresDetails;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresPreview;
import studio.startapps.pandemona.util.DataPage;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(OnDutyDrugstoreController.class)
@ActiveProfiles({"test"})
class OnDutyDrugstoresControllerTest extends AbstractControllerTest {

    @MockitoBean
    OnDutyDrugstoresService onDutyDrugstoresService;

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

    @WithMockUser
    @Test
    void findByStartDateEndDateShouldReturnOnDutyDrugstores() throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("start", "2025-01-01");
        params.put("end", "2025-01-08");

        given(onDutyDrugstoresService.findAll(eq(params), any(Pageable.class))).willReturn(
            new DataPage<OnDutyDrugstoresPreview>(
                List.of(
                    new OnDutyDrugstoresPreview(1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 8), List.of("Drugstore A", "Drugstore B"))
                ),
                1, 0, true, true, 1, 1
            )
        );

        mockMvc.perform(
            get("/api/onduty-drugstores")
                    .param("start", "2025-01-01")
                    .param("end", "2025-01-08")
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(content().json("{'totalPages':1,'number':0,'first':true,'last':true,'totalElements':1,'numberOfElements':1,'content':[{'id':1,'startDate':'2025-01-01','endDate':'2025-01-08','drugstores':['Drugstore A','Drugstore B']}]}"));

        verify(onDutyDrugstoresService).findAll(eq(params), any());
    }
}
