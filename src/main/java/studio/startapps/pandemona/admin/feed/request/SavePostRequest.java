package studio.startapps.pandemona.admin.feed.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record SavePostRequest(
    String authorName,
    String authorPicture,
    Map<String, String> content,
    List<String> mediaList,
    LocalDateTime publishedOn
) {
}
