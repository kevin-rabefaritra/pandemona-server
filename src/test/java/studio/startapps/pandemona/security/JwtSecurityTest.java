package studio.startapps.pandemona.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import studio.startapps.pandemona.exception.auth.InvalidAuthCredentialsException;
import studio.startapps.pandemona.repository.dto.AuthTokenSet;
import studio.startapps.pandemona.service.AuthenticationService;
import studio.startapps.pandemona.util.RequestToken;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
public class JwtSecurityTest {

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
        RequestToken token2 = this.authenticationService.toRefreshToken(token.accessToken());

        assertThat(this.authenticationService.hasAccessTokenExpired(token1)).isFalse();
        assertThat(this.authenticationService.hasRefreshTokenExpired(token2)).isFalse();
    }
}
