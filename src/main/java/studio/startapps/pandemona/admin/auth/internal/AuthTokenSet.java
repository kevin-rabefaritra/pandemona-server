package studio.startapps.pandemona.admin.auth.internal;

public record AuthTokenSet(
    String accessToken,
    String refreshToken
) {}
