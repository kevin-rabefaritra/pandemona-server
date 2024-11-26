package studio.startapps.pandemona.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@Import({SecurityConfig.class, WebConfig.class})
@EnableJpaAuditing
public class PandemonaConfig {

}
