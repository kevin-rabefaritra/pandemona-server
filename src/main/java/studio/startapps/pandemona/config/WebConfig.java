package studio.startapps.pandemona.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String clientOrigin;

    public WebConfig(@Value("${pandemonium.cors.client-origin}") String clientOrigin) {
        this.clientOrigin = clientOrigin;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> protectedEndpoints = List.of("/api/auth/*", "/api/drugstores/**", "/api/onduty-drugstores/**", "/api/analytics/**");
        protectedEndpoints.forEach((endpoint) -> {
            registry.addMapping(endpoint)
                    .allowedOrigins(this.clientOrigin)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                    .allowCredentials(true);
        });

        // Mobile endpoint (GET only)
        registry.addMapping("/api/v3/**")
                .allowedOrigins("*")
                .allowedMethods("GET");
    }
}
