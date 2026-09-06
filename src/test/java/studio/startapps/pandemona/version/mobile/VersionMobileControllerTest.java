package studio.startapps.pandemona.version.mobile;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;
import studio.startapps.pandemona.version.internal.AppVersion;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(VersionMobileControllerV1.class)
class VersionMobileControllerTest extends AbstractControllerTest {

    @MockitoBean
    VersionMobileService versionMobileService;

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
