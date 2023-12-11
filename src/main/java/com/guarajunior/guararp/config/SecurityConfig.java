package com.guarajunior.guararp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guarajunior.guararp.infra.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class SecurityConfig {
    final SecurityFilter securityFilter;
    final ObjectMapper objectMapper;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(customizeCors())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // RH
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/cursos", "/departamentos", "/cargos", "/autoridades").hasAnyAuthority(Permission.CAN_VIEW.name() + "_ON_RH", Permission.CAN_VIEW.name() + "_ON_ADMINISTRATIVO")
                        .requestMatchers("/usuarios", "/cursos", "/departamentos", "/cargos", "/autoridades").hasAnyAuthority(Permission.CAN_WRITE.name() + "_ON_RH", Permission.CAN_WRITE.name() + "_ON_ADMINISTRATIVO")

                        // Warehouse
                        .requestMatchers(HttpMethod.GET, "/produtos").hasAnyAuthority(Permission.CAN_VIEW.name() + "_ON_ALMOXARIFADO", Permission.CAN_VIEW.name() + "_ON_ADMINISTRATIVO")
                        .requestMatchers("/produtos").hasAnyAuthority(Permission.CAN_WRITE.name() + "_ON_ALMOXARIFADO", Permission.CAN_WRITE.name() + "_ON_ADMINISTRATIVO")

                        // Financial
                        .requestMatchers(HttpMethod.GET, "/transacoes", "/bancos").hasAnyAuthority(Permission.CAN_VIEW.name() + "_ON_FINANCEIRO", Permission.CAN_VIEW.name() + "_ON_ADMINISTRATIVO")
                        .requestMatchers("/transacoes", "/bancos").hasAnyAuthority(Permission.CAN_WRITE.name() + "_ON_FINANCEIRO", Permission.CAN_WRITE.name() + "_ON_ADMINISTRATIVO")

                        // Projects
                        .requestMatchers(HttpMethod.GET, "/empresas").hasAnyAuthority(Permission.CAN_VIEW.name() + "_ON_PROJETOS", Permission.CAN_VIEW.name() + "_ON_ADMINISTRATIVO")
                        .requestMatchers("/empresas").hasAnyAuthority(Permission.CAN_WRITE.name() + "_ON_PROJETOS", Permission.CAN_WRITE.name() + "_ON_ADMINISTRATIVO")

                        .requestMatchers(
                                "/auth/**",
                                "/swagger/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/v3/api-docs/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Customizer<CorsConfigurer<HttpSecurity>> customizeCors() {
        return cors -> cors
                .configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://127.0.0.1:5173"));
                    configuration.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(List.of("*"));
                    configuration.setAllowCredentials(true);

                    return configuration;
                });
    }
}
