package studio.startapps.pandemona.feed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.feed.internal.Post;
import studio.startapps.pandemona.feed.internal.PostRepository;
import studio.startapps.pandemona.util.LangUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {

    private final PostRepository postRepository;

    private final FeedProperties feedProperties;

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public String getRequestedLanguage(String acceptLanguage) {
        return LangUtils.getFirstSupportedLangs(acceptLanguage, feedProperties.supportedLangsList());
    }
}