package studio.startapps.pandemona.number.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;
import studio.startapps.pandemona.number.internal.EmergencyNumberType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(NumberMobileController.class)
class NumberMobileControllerTest extends AbstractControllerTest {

    @MockitoBean
    NumberMobileService numberMobileService;

    @Test
    void fetchNumbersShouldBeOk() throws Exception {
        given(numberMobileService.findAll()).willReturn(List.of(
            new EmergencyNumberItem("1", "First Emergency Number", "Nowhere, Somewhere", CityEnum.ANTANANARIVO, List.of("112", "113"), -1.11f, 1.11f, EmergencyNumberType.AMBULANCE),
            new EmergencyNumberItem("2", "SOS Firefighters", "Nosy Be, Somewhere", CityEnum.NOSY_BE, List.of("9919191", "11111"), -1.11f, 1.11f, EmergencyNumberType.FIREFIGHTERS)
        ));

        mockMvc.perform(
            get("/api/mobile/v1/numbers")
        ).andExpect(status().isOk())
        .andExpect(content().json("""
                [
                {'id': '1', 'name': 'First Emergency Number', 'address': 'Nowhere, Somewhere', 'city': 'ANTANANARIVO', 'contacts': ['112', '113'], 'latitude': -1.11, 'longitude': 1.11, 'type': 'AMBULANCE'},
                {'id': '2', 'name': 'SOS Firefighters', 'address': 'Nosy Be, Somewhere', 'city': 'NOSY_BE', 'contacts': ['9919191', '11111'], 'latitude': -1.11, 'longitude': 1.11, 'type': 'FIREFIGHTERS'}
                ]
                """));
    }
}
