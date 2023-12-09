package com.guarajunior.guararp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guarajunior.guararp.infra.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // RH
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/cursos", "/departamentos", "/cargos", "/autoridades").hasAnyAuthority(Permission.CAN_VIEW.name() + "_ON_RH", Permission.CAN_VIEW.name() + "_ON_GLOBAL")
                        .requestMatchers("/usuarios", "/cursos", "/departamentos", "/cargos", "/autoridades").hasAnyAuthority(Permission.CAN_WRITE.name() + "_ON_RH", Permission.CAN_WRITE.name() + "_ON_GLOBAL")

                        // Warehouse
                        .requestMatchers(HttpMethod.GET, "/produtos").hasAnyAuthority(Permission.CAN_VIEW.name() + "_ON_ALMOXARIFADO", Permission.CAN_VIEW.name() + "_ON_GLOBAL")
                        .requestMatchers("/produtos").hasAnyAuthority(Permission.CAN_WRITE.name() + "_ON_ALMOXARIFADO", Permission.CAN_WRITE.name() + "_ON_GLOBAL")

                        // Financial
                        .requestMatchers(HttpMethod.GET, "/transacoes", "/bancos").hasAnyAuthority(Permission.CAN_VIEW.name() + "_ON_FINANCEIRO", Permission.CAN_VIEW.name() + "_ON_GLOBAL")
                        .requestMatchers("/transacoes", "/bancos").hasAnyAuthority(Permission.CAN_WRITE.name() + "_ON_FINANCEIRO", Permission.CAN_WRITE.name() + "_ON_GLOBAL")

                        // Projects
                        .requestMatchers(HttpMethod.GET, "/empresas").hasAnyAuthority(Permission.CAN_VIEW.name() + "_ON_PROJETOS", Permission.CAN_VIEW.name() + "_ON_GLOBAL")
                        .requestMatchers("/empresas").hasAnyAuthority(Permission.CAN_WRITE.name() + "_ON_PROJETOS", Permission.CAN_WRITE.name() + "_ON_GLOBAL")

                        // ADM
                        /*.requestMatchers("/**").hasAuthority(Permission.CAN_WRITE.name() + "_ON_GLOBAL")*/

                        .requestMatchers(
                                "/auth/**",
                                "/swagger/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources",
                                "/swagger-resources/**").permitAll()

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
}
