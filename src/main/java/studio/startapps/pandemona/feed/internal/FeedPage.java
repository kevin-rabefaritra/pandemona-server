package studio.startapps.pandemona.feed.internal;

import java.util.List;

public record FeedPage(
    List<PostPreview> data,
    boolean last
) {
    public FeedPage() {
        this(List.of(), true);
    }
}
