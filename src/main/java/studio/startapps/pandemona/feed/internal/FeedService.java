package studio.startapps.pandemona.feed.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.feed.internal.request.SavePostRequest;
import studio.startapps.pandemona.feed.internal.request.UpdatePostRequest;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {

    private final PostRepository postRepository;

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public void save(SavePostRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        Post post = Post.builder()
                .authorName(request.authorName())
                .authorPicture(request.authorPicture())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .publishedOn(request.publishedOn())
                .content(objectMapper.convertValue(request.content(), JsonNode.class))
                .mediaList(objectMapper.convertValue(request.mediaList(), JsonNode.class))
                .build();

        postRepository.save(post);
    }

    public void update(long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        ObjectMapper objectMapper = new ObjectMapper();
        post.setAuthorName(request.authorName());
        post.setAuthorPicture(request.authorPicture());
        post.setUpdatedAt(LocalDateTime.now());
        post.setPublishedOn(request.publishedOn());
        post.setContent(objectMapper.convertValue(request.content(), JsonNode.class));
        post.setMediaList(objectMapper.convertValue(request.mediaList(), JsonNode.class));

        postRepository.save(post);
    }

    public void delete(long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return;
        }

        postRepository.delete(post);
    }
}