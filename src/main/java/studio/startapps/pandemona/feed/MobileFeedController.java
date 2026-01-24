package studio.startapps.pandemona.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.feed.internal.Post;
import studio.startapps.pandemona.feed.internal.PostPreview;
import studio.startapps.pandemona.util.MobileDataPage;

@RestController
@RequestMapping(value = "/api/mobile/feed")
@RequiredArgsConstructor
public class MobileFeedController {

    private final FeedService feedService;
    @GetMapping(version = "2.0")
    MobileDataPage<PostPreview> findAll(@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE) String acceptLanguage, Pageable pageable) {
        String language = feedService.getRequestedLanguage(acceptLanguage);
        Page<Post> posts = feedService.findAll(pageable);
        return new MobileDataPage<>(posts.map(post -> PostPreview.build(post, language)));
    }
}
