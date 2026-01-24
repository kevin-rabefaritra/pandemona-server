package studio.startapps.pandemona.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.feed.internal.FeedPage;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mobile/v1/feed")
@RequiredArgsConstructor
public class MobileFeedControllerV1 {

    @GetMapping(version = "1.0")
    @Deprecated(since = "v2.0")
    FeedPage findAll() {
        return FeedPage.builder()
                .data(List.of())
                .last(true)
                .build();
    }
}
