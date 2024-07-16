package studio.startapps.pandemona.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.config.SecurityConfig;
import studio.startapps.pandemona.controller.web.OnDutyDrugstoreController;
import studio.startapps.pandemona.repository.entity.CityEnum;
import studio.startapps.pandemona.repository.entity.Drugstore;
import studio.startapps.pandemona.repository.entity.OnDutyDrugstores;
import studio.startapps.pandemona.service.AuthenticationService;
import studio.startapps.pandemona.service.OnDutyDrugstoresService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

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

    @WithMockUser("user")
    @Test
    void testGetOnDutyDrugstoresIsOk() throws Exception {
        mockMvc.perform(get("/api/onduty-drugstores"))
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void testGetOnDutyDrugstoresByIdIsValid() throws Exception {
        given(onDutyDrugstoresService.findById(10L)).willReturn(Optional.of(getDummyOnDutyDrugstores()));

        mockMvc.perform(get("/api/onduty-drugstores/10"))
                .andExpect(status().isOk());

        verify(onDutyDrugstoresService).findById(10L);
    }

    private static OnDutyDrugstores getDummyOnDutyDrugstores() {
        OnDutyDrugstores result = new OnDutyDrugstores();
        result.setStartDate(LocalDate.now());
        result.setEndDate(LocalDate.now().plusDays(7));
        result.setDrugstores(Set.of(
            new Drugstore(1, "name", "address", List.of("111111"), -1, -1, List.of(), CityEnum.ANTANANARIVO),
            new Drugstore(2, "name2", "address", List.of("111111"), -1, -1, List.of(), CityEnum.ANTANANARIVO),
            new Drugstore(3, "name3", "address", List.of("111111"), -1, -1, List.of(), CityEnum.ANTANANARIVO)
        ));
        return result;
    }
}
