package studio.startapps.pandemona.number.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.configuration.SecurityConfig;
import studio.startapps.pandemona.number.internal.EmergencyNumberType;
import studio.startapps.pandemona.number.internal.EmergencyNumberRequest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = NumberController.class)
@Import(SecurityConfig.class)
public class NumberControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    NumberService numberService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    void adminFetchNumbersShouldBeOk() throws Exception {
        mockMvc.perform(
            get("/api/numbers")
        ).andExpect(status().isOk());

        verify(numberService).findAll(any(Pageable.class));
    }

    @Test
    @WithMockUser
    void getHealthCenterShouldBeOk() throws Exception {
        EmergencyNumberDetails numberDetails = new EmergencyNumberDetails(
                "Emergency Police", null, EmergencyNumberType.POLICE, CityEnum.TOAMASINA, -1f, -1f, List.of("111")
        );
        given(numberService.findById(eq(99L))).willReturn(numberDetails);

        mockMvc.perform(
                get("/api/numbers/99")
        ).andExpect(status().isOk())
        .andExpect(content().json("{'name': 'Emergency Police', 'address': null, 'type': 'POLICE', 'city': 'TOAMASINA', 'latitude': -1, 'longitude': -1, 'contacts': ['111']}"));

        verify(numberService).findById(99L);
    }

    @Test
    @WithMockUser
    void adminCreateNewNumberShouldBeCreated() throws Exception {
        EmergencyNumberRequest request = new EmergencyNumberRequest(
            "Some Ambulance", "Nowhere, somewhere", List.of("112", "113"), CityEnum.ANTANANARIVO, -1.01f, 1.01f, EmergencyNumberType.AMBULANCE
        );

        mockMvc.perform(
            post("/api/numbers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {"name": "Some Ambulance", "address": "Nowhere, somewhere", "type": "AMBULANCE", "latitude": -1.01, "longitude": 1.01, "city": "ANTANANARIVO", "contacts": ["112", "113"]}""")
        ).andExpect(status().isCreated());

        verify(numberService).save(request);
    }

    @Test
    @WithMockUser
    void adminUpdateNumberShouldBeOk() throws Exception {
        EmergencyNumberRequest request = new EmergencyNumberRequest(
            "Another Police", "Nowhere, somewhere", List.of("112", "117"), CityEnum.NOSY_BE, -1.01f, 1.01f, EmergencyNumberType.POLICE
        );

        mockMvc.perform(
                put("/api/numbers/70")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"name": "Another Police", "address": "Nowhere, somewhere", "type": "POLICE", "latitude": -1.01, "longitude": 1.01, "city": "NOSY_BE", "contacts": ["112", "117"]}""")
        ).andExpect(status().isOk());

        verify(numberService).update(70, request);
    }

    @Test
    @WithMockUser
    void adminDeleteNumberShouldBeOk() throws Exception {
        mockMvc.perform(
            delete("/api/numbers/70")
        ).andExpect(status().isOk());

        verify(numberService).delete(70);
    }

    @Test
    @WithMockUser
    void adminFetchHealthCenterTypesShouldBeOk() throws Exception {
        given(numberService.getTypes()).willReturn(List.of("AMBULANCE", "POLICE", "FIREFIGHTERS"));

        mockMvc.perform(
            get("/api/numbers/types")
        ).andExpect(status().isOk())
        .andExpect(content().json("['AMBULANCE', 'POLICE', 'FIREFIGHTERS']"));

        verify(numberService).getTypes();
    }
}
