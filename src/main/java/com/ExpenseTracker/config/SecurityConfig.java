package com.ExpenseTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ExpenseTracker.security.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		return http.csrf(csrf -> csrf.disable())
		    .authorizeHttpRequests(auth -> auth 
		    		
		    		.requestMatchers(
		    				HttpMethod.POST, "/users")
		    		.permitAll()
		    		.requestMatchers(
		    				HttpMethod.POST,"/auth/login")
		    		.permitAll()
		    		.requestMatchers(HttpMethod.GET,"/users/my-expenses")
		    		.hasAnyRole("USER","ADMIN")
		    		.requestMatchers(HttpMethod.GET,"/users/all-users")
		    		.hasRole("ADMIN")
		    		.requestMatchers(HttpMethod.GET,"/users/*")
		    		.hasRole("ADMIN")
		    		.requestMatchers(HttpMethod.PUT,"/users/*")
		    		.hasRole("ADMIN")
		    		.requestMatchers(HttpMethod.DELETE,"/users/*")
		    		.hasRole("ADMIN")
		    		
		    		.anyRequest()
		    		.authenticated()
		    		)
		    .addFilterBefore(jwtAuthenticationFilter,  UsernamePasswordAuthenticationFilter.class)
		    .build();
		
	}

	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		
		return config.getAuthenticationManager();
	}
	
}
