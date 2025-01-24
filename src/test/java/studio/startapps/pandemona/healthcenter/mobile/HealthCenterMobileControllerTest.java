package studio.startapps.pandemona.healthcenter.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.configuration.SecurityConfig;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = HealthCenterMobileController.class)
@Import(SecurityConfig.class)
public class HealthCenterMobileControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    HealthCenterMobileService healthCenterMobileService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void fetchHealthCentersShouldBeOk() throws Exception {
        given(healthCenterMobileService.findAll()).willReturn(List.of(
            new HealthCenterItem("1", "Clinic 101", "Nowhere, Somewhere", CityEnum.ANTSIRABE, List.of("03399399393", "11001010101"), 28.4f, 18.2f, HealthCenterType.CLINIC),
            new HealthCenterItem("2", "Toliara Hospital", "Nowhere, Somewhere", CityEnum.TOLIARA, List.of("111"), 28.4f, 8.2f, HealthCenterType.HOSPITAL)
        ));

        mockMvc.perform(
                get("/api/mobile/v1/health-centers")
        ).andExpect(status().isOk())
        .andExpect(content().json("""
                [
                {'id': '1', 'name': 'Clinic 101', 'address': 'Nowhere, Somewhere', 'city': 'ANTSIRABE', 'contacts': ['03399399393', '11001010101'], 'latitude': 28.4, 'longitude': 18.2, 'type': 'CLINIC'},
                {'id': '2', 'name': 'Toliara Hospital', 'address': 'Nowhere, Somewhere', 'city': 'TOLIARA', 'contacts': ['111'], 'latitude': 28.4, 'longitude': 8.2, 'type': 'HOSPITAL'}
                ]
                """));
    }
}
