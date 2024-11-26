package studio.startapps.pandemona.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.configuration.SecurityConfig;
import studio.startapps.pandemona.drugstore.DrugstoreController;
import studio.startapps.pandemona.drugstore.internal.CityEnum;
import studio.startapps.pandemona.drugstore.Drugstore;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.drugstore.DrugstoreService;
import studio.startapps.pandemona.utils.JsonUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = DrugstoreController.class)
@Import(SecurityConfig.class)
@ActiveProfiles({"test"})
class DrugstoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DrugstoreService drugstoreService;

    @MockBean
    AuthenticationService authenticationService;

    @WithMockUser("user")
    @Test
    void testGetDrugstoresIsOk() throws Exception {
        mockMvc.perform(get("/api/drugstores")).andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    void testGetDrugstoresAnonymousIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/drugstores")).andExpect(status().isUnauthorized());
    }

    @WithMockUser("user")
    @Test
    void testCreateDrugstoreIsCreated() throws Exception {
        given(drugstoreService.save(any(Drugstore.class))).willReturn(new Drugstore());

        Drugstore drugstore = getDummyDrugstore();

        mockMvc.perform(post("/api/drugstores")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.asJsonString(drugstore)))
                .andExpect(status().isCreated());
        verify(drugstoreService).save(any(Drugstore.class));
    }

    @WithMockUser("user")
    @Test
    void testGetDrugstoreIsValid() throws Exception {
        Drugstore drugstore = getDummyDrugstore();
        drugstore.setId(10L);

        given(drugstoreService.findFirstById(10L)).willReturn(Optional.of(drugstore));
        mockMvc.perform(get("/api/drugstores/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(10L));
    }

    @WithMockUser("user")
    @Test
    void testUpdateDrugstoreIsValid() throws Exception {
        Drugstore drugstore = getDummyDrugstore();

        mockMvc.perform(put("/api/drugstores/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(drugstore)))
                .andExpect(status().isNoContent());

        drugstore.setId(10L);
        verify(drugstoreService).save(drugstore);
    }

    @WithMockUser("user")
    @Test
    void testDeleteDrugstoreIsValid() throws Exception {
        mockMvc.perform(delete("/api/drugstores/10"))
                .andExpect(status().isNoContent());
        verify(drugstoreService).deleteById(10L);
    }

    @WithMockUser("user")
    @Test
    void testGetCitiesIsConsistent() throws Exception {
        mockMvc.perform(get("/api/drugstores/cities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @WithMockUser("user")
    @Test
    void testGetAllDrugstoresDTO() throws Exception {
        mockMvc.perform(get("/api/drugstores/all"))
                .andExpect(status().isOk());
    }

    private static Drugstore getDummyDrugstore() {
        Drugstore result = new Drugstore();
        result.setName("Drugstore test");
        result.setAddress("Address");
        result.setContacts(List.of("444444444"));
        result.setLatitude(-1d);
        result.setLongitude(-1d);
        result.setCity(CityEnum.ANTANANARIVO);
        return result;
    }
}
