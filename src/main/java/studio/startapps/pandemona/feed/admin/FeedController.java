package studio.startapps.pandemona.feed.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.feed.internal.FeedService;
import studio.startapps.pandemona.feed.internal.Post;
import studio.startapps.pandemona.feed.internal.PostPreview;
import studio.startapps.pandemona.feed.internal.request.SavePostRequest;
import studio.startapps.pandemona.feed.internal.request.UpdatePostRequest;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    Page<PostPreview> findAll(Pageable pageable) {
        return feedService.findAll(pageable).map(PostPreview::build);
    }

    @GetMapping("/{id}")
    PostPreview findById(@PathVariable long id) {
        Post post = feedService.findById(id);
        return PostPreview.build(post);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody SavePostRequest request) {
        feedService.save(request);
    }

    @PutMapping("/{id}")
    void update(@PathVariable long id, @RequestBody UpdatePostRequest request) {
        feedService.update(id, request);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        feedService.delete(id);
    }
}
