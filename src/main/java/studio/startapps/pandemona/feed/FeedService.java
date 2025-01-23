package studio.startapps.pandemona.feed;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import studio.startapps.pandemona.feed.internal.FeedPage;
import studio.startapps.pandemona.feed.internal.PostPreview;
import studio.startapps.pandemona.util.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {

    private final FeedProperties feedProperties;
    private final RestTemplate restTemplate;

    FeedPage findAll(String language, Pageable pageable) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT_LANGUAGE, language);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        List<PostPreview> content = new ArrayList<>();
        boolean last = true;

        try {
            String url = UriComponentsBuilder.fromHttpUrl(this.feedProperties.getEndpoint())
                    .queryParam("channel", this.feedProperties.getChannel())
                    .queryParam("page", pageable.getPageNumber())
                    .encode()
                    .toUriString();

            ResponseEntity<JsonNode> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RestClientException("Failed with status code %s".formatted(response.getStatusCode()));
            }

            JsonNode responseContent = response.getBody();
            responseContent.get("content").elements().forEachRemaining((jsonNode -> {
                content.add(this.toPostPreview(jsonNode));
            }));
            last = responseContent.get("last").asBoolean();
        }
        catch (RestClientException e) {
            log.error("[FeedService.findAll] Failed to fetch feed. Error {}", e.getMessage());
        }

        return new FeedPage(content, last);
    }

    private PostPreview toPostPreview(JsonNode jsonNode) {
        JsonNode postNode = jsonNode.get("post");

        final List<String> postTags = new ArrayList<>();
        final List<String> mediaUris = new ArrayList<>();

        postNode.get("tags").forEach((tag) -> postTags.add(tag.asText()));
        postNode.get("mediaUris").forEach((tag) -> mediaUris.add(tag.asText()));

        return new PostPreview(
            postNode.get("reference").asText(),
            postNode.get("authorName").asText(),
            postNode.get("authorProfilePicture").asText(),
            DateUtils.parseDateTimeISO(postNode.get("publishedOn").asText()),
            postNode.get("summary").asText(),
            postTags,
            mediaUris
        );
    }
}
