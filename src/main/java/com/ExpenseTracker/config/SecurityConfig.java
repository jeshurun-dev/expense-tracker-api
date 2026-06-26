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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ExpenseTracker.exceptions.CustomAccessDeniedHandler;
import com.ExpenseTracker.security.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private JwtAuthenticationFilter jwtAuthenticationFilter;
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,CustomAccessDeniedHandler customAccessDeniedHandler) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.customAccessDeniedHandler = customAccessDeniedHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	//for frontend end connection
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();

	    configuration.addAllowedOrigin("http://localhost:5173");
	    configuration.addAllowedMethod("*");
	    configuration.addAllowedHeader("*");
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source =
	            new UrlBasedCorsConfigurationSource();

	    source.registerCorsConfiguration("/**", configuration);

	    return source;
	} 
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		return http
		        .cors(cors -> {}) //added to connect the front end
		        .csrf(csrf -> csrf.disable())
		        .authorizeHttpRequests(auth -> auth 
		    		
		    		.requestMatchers(
		    				HttpMethod.POST, "/users")
		    		.permitAll()
		    		.requestMatchers(
		    				HttpMethod.POST,"/auth/login")
		    		.permitAll()
		    		.requestMatchers(
		    		        "/swagger-ui/**",
		    		        "/v3/api-docs/**"
		    		)
		    		.permitAll()
		    		.requestMatchers(HttpMethod.GET,"/users/my-expenses")
		    		.hasAnyRole("USER","ADMIN")
		    		.requestMatchers(HttpMethod.GET,"/users/**")
		    		.hasRole("ADMIN")
		    		.requestMatchers(HttpMethod.PATCH,"/users/*")
		    		.hasAnyRole("USER","ADMIN")
		    		.requestMatchers(HttpMethod.DELETE,"/users/**")
		    		.hasRole("ADMIN")
		    		.requestMatchers(HttpMethod.POST,"/expenses")
		    		.hasAnyRole("USER","ADMIN")
		    		.requestMatchers(HttpMethod.GET,"/expenses/paginated")
		    		.hasAnyRole("ADMIN")
		    		.requestMatchers(HttpMethod.PUT,"/expenses/*")
		    		.hasAnyRole("USER","ADMIN")
		    		.requestMatchers(HttpMethod.DELETE,"/expenses/*")
		    		.hasAnyRole("USER","ADMIN")
		    		.requestMatchers(HttpMethod.GET,"/expenses/expense-user")
		    		.hasAnyRole("ADMIN")
		    		.anyRequest()
		    		.authenticated()
		    		)
		    .addFilterBefore(jwtAuthenticationFilter,  UsernamePasswordAuthenticationFilter.class)
		    .exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler))
		    .build();
		
	}

	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		
		return config.getAuthenticationManager();
	}
	
}
