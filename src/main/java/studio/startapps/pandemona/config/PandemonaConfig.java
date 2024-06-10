package studio.startapps.pandemona.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import studio.startapps.pandemona.repositories.DrugstoreRepository;
import studio.startapps.pandemona.services.DrugstoreService;
import studio.startapps.pandemona.services.internal.DrugstoreServiceImpl;

@Configuration
@Import({SecurityConfig.class})
public class PandemonaConfig {

    @Bean
    public DrugstoreService drugstoreService(DrugstoreRepository drugstoreRepository) {
        return new DrugstoreServiceImpl(drugstoreRepository);
    }
}
