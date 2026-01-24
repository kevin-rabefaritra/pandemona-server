package studio.startapps.pandemona.drugstore.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(DrugstoreMobileController.class)
class DrugstoreMobileControllerTest extends AbstractControllerTest {

    @MockitoBean
    DrugstoreMobileService drugstoreMobileService;

    @Test
    void fetchDrugstoresShouldBeOk() throws Exception {
        given(drugstoreMobileService.findAll()).willReturn(List.of(
            new DrugstoreItem("123", "Drugstore Example", "Someplace, 188 Nowhere", "antananarivo", List.of("333333333", "9929929"), List.of(), 19.0002f, 1.00002f)
        ));

        mockMvc.perform(
            get("/api/mobile/v1/drugstores")
        ).andExpect(status().isOk())
        .andExpect(content().json("[{'id': '123', 'name': 'Drugstore Example', 'address': 'Someplace, 188 Nowhere', 'contacts': ['333333333', '9929929'], 'city': 'antananarivo', 'latitude': 19.0002, 'longitude': 1.00002, 'features': []}]"));
    }
}
