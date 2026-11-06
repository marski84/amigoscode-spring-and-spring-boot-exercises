package com.amigoscode.security;

// TODO: 19 - Create a SecurityFilterChain bean:
//  - Annotate this class with @Configuration and @EnableWebSecurity
//  - Create a @Bean method that returns SecurityFilterChain
//  - Configure HTTP Basic authentication
//  - Permit access to /h2-console/** and /actuator/**
//  - Require authentication for all other endpoints
//  Hint:
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//            .csrf(csrf -> csrf.disable())
//            .headers(headers -> headers.frameOptions(f -> f.disable()))
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/h2-console/**", "/actuator/**").permitAll()
//                .anyRequest().authenticated()
//            )
//            .httpBasic(Customizer.withDefaults())
//            .build();
//    }

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// TODO: 20 - Create an in-memory user:
//  - Create a @Bean method that returns UserDetailsService
//  - Use User.builder() to create a user with username "admin", password "password", role "ADMIN"
//  - Use BCryptPasswordEncoder to encode the password
//  - Return new InMemoryUserDetailsManager(user)
//  - Also create a @Bean for PasswordEncoder returning new BCryptPasswordEncoder()
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    //        return http
//            .csrf(csrf -> csrf.disable())
//            .headers(headers -> headers.frameOptions(f -> f.disable()))
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/h2-console/**", "/actuator/**").permitAll()
//                .anyRequest().authenticated()
//            )
//            .httpBasic(Customizer.withDefaults())
//            .build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(
                        headers -> headers.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/h2-console/**", "/actuator/**")
                                .permitAll().anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("testuser")
                .password(encoder().encode("test"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
