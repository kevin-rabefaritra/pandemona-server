package studio.startapps.pandemona.feed.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/mobile/v1/feed")
@RequiredArgsConstructor
@Deprecated
public class FeedMobileControllerV1 {

    @GetMapping
    @Deprecated(since = "v2.0")
    Map<String, Object> findAll() {
        return Map.of("data", List.of(), "last", true);
    }
}
