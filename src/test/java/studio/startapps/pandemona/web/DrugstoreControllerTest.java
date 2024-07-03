package studio.startapps.pandemona.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.config.SecurityConfig;
import studio.startapps.pandemona.repository.entity.Drugstore;
import studio.startapps.pandemona.controller.web.DrugstoreController;
import studio.startapps.pandemona.service.DrugstoreService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DrugstoreController.class)
@Import(SecurityConfig.class)
@ActiveProfiles({"test"})
class DrugstoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DrugstoreService drugstoreService;

    @Test
    @WithUserDetails("user")
    void testDrugstoreIndexForAdminIsOk() throws Exception {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Drugstore> emptyPage = Page.empty(pageable);

        given(drugstoreService.findAll(any())).willReturn(emptyPage);

        mockMvc.perform(get("/drugstores"))
            .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testDrugstoreIndexForGuestIsRedirected() throws Exception {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Drugstore> emptyPage = Page.empty(pageable);

        given(drugstoreService.findAll(any())).willReturn(emptyPage);

        mockMvc.perform(get("/drugstores"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails("user")
    void testCreateFormIsOk() throws Exception {
        mockMvc.perform(get("/drugstores/create"))
                .andExpect(status().isOk());
    }
}
