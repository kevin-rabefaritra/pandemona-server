package studio.startapps.pandemona.auth.internal;

public record AuthTokenSet(
    String accessToken,
    String refreshToken
) {}
