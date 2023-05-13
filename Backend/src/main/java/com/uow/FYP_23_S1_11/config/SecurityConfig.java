package com.uow.FYP_23_S1_11.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.uow.FYP_23_S1_11.filters.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Autowired
        private JwtAuthFilter jwtAuthFilter;
        @Autowired
        private AuthenticationEntryPoint authEntryPoint;
        @Autowired
        private AccessDeniedHandler accessDeniedHandler;

        @Value("CLIENT.URL")
        private String clientURL;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.cors()
                                .and()
                                .csrf()
                                .disable()
                                .exceptionHandling()
                                .authenticationEntryPoint(authEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                                .and()
                                .authorizeHttpRequests()
                                .requestMatchers("/api/auth/**", "/api/public/**", "/api/test/**",
                                                "/ws/**", "/api/queue/**",
                                                "/swagger-ui/**", "/v3/api-docs/**")
                                .permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsFilter corsFilter() {
                final CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(
                                Arrays.asList("http://localhost:5173", "http://127.0.0.1:5173",
                                                "https://fypdeploy-imtherealjh.vercel.app/",
                                                "https://fypdeploy.vercel.app/",
                                                "https://fypdeploy-git-auth-imtherealjh.vercel.app/"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT",
                                "DELETE", "OPTIONS"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Arrays.asList("*"));

                final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                return new CorsFilter(source);
        }
}