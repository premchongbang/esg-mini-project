package com.esg.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${esg.basic.auth.username}")
    private String username;

    @Value("${esg.basic.auth.password}")
    private String password;

    @Bean
    public InMemoryUserDetailsManager user() {
        return new InMemoryUserDetailsManager(User.withUsername(username).password("{noop}"+password).authorities("read").build());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http
                    .csrf(AbstractHttpConfigurer::disable)
                   .authorizeHttpRequests(auth -> auth
                           .requestMatchers("/h2-console/**").permitAll()
                           .anyRequest()
                           .authenticated())
                   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                   .httpBasic(Customizer.withDefaults())
                    .build();
    }
}

