package studio.startapps.pandemona.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({SecurityConfig.class, WebConfig.class})
@EnableJpaAuditing
public class PandemonaConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
