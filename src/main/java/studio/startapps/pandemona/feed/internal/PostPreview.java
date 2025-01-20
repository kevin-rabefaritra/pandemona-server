package studio.startapps.pandemona.feed.internal;

import java.time.LocalDateTime;
import java.util.List;

public record PostPreview(
    String reference,
    String authorName,
    String authorProfilePicture,
    LocalDateTime publishedOn,
    String summary,
    List<String> tags,
    List<String> mediaUris
) {
}
