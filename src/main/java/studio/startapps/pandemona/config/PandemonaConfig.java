package studio.startapps.pandemona.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import studio.startapps.pandemona.repositories.DrugstoreRepository;
import studio.startapps.pandemona.services.DrugstoreService;
import studio.startapps.pandemona.services.internal.DrugstoreServiceImpl;

@Configuration
public class PandemonaConfig {

    @Bean
    public DrugstoreService drugstoreService(DrugstoreRepository drugstoreRepository) {
        return new DrugstoreServiceImpl(drugstoreRepository);
    }
}
