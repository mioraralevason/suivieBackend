package com.suivilbcft.suivibackend.config;

import java.util.Arrays;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;  

   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/countries/**").permitAll() 
                .requestMatchers("/api/seuils-risque/**").permitAll() 
                .requestMatchers("/api/questions/**").permitAll() 
                .requestMatchers("/api/sous-section/**").permitAll()
                .requestMatchers("/api/sections/**").permitAll()
                .requestMatchers("/api/section/**").permitAll()

                // SÉCURISÉ : SEULS admin ET superviseur
                .requestMatchers("/api/scoring/**")
                .hasAnyAuthority("admin", "superviseur")

                // Allow institutional workflow endpoints for authenticated users
                .requestMatchers("/api/institution/workflow/status").authenticated()
                .requestMatchers("/api/institution/update-info").authenticated()
                .requestMatchers("/api/institution/completion-status").authenticated()
                .requestMatchers("/api/institution/activity/details/**").authenticated()
                .requestMatchers("/api/institution/activity/save-responses/**").authenticated()

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  
            .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}