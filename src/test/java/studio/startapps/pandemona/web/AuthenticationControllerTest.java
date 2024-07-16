package studio.startapps.pandemona.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.startapps.pandemona.config.SecurityConfig;
import studio.startapps.pandemona.controller.web.AuthenticationController;
import studio.startapps.pandemona.exception.auth.TokenExpiredException;
import studio.startapps.pandemona.repository.dto.AuthTokenSet;
import studio.startapps.pandemona.repository.entity.User;
import studio.startapps.pandemona.service.AuthenticationService;
import studio.startapps.pandemona.utils.JsonUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = AuthenticationController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthenticationService authenticationService;

    @Test
    void testLoginIsOk() throws Exception {
        String username = "user";
        String password = "password";

        given(authenticationService.authenticate(username, password))
                .willReturn(getDummyTokenSet());

        mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password)))
                .andExpect(status().isOk());
    }

    @Test
    void testRefreshTokenIsOk() throws Exception {
        String username = "user";
        String refreshToken = "def";

        given(authenticationService.renewAccessToken(any(), any())).willReturn(getDummyTokenSet());

        mockMvc.perform(post("/api/auth/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"username\": \"%s\", \"refreshToken\": \"%s\"}", username, refreshToken)))
                .andExpect(status().isOk());
    }

    @Test
    void testRefreshTokenExpiredShouldFail() throws Exception {
        String username = "user";
        String refreshToken = "def";

        given(authenticationService.renewAccessToken(any(), any())).willThrow(TokenExpiredException.class);

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"username\": \"%s\", \"refreshToken\": \"%s\"}", username, refreshToken)))
                .andExpect(status().isUnauthorized());
    }

    private static AuthTokenSet getDummyTokenSet() {
        return new AuthTokenSet("abc", "def");
    }
}
