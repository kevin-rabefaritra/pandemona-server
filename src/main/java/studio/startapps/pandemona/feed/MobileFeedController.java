package studio.startapps.pandemona.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.feed.internal.FeedPage;

@RestController
@RequestMapping("/api/mobile/v1/feed")
@RequiredArgsConstructor
public class MobileFeedController {

    private final FeedService feedService;

    @GetMapping
    FeedPage findAll(@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE) String language, Pageable pageable) {
        return this.feedService.findAll(language, pageable);
    }
}
