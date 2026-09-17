package studio.startapps.pandemona.city;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(CityController.class)
class CityControllerTest extends AbstractControllerTest {

    @MockitoBean
    CityService cityService;

    @Test
    @WithMockUser
    void getCitiesShouldBeOk() throws Exception {
        given(cityService.getCities()).willReturn(List.of("ANTANANARIVO", "ANTSIRABE", "NOSY_BE", "TOAMASINA"));

        mockMvc.perform(
            get("/api/cities")
        ).andExpect(status().isOk())
        .andExpect(content().json("['ANTANANARIVO', 'ANTSIRABE', 'NOSY_BE', 'TOAMASINA']"));

        verify(cityService).getCities();
    }
}
