package studio.startapps.pandemona.healthcenter.admin;

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
import studio.startapps.pandemona.healthcenter.internal.HealthCenterRequest;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = HealthCenterController.class)
@Import(SecurityConfig.class)
public class HealthCenterControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    HealthCenterService healthCenterService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    void adminFetchHealthCentersShouldBeOk() throws Exception {
        mockMvc.perform(
                get("/api/health-centers")
        ).andExpect(status().isOk());

        verify(healthCenterService).findAll(any(Pageable.class));
    }

    @Test
    @WithMockUser
    void getHealthCenterShouldBeOk() throws Exception {
        HealthCenterDetails healthCenterDetails = new HealthCenterDetails(
            "Some health center", "Somewhere, nowhere", HealthCenterType.CLINIC, CityEnum.ANTANANARIVO, -1f, -1f, List.of("111", "222")
        );
        given(healthCenterService.findById(eq(10L))).willReturn(healthCenterDetails);

        mockMvc.perform(
            get("/api/health-centers/10")
        ).andExpect(status().isOk())
        .andExpect(content().json("{'name': 'Some health center', 'address': 'Somewhere, nowhere', 'type': 'CLINIC', 'city': 'ANTANANARIVO', 'latitude': -1, 'longitude': -1, 'contacts': ['111', '222']}"));

        verify(healthCenterService).findById(10L);
    }

    @Test
    @WithMockUser
    void adminCreateNewHealthCenterShouldBeCreated() throws Exception {
        HealthCenterRequest request = new HealthCenterRequest(
            "Some Clinic", "Nowhere, somewhere", List.of("112", "113"), CityEnum.ANTANANARIVO, -1.01f, 1.01f, HealthCenterType.CLINIC
        );

        mockMvc.perform(
                post("/api/health-centers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"name": "Some Clinic", "address": "Nowhere, somewhere", "type": "CLINIC", "latitude": -1.01, "longitude": 1.01, "city": "ANTANANARIVO", "contacts": ["112", "113"]}""")
        ).andExpect(status().isCreated());

        verify(healthCenterService).save(request);
    }

    @Test
    @WithMockUser
    void adminUpdateHealthCenterShouldBeOk() throws Exception {
        HealthCenterRequest request = new HealthCenterRequest(
            "Hospital B", "Nowhere, somewhere", List.of("101"), CityEnum.ANTSIRABE, -1.01f, 1.01f, HealthCenterType.HOSPITAL
        );

        mockMvc.perform(
                put("/api/health-centers/70")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"name": "Hospital B", "address": "Nowhere, somewhere", "type": "HOSPITAL", "latitude": -1.01, "longitude": 1.01, "city": "ANTSIRABE", "contacts": ["101"]}""")
        ).andExpect(status().isOk());

        verify(healthCenterService).update(70, request);
    }

    @Test
    @WithMockUser
    void adminDeleteHealthCenterShouldBeOk() throws Exception {
        mockMvc.perform(
                delete("/api/health-centers/70")
        ).andExpect(status().isOk());

        verify(healthCenterService).delete(70);
    }

    @Test
    @WithMockUser
    void adminFetchHealthCenterTypesShouldBeOk() throws Exception {
        given(healthCenterService.getTypes()).willReturn(List.of("CLINIC", "HOSPITAL"));

        mockMvc.perform(
            get("/api/health-centers/types")
        ).andExpect(status().isOk())
        .andExpect(content().json("['CLINIC', 'HOSPITAL']"));

        verify(healthCenterService).getTypes();
    }
}
