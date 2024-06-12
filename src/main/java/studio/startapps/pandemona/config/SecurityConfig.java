package studio.startapps.pandemona.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(authorizeRequests -> {
                authorizeRequests.requestMatchers("/drugstores/**", "/home").authenticated();
                authorizeRequests.requestMatchers("/assets/**").permitAll(); // allow all assets
                authorizeRequests.requestMatchers("/login", "/logout").permitAll();
            })
            .formLogin((formLogin) -> {
                formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/home", true)
                    .permitAll();
            })
            .logout((logout) -> {
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll();
            });
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(
            PasswordEncoder passwordEncoder,
            @Value("${pandemonium.username}") String username,
            @Value("${pandemonium.password}") String password
    ) {
        UserDetails user1 = User
            .withUsername(username)
            .password(passwordEncoder.encode(password))
            .build();

        return new InMemoryUserDetailsManager(user1);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
