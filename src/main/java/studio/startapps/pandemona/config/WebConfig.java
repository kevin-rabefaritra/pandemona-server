package studio.startapps.pandemona.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.stream.Stream;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String clientOrigin;

    public WebConfig(@Value("${pandemonium.cors.client-origin}") String clientOrigin) {
        this.clientOrigin = clientOrigin;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        Stream<String> protectedEndpoints = Stream.of("/api/auth/**", "/api/drugstores/**", "/api/onduty-drugstores");
        protectedEndpoints.forEach((endpoint) -> {
            registry.addMapping(endpoint)
                    .allowedOriginPatterns(this.clientOrigin)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
        });

        // Mobile endpoint (GET only)
        registry.addMapping("/api/auth/**")
                .allowedOrigins("*")
                .allowedMethods("GET");
    }
}
