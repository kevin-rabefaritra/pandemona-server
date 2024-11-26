package studio.startapps.pandemona.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.auth.internal.InvalidAuthCredentialsException;
import studio.startapps.pandemona.auth.internal.TokenExpiredException;
import studio.startapps.pandemona.auth.internal.TokenSubjectMismatchException;
import studio.startapps.pandemona.auth.internal.AuthTokenSet;
import studio.startapps.pandemona.user.User;
import studio.startapps.pandemona.util.JwtUtil;
import studio.startapps.pandemona.util.RequestToken;

import javax.crypto.SecretKey;

@Service
public class AuthenticationService implements UserDetailsService {

    private final String masterUsername;
    private final String masterPassword;
    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final long accessTokenTtl;
    private final long refreshTokenTtl;

    public AuthenticationService(
        @Value("${pandemonium.username}") String masterUsername,
        @Value("${pandemonium.password}") String masterPassword,
        @Value("${pandemonium.jwt.access.secretkey}") String accessSecretKey,
        @Value("${pandemonium.jwt.refresh.secretkey}") String refreshSecretKey,
        @Value("${pandemonium.jwt.access.ttl}") long accessTokenTtl,
        @Value("${pandemonium.jwt.refresh.ttl}") long refreshTokenTtl
    ) {
        this.masterUsername = masterUsername;
        this.masterPassword = masterPassword;
        this.accessSecretKey = JwtUtil.generateKey(accessSecretKey);
        this.refreshSecretKey = JwtUtil.generateKey(refreshSecretKey);
        this.accessTokenTtl = accessTokenTtl;
        this.refreshTokenTtl = refreshTokenTtl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(this.masterUsername)) {
            return new User(username);
        }
        throw new UsernameNotFoundException("User not found");
    }

    /**
     * Authenticates a user
     * @param username The user's username
     * @param password The user's password
     * @return A token set if the credentials are correct, otherwise throws InvalidAuthCredentialsException
     * @throws InvalidAuthCredentialsException if the credentials do not match with any existing user
     */
    public AuthTokenSet authenticate(String username, String password) throws InvalidAuthCredentialsException {
        if (username.equals(this.masterUsername) && password.equals(this.masterPassword)) {
            return JwtUtil.generateTokenSet(username, this.accessSecretKey, this.refreshSecretKey);
        }
        throw new InvalidAuthCredentialsException(username);
    }

    /**
     * Renews an access token given a refresh token
     * @param username token owner
     * @param refreshToken refresh token
     * @return AuthTokenSet with the access token and the SAME refresh token
     * @throws TokenSubjectMismatchException if the claimed username does not match with the refresh token subject
     * @throws TokenExpiredException if the refresh token has expired
     */
    public AuthTokenSet renewAccessToken(String username, String refreshToken) throws TokenSubjectMismatchException, TokenExpiredException {
        RequestToken requestToken = JwtUtil.toRequestToken(refreshToken, this.refreshSecretKey);
        if (!requestToken.subject().equals(username)) {
            throw new TokenSubjectMismatchException(requestToken.subject(), username);
        }
        if (this.hasRefreshTokenExpired(requestToken)) {
            throw new TokenExpiredException();
        }

        AuthTokenSet authTokenSet = JwtUtil.generateTokenSet(username, this.accessSecretKey, this.refreshSecretKey);
        return new AuthTokenSet(authTokenSet.accessToken(), refreshToken);
    }

    /**
     * Returns true if the provided access token has expired, else false
     * @param token the access token to process
     * @return true or false
     */
    public boolean hasAccessTokenExpired(RequestToken token) {
        return JwtUtil.hasTokenExpired(token, this.accessTokenTtl);
    }

    /**
     * Returns true if the provided refresh token has expired, else false
     * @param token the refresh token to process
     * @return true or false
     */
    public boolean hasRefreshTokenExpired(RequestToken token) {
        return JwtUtil.hasTokenExpired(token, this.refreshTokenTtl);
    }

    public RequestToken toAccessToken(String jwt) {
        return JwtUtil.toRequestToken(jwt, this.accessSecretKey);
    }

    public RequestToken toRefreshToken(String jwt) {
        return JwtUtil.toRequestToken(jwt, this.refreshSecretKey);
    }
}
