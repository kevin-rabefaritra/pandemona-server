package studio.startapps.pandemona.city;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.configuration.SecurityConfig;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = CityController.class)
@Import(SecurityConfig.class)
public class CityControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    CityService cityService;

    @Autowired
    MockMvc mockMvc;

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
