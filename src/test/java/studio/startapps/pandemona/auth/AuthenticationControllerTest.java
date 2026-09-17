package studio.startapps.pandemona.auth;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import studio.startapps.pandemona.auth.internal.AuthTokenSet;
import studio.startapps.pandemona.auth.internal.TokenExpiredException;
import studio.startapps.pandemona.auth.request.CheckRefreshTokenRequest;
import studio.startapps.pandemona.core.AbstractControllerTest;
import studio.startapps.pandemona.core.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(AuthenticationController.class)
class AuthenticationControllerTest extends AbstractControllerTest {

    @Test
    void loginIsOk() throws Exception {
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
    void refreshTokenIsOk() throws Exception {
        String username = "user";
        String refreshToken = "def";

        given(authenticationService.renewAccessToken(any(), any())).willReturn(getDummyTokenSet());

        mockMvc.perform(post("/api/auth/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{\"username\": \"%s\", \"refreshToken\": \"%s\"}", username, refreshToken)))
                .andExpect(status().isOk());
    }

    @Test
    void refreshTokenExpiredShouldFail() throws Exception {
        String username = "user";
        String refreshToken = "def";

        given(authenticationService.renewAccessToken(any(), any())).willThrow(TokenExpiredException.class);

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"username\": \"%s\", \"refreshToken\": \"%s\"}", username, refreshToken)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void checkRefreshTokenShouldBeOk() throws Exception {
        CheckRefreshTokenRequest checkRefreshTokenRequest = new CheckRefreshTokenRequest("my-refresh-token");

        mockMvc.perform(post("/api/auth/check/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "refreshToken": "my-refresh-token" }
                        """))
                .andExpect(status().isOk());

        verify(authenticationService).checkRefreshTokenExpired(checkRefreshTokenRequest);
    }

    @Test
    void checkRefreshTokenShouldBeUnauthorized() throws Exception {
        CheckRefreshTokenRequest checkRefreshTokenRequest = new CheckRefreshTokenRequest("my-refresh-token");

        doThrow(TokenExpiredException.class).when(authenticationService).checkRefreshTokenExpired(checkRefreshTokenRequest);

        mockMvc.perform(post("/api/auth/check/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        { "refreshToken": "my-refresh-token" }
                        """))
                .andExpect(status().isUnauthorized());
    }

    private static AuthTokenSet getDummyTokenSet() {
        return new AuthTokenSet("abc", "def");
    }
}
