package studio.startapps.pandemona.feed.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.feed.internal.FeedService;
import studio.startapps.pandemona.feed.internal.Post;
import studio.startapps.pandemona.feed.internal.PostPreview;
import studio.startapps.pandemona.util.MobileDataPage;

@RestController
@RequestMapping(value = "/api/mobile/feed", version = "2.0")
@RequiredArgsConstructor
public class FeedMobileController {

    private final FeedService feedService;

    @GetMapping
    MobileDataPage<PostPreview> findAll(@PageableDefault(size = 20, sort = "publishedOn", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> posts = feedService.findAll(pageable);
        return new MobileDataPage<>(posts.map(PostPreview::build));
    }
}
