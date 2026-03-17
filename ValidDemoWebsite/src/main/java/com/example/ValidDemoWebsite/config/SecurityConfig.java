package com.example.ValidDemoWebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Static assets
                        .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()

                        // Product management
                        .requestMatchers("/products/create", "/products/edit/**", "/products/delete/**").hasRole("ADMIN")
                        .requestMatchers("/products").hasAnyRole("USER", "ADMIN")

                        // Order (only USER)
                        .requestMatchers("/order").hasRole("USER")

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/home", true)
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
