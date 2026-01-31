package studio.startapps.pandemona.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Value("${pandemonium.cors.client-origin}")
    private String clientOrigin;

    private final AuthenticationFilter authenticationFilter;
    private final UnauthorizedUserHandler unauthorizedUserHandler = new UnauthorizedUserHandler();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        httpSecurity
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests -> {
                authorizeRequests.requestMatchers("/api/v3/**", "/api/mobile/**").permitAll();
                authorizeRequests.requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/refresh", "/api/auth/check/refresh").permitAll();
                authorizeRequests.anyRequest().authenticated();
            })
            .exceptionHandling(e -> e.accessDeniedHandler(this.unauthorizedUserHandler).authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        log.info("[SecurityConfig] Registering CORS for {}", clientOrigin);

        // 1. Protected endpoints (admin)
        List<String> protectedEndpoints = List.of("/api/auth/**", "/api/drugstores/**", "/api/onduty-drugstores/**", "/api/cities", "/api/health-centers/**", "/api/numbers/**", "/api/endpoints/*", "/api/feed/**");
        protectedEndpoints.forEach(endpoint -> {
            CorsConfiguration configuration = buildCorsConfiguration(clientOrigin, List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
            source.registerCorsConfiguration(endpoint, configuration);
        });

        // 2. Mobile endpoints
        CorsConfiguration mobileConfiguration = buildCorsConfiguration("*", List.of("POST"));
        source.registerCorsConfiguration("/api/mobile/*/report", mobileConfiguration);

        List<String> openEndpoints = List.of("/api/v3/**", "/api/mobile/**");
        openEndpoints.forEach(endpoint -> {
            CorsConfiguration configuration = buildCorsConfiguration("*", List.of("GET", "OPTIONS"));
            source.registerCorsConfiguration(endpoint, configuration);
        });

        return source;
    }

    private CorsConfiguration buildCorsConfiguration(String allowedOrigin, List<String> allowedMethods) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigin));
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        return configuration;
    }

    private static class UnauthorizedUserHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
            else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}
