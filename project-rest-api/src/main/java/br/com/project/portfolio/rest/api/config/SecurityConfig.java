// br/com/project/portfolio/rest/api/config/SecurityConfig.java
package br.com.project.portfolio.rest.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static br.com.project.portfolio.rest.api.shared.util.Constants.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${api.auth.username}")
    private String username;
    @Value("${api.auth.password}")
    private String password;
    @Value("${api.auth.role}")
    private String role;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(ACTUATOR_WHITELIST).permitAll()
                        .requestMatchers(PUBLIC_MISC).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // CORS preflight
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Basic Auth

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername(username).password(encoder.encode(password)).roles(role).build()
        );
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
