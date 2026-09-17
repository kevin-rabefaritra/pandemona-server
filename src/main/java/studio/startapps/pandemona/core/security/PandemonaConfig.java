package studio.startapps.pandemona.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;
import studio.startapps.pandemona.core.configuration.WebConfig;

@Configuration
@Import({SecurityConfig.class, WebConfig.class})
@EnableJpaAuditing
public class PandemonaConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
