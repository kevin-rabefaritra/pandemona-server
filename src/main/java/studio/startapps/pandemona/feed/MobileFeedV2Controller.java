package studio.startapps.pandemona.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.feed.internal.PostPreview;
import studio.startapps.pandemona.util.DataPage;

@RestController
@RequestMapping("/api/mobile/v2/feed")
@RequiredArgsConstructor
public class MobileFeedV2Controller {

    private final FeedService feedService;

    @GetMapping
    DataPage<PostPreview> findAll(@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE) String language, Pageable pageable) {
        // Todo: implement
        return null;
    }
}
