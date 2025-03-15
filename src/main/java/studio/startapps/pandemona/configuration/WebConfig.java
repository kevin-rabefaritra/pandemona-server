package studio.startapps.pandemona.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    private final String clientOrigin;

    public WebConfig(@Value("${pandemonium.cors.client-origin}") String clientOrigin) {
        this.clientOrigin = clientOrigin;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("[addCorsMappings] Adding client endpoint {}", this.clientOrigin);

        List<String> protectedEndpoints = List.of("/api/auth/*", "/api/drugstores/**", "/api/onduty-drugstores/**", "/api/cities", "/api/health-centers/**", "/api/numbers/**");
        protectedEndpoints.forEach((endpoint) -> {
            registry.addMapping(endpoint)
                    .allowedOrigins(this.clientOrigin)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                    .allowCredentials(true);
        });

        // Mobile endpoints
        // Report feature
        registry.addMapping("/api/mobile/*/report")
                .allowedOrigins("*")
                .allowedMethods("POST");

        // GET
        List<String> openEndpoints = List.of("/api/v3/**", "/api/mobile/**");
        openEndpoints.forEach((endpoint) -> {
            registry.addMapping(endpoint)
                    .allowedOrigins("*")
                    .allowedMethods("GET", "OPTIONS");
        });
    }
}
