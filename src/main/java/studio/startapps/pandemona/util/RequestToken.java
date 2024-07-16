package studio.startapps.pandemona.util;

import java.time.LocalDateTime;

public record RequestToken(
    String subject,
    LocalDateTime issuedOn
) {
}
