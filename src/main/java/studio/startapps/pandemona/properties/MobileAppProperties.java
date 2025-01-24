package studio.startapps.pandemona.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pandemonium.app")
@Data
public class MobileAppProperties {

    private int lastVersionNumber;
    private String lastVersionCode;
}
