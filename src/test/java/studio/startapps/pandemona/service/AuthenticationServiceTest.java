package studio.startapps.pandemona.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.auth.AuthenticationService;
import studio.startapps.pandemona.auth.internal.InvalidAuthCredentialsException;
import studio.startapps.pandemona.auth.internal.AuthTokenSet;
import studio.startapps.pandemona.util.RequestToken;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @Test
    void testJwt_isConsistent() throws InvalidAuthCredentialsException {
        // Write token
        String username = "user";
        String password = "123456";
        AuthTokenSet token = this.authenticationService.authenticate(username, password);

        // Read token
        RequestToken token1 = this.authenticationService.toAccessToken(token.accessToken());
        assertThat(token1.subject()).isEqualTo(username);
    }

    @Test
    void testJwt_hasNotExpired() throws InvalidAuthCredentialsException {
        String username = "user";
        String password = "123456";

        AuthTokenSet token = this.authenticationService.authenticate(username, password);

        RequestToken token1 = this.authenticationService.toAccessToken(token.accessToken());
        RequestToken token2 = this.authenticationService.toRefreshToken(token.refreshToken());

        assertThat(this.authenticationService.hasAccessTokenExpired(token1)).isFalse();
        assertThat(this.authenticationService.hasRefreshTokenExpired(token2)).isFalse();
    }
}
