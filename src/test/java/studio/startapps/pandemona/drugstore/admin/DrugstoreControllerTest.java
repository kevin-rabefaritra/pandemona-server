package studio.startapps.pandemona.drugstore.admin;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;

import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(DrugstoreController.class)
class DrugstoreControllerTest extends AbstractControllerTest {

    @MockitoBean
    DrugstoreService drugstoreService;

    @WithMockUser
    @Test
    void getDrugstoresIsOk() throws Exception {
        mockMvc.perform(get("/api/drugstores")).andExpect(status().isOk());

        verify(drugstoreService).findAll(any(Pageable.class), eq(Map.of()));
    }

    @WithMockUser
    @Test
    void listDrugstoresWithFilterIsOk() throws Exception {
        Map<String, String> expectedParams = Map.of("city", "antananarivo", "keyword", "somekeyword");

        mockMvc.perform(
            get("/api/drugstores")
                .queryParam("city", "antananarivo")
                .queryParam("keyword", "somekeyword")
        ).andExpect(status().isOk());

        verify(drugstoreService).findAll(any(Pageable.class), eq(expectedParams));
    }

    @WithAnonymousUser
    @Test
    void getDrugstoresAnonymousIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/drugstores")).andExpect(status().isUnauthorized());
    }

    @WithMockUser()
    @Test
    void saveDrugstoreIsCreated() throws Exception {
        DrugstoreRequest drugstoreRequest = new DrugstoreRequest("Some drugstore", "Nowhere, somewhere", CityEnum.ANTANANARIVO, -1f, -1f, List.of("0339939393", "191919191"), List.of());

        mockMvc.perform(
            post("/api/drugstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "Some drugstore", "address": "Nowhere, somewhere", "city": "ANTANANARIVO", "latitude": -1, "longitude": -1, "contacts": ["0339939393", "191919191"], "features": []}
                        """)
        ).andExpect(status().isCreated());

        verify(drugstoreService).save(drugstoreRequest);
    }

    @WithMockUser("user")
    @Test
    void getDrugstoreIsValid() throws Exception {
        DrugstoreDetails drugstoreDetails = new DrugstoreDetails("Some drugstore", "Some address", CityEnum.NOSY_BE, 27.3f, 89.1f, List.of("123", "456"), List.of());

        given(drugstoreService.findById(10L)).willReturn(drugstoreDetails);
        mockMvc.perform(
                get("/api/drugstores/10")
        ).andExpect(status().isOk())
        .andExpect(content().json("{'name': 'Some drugstore', 'address': 'Some address', 'city': 'NOSY_BE', 'latitude': 27.3, 'longitude': 89.1, 'contacts': ['123', '456'], 'features': []}"));
    }

    @WithMockUser("user")
    @Test
    void getDrugstoreWithoutContactsIsValid() throws Exception {
        DrugstoreDetails drugstoreDetails = new DrugstoreDetails("Some drugstore", "Some address", CityEnum.NOSY_BE, 27.3f, 89.1f, null, null);

        given(drugstoreService.findById(10L)).willReturn(drugstoreDetails);
        mockMvc.perform(
                        get("/api/drugstores/10")
                ).andExpect(status().isOk())
                .andExpect(content().json("{'name': 'Some drugstore', 'address': 'Some address', 'city': 'NOSY_BE', 'latitude': 27.3, 'longitude': 89.1, 'contacts': [], 'features': []}"));
    }

    @WithMockUser("user")
    @Test
    void updateDrugstoreIsValid() throws Exception {
        DrugstoreRequest drugstoreRequest = new DrugstoreRequest("New drugstore", "Nowhere, somewhere", CityEnum.ANTSIRABE, -1f, -1f, List.of("0339939393", "191919191"), List.of());

        mockMvc.perform(
                put("/api/drugstores/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "New drugstore", "address": "Nowhere, somewhere", "city": "ANTSIRABE", "latitude": -1, "longitude": -1, "contacts": ["0339939393", "191919191"], "features": []}
                                """)
        ).andExpect(status().isNoContent());

        verify(drugstoreService).update(10L, drugstoreRequest);
    }

    @WithMockUser()
    @Test
    void deleteDrugstoreIsValid() throws Exception {
        mockMvc.perform(delete("/api/drugstores/10"))
                .andExpect(status().isNoContent());
        verify(drugstoreService).deleteById(10L);
    }

    @WithMockUser
    @Test
    void getDrugstoreFeaturesIsOk() throws Exception {
        given(drugstoreService.getFeatures()).willReturn(List.of("H24", "D7", "OUTSKIRTS"));

        mockMvc.perform(
            get("/api/drugstores/features")
        ).andExpect(status().isOk())
        .andExpect(content().json("['H24', 'D7', 'OUTSKIRTS']"));
    }

    @WithMockUser
    @Test
    void searchByKeywordIsOk() throws Exception {
        given(drugstoreService.findByKeyword(eq("somedrugstore"))).willReturn(List.of(
            new DrugstorePreview(1L, "Some drugstore", "Some address", CityEnum.ANTANANARIVO),
            new DrugstorePreview(2L, "Another drugstore", "Another address", CityEnum.ANTSIRABE)
        ));

        mockMvc.perform(
            get("/api/drugstores/search")
                .queryParam("q", "somedrugstore")
        ).andExpect(status().isOk())
        .andExpect(content().json("[{'id': 1, 'name': 'Some drugstore', 'address': 'Some address', 'city': 'ANTANANARIVO'}, {'id': 2, 'name': 'Another drugstore', 'address': 'Another address', 'city': 'ANTSIRABE'}]"));

        verify(drugstoreService).findByKeyword("somedrugstore");
    }
}
