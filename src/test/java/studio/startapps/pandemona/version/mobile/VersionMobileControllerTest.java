package studio.startapps.pandemona.version.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.configuration.SecurityConfig;
import studio.startapps.pandemona.version.internal.AppVersion;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = VersionMobileController.class)
@Import(SecurityConfig.class)
public class VersionMobileControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    VersionMobileService versionMobileService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void checkVersionShouldBeOk() throws Exception {
        given(versionMobileService.getVersion()).willReturn(new AppVersion(10, "10.0.1"));

        mockMvc.perform(
            get("/api/mobile/v1/version")
        ).andExpect(status().isOk())
        .andExpect(content().json("{'versionNumber': 10, 'versionCode': '10.0.1'}"));

        verify(versionMobileService).getVersion();
    }
}
