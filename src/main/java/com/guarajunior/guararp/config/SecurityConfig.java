package com.guarajunior.guararp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
public class SecurityConfig {
	@Autowired
	SecurityFilter securityFilter;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						
						//user
							//users
							.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
							.requestMatchers(HttpMethod.POST, "/auth/registro").permitAll()
							.requestMatchers(HttpMethod.POST, "/auth/recuperar-senha").permitAll()
							.requestMatchers(HttpMethod.POST, "/auth/reset-password").permitAll()
							.requestMatchers(HttpMethod.GET, "/usuarios").hasAuthority("RH")
							.requestMatchers("/usuarios").hasAuthority("canWriteUser")
							//course
							.requestMatchers(HttpMethod.GET, "/cursos").hasAnyAuthority("RH")
							.requestMatchers("/cursos").hasAuthority("canWriteUser")
							//departments
							.requestMatchers(HttpMethod.GET, "/departamentos").hasAnyAuthority("RH")
							.requestMatchers("/departamentos").hasAuthority("canWriteUser")
							//role
							.requestMatchers(HttpMethod.GET, "/cargos").hasAnyAuthority("RH")
							.requestMatchers("/cargos").hasAuthority("canWriteUser")
							//authority
							.requestMatchers(HttpMethod.GET, "/autoridades").hasAnyAuthority("RH")
							.requestMatchers("/autoridades").hasAuthority("canWriteUser")
						//warehouse
						.requestMatchers(HttpMethod.GET, "/produtos").hasAnyAuthority("ALMOXARIFADO")
						.requestMatchers("/produtos").hasAuthority("canWriteWarehouse")
						//financial
							//transaction
							.requestMatchers(HttpMethod.GET, "/transacoes").hasAnyAuthority("FINANCEIRO")
							.requestMatchers("/transacoes").hasAuthority("canWriteFinancial")
							//account bank
							.requestMatchers(HttpMethod.GET, "/bancos").hasAnyAuthority("FINANCEIRO")
							.requestMatchers("/bancos").hasAuthority("canWriteFinancial")
						//company
						.requestMatchers(HttpMethod.GET, "/empresas").hasAnyAuthority("PROJETOS")
						.requestMatchers("/empresas").hasAuthority("canWriteCompany")
						//ADM
						.requestMatchers(HttpMethod.GET, "/**").hasAuthority("admView")
						.requestMatchers(HttpMethod.POST, "/**").hasAuthority("admWrite")

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
