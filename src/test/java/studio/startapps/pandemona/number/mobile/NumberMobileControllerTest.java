package studio.startapps.pandemona.number.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.configuration.SecurityConfig;
import studio.startapps.pandemona.number.internal.EmergencyNumberType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = NumberMobileController.class)
@Import(SecurityConfig.class)
public class NumberMobileControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    NumberMobileService numberMobileService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void fetchNumbersShouldBeOk() throws Exception {
        given(numberMobileService.findAll()).willReturn(List.of(
            new EmergencyNumberItem(1L, "First Emergency Number", "Nowhere, Somewhere", CityEnum.ANTANANARIVO, List.of("112", "113"), -1.11f, 1.11f, EmergencyNumberType.AMBULANCE),
            new EmergencyNumberItem(2L, "SOS Firefighters", "Nosy Be, Somewhere", CityEnum.NOSY_BE, List.of("9919191", "11111"), -1.11f, 1.11f, EmergencyNumberType.FIREFIGHTERS)
        ));

        mockMvc.perform(
            get("/api/mobile/v1/numbers")
        ).andExpect(status().isOk())
        .andExpect(content().json("""
                [
                {'id': 1, 'name': 'First Emergency Number', 'address': 'Nowhere, Somewhere', 'city': 'ANTANANARIVO', 'contacts': ['112', '113'], 'latitude': -1.11, 'longitude': 1.11, 'type': 'AMBULANCE'},
                {'id': 2, 'name': 'SOS Firefighters', 'address': 'Nosy Be, Somewhere', 'city': 'NOSY_BE', 'contacts': ['9919191', '11111'], 'latitude': -1.11, 'longitude': 1.11, 'type': 'FIREFIGHTERS'}
                ]
                """));
    }
}
