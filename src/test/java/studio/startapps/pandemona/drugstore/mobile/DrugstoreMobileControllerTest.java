package studio.startapps.pandemona.drugstore.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.configuration.PandemonaConfig;
import studio.startapps.pandemona.configuration.SecurityConfig;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = DrugstoreMobileController.class)
@Import(SecurityConfig.class)
public class DrugstoreMobileControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    DrugstoreMobileService drugstoreMobileService;

    @Autowired
    MockMvc mockMvc;

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
