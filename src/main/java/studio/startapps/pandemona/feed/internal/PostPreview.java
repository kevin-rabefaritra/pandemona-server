package studio.startapps.pandemona.feed.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import studio.startapps.pandemona.util.LangUtils;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Slf4j
public record PostPreview(
    Long id,
    String authorName,
    String authorPicture,
    String content,
    List<String> mediaList,
    LocalDateTime publishedOn
) {

    public static PostPreview build(Post post, String language) {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = LangUtils.getContentForLang(post.getContent(), language);

        // media list
        List<String> mediaList = List.of();
        try {
            TypeReference<List<String>> listTypeReference = new TypeReference<>() {};
            mediaList = objectMapper.readValue(post.getMediaList().toString(),listTypeReference);
        }
        catch (JsonProcessingException e) {
            log.error("[PostPreview] Unable to map {}", post.getMediaList().toString());
        }

        return PostPreview.builder()
                .id(post.getId())
                .authorName(post.getAuthorName())
                .authorPicture(post.getAuthorPicture())
                .content(content)
                .mediaList(mediaList)
                .publishedOn(post.getPublishedOn())
                .build();
    }
}
