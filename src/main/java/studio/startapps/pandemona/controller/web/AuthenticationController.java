package studio.startapps.pandemona.controller.web;

import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.exception.auth.InvalidAuthCredentialsException;
import studio.startapps.pandemona.exception.auth.TokenExpiredException;
import studio.startapps.pandemona.exception.auth.TokenSubjectMismatchException;
import studio.startapps.pandemona.repository.dto.AuthTokenSet;
import studio.startapps.pandemona.service.AuthenticationService;

import java.util.Map;

@RestController
@CrossOrigin(originPatterns = "127.0.0.1:[*]")
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
