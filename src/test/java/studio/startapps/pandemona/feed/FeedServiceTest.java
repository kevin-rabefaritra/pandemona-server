package studio.startapps.pandemona.feed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import studio.startapps.pandemona.feed.internal.FeedPage;
import studio.startapps.pandemona.feed.internal.PostPreview;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
public class FeedServiceTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    FeedService feedService;

    @Value("${pandemonium.feed.endpoint}")
    String endpoint;

    MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void setup() {
        mockRestServiceServer = MockRestServiceServer.createServer(this.restTemplate);
    }

    @DisplayName("Fetch feed should return FeedPage")
    @Test
    void fetchFeedShouldReturnFeedPage() throws Exception {
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(String.format("%s?page=0", endpoint)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("""
                            {"content":[{"type":"POST","post":{"id":"67596e6280a26e5a79aff88c","reference":"3d3f5a2a28f45c1233c7ea9e1f6a2b2f","authorName":"2424.mg - L'actualité à Madagascar en temps réel","authorProfilePicture":"https://cdn.niuz.app/static/e19b4ed22ce9c3df5fd5bb6abf9f0a74","publishedOn":"2024-12-10T15:00:26","summary":"<SOME INTERESTING SUMMARY>","tags":["health","international","humanitarian","Madagascar","surgery","Africa Mercy","Mercy Ships","Toamasina","public health","training","education","NGO","maintenance","medical","surgical interventions","partnership","long-term development","news","updates"],"mediaUris":["https://cdn.niuz.app/2024/12/10/67b8044464682798bf4e75e9ed48c749"],"status":"APPROVED"},"event":null}],"pageable":{"pageNumber":0,"pageSize":10,"sort":{"empty":false,"sorted":true,"unsorted":false},"offset":0,"paged":true,"unpaged":false},"last":true,"totalPages":1,"totalElements":1,"size":10,"number":0,"sort":{"empty":false,"sorted":true,"unsorted":false},"first":true,"numberOfElements":1,"empty":false}
                            """)
            );

        FeedPage feedPage = feedService.findAll("en", Pageable.ofSize(10));
        assertEquals(1, feedPage.data().size());

        PostPreview expectedPostPreview = new PostPreview(
                "3d3f5a2a28f45c1233c7ea9e1f6a2b2f",
                "2424.mg - L'actualité à Madagascar en temps réel",
                "https://cdn.niuz.app/static/e19b4ed22ce9c3df5fd5bb6abf9f0a74",
                LocalDateTime.of(2024, 12, 10, 15, 0, 26),
                "<SOME INTERESTING SUMMARY>",
                List.of("health","international","humanitarian","Madagascar","surgery","Africa Mercy","Mercy Ships","Toamasina","public health","training","education","NGO","maintenance","medical","surgical interventions","partnership","long-term development","news","updates"),
                List.of("https://cdn.niuz.app/2024/12/10/67b8044464682798bf4e75e9ed48c749")
        );
        FeedPage expectedPage = new FeedPage(List.of(expectedPostPreview), true);
        assertEquals(expectedPage, feedPage);
    }
}
