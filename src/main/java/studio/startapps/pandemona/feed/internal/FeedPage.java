package studio.startapps.pandemona.feed.internal;

import lombok.Builder;

import java.util.List;

@Builder
public record FeedPage(
    List<PostPreview> data,
    boolean last
) {
    public FeedPage() {
        this(List.of(), true);
    }
}
