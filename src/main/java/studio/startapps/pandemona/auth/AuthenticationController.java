package studio.startapps.pandemona.auth;

import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.auth.internal.InvalidAuthCredentialsException;
import studio.startapps.pandemona.auth.internal.TokenExpiredException;
import studio.startapps.pandemona.auth.internal.TokenSubjectMismatchException;
import studio.startapps.pandemona.auth.internal.AuthTokenSet;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login")
    public AuthTokenSet login(@RequestBody Map<String, String> body) throws InvalidAuthCredentialsException {
        String username = body.getOrDefault("username", "");
        String password = body.getOrDefault("password", "");
        return this.authenticationService.authenticate(username, password);
    }

    @PostMapping(path = "/refresh")
    public AuthTokenSet refreshToken(@RequestBody Map<String, String> body) throws TokenSubjectMismatchException, TokenExpiredException {
        String username = body.getOrDefault("username", "");
        String refreshToken = body.getOrDefault("refreshToken", "");
        return this.authenticationService.renewAccessToken(username, refreshToken);
    }
}
