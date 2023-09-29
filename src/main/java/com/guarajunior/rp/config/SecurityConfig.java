package com.guarajunior.rp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						
						//user
							//users
							.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
							.requestMatchers(HttpMethod.POST, "/auth/registro").permitAll()
							.requestMatchers(HttpMethod.GET, "/usuarios").hasAuthority("RH")
							.requestMatchers("/usuarios").hasAuthority("canWriteUser")
							//course
							.requestMatchers(HttpMethod.GET, "/cursos").hasAnyAuthority("canView")
							.requestMatchers("/cursos").hasAuthority("canWriteUser")
							//departments
							.requestMatchers(HttpMethod.GET, "/departamentos").hasAnyAuthority("canViewUser", "canWriteUser")
							.requestMatchers("/departamentos").hasAuthority("canWriteUser")
							//role
							.requestMatchers(HttpMethod.GET, "/cargos").hasAnyAuthority("canViewUser", "canWriteUser")
							.requestMatchers("/cargos").hasAuthority("canWriteUser")
							//authority
							.requestMatchers(HttpMethod.GET, "/autoridades").hasAnyAuthority("canViewUser", "canWriteUser")
							.requestMatchers("/autoridades").hasAuthority("canWriteUser")
						//warehouse
						.requestMatchers(HttpMethod.GET, "/produtos").hasAnyAuthority("canViewWarehouse", "canWriteWarehouse")
						.requestMatchers("/produtos").hasAuthority("canWriteWarehouse")
						//financial
							//transaction
							.requestMatchers(HttpMethod.GET, "/transacoes").hasAnyAuthority("canViewFinancial", "canWriteFinancial")
							.requestMatchers("/transacoes").hasAuthority("canWriteFinancial")
							//account bank
							.requestMatchers(HttpMethod.GET, "/bancos").hasAnyAuthority("canViewFinancial", "canWriteFinancial")
							.requestMatchers("/bancos").hasAuthority("canWriteFinancial")
						//company
						.requestMatchers(HttpMethod.GET, "/empresas").hasAnyAuthority("canViewCompany", "canWriteCompany")
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
