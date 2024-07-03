package studio.startapps.pandemona.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.config.SecurityConfig;
import studio.startapps.pandemona.service.DrugstoreService;
import studio.startapps.pandemona.controller.web.OnDutyDrugstoreController;
import studio.startapps.pandemona.repository.entity.OnDutyDrugstores;
import studio.startapps.pandemona.service.OnDutyDrugstoresService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OnDutyDrugstoreController.class)
@Import(SecurityConfig.class)
@ActiveProfiles({"test"})
class OnDutyDrugstoresControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OnDutyDrugstoresService onDutyDrugstoresService;

    @MockBean
    DrugstoreService drugstoreService;

    @Test
    @WithUserDetails("user")
    void testOnDutyDrugstoresIndexForAdminIsOk() throws Exception {
        Pageable pageable = PageRequest.of(0, 1);
        Page<OnDutyDrugstores> emptyPage = Page.empty(pageable);

        given(onDutyDrugstoresService.findAll(any())).willReturn(emptyPage);

        mockMvc.perform(get("/onduty-drugstores"))
            .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user")
    void testCreateFormIsOk() throws Exception {
        LocalDate today = LocalDate.now();
        given(onDutyDrugstoresService.getNextStartDate()).willReturn(today);

        mockMvc.perform(get("/onduty-drugstores/create"))
                .andExpect(status().isOk());
    }
}
