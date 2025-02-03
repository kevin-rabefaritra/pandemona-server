package studio.startapps.pandemona.feed;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pandemonium.feed")
@Data
public class FeedProperties {
    private String endpoint;
    private String channel;
    private String postDetailsUrl;
    private String supportedLangs;

    public String[] supportedLangsList() {
        return this.supportedLangs.split(",");
    }
}
