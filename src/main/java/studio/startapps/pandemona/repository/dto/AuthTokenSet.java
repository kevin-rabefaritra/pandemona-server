package studio.startapps.pandemona.repository.dto;

public record AuthTokenSet(
    String accessToken,
    String refreshToken
) {}
