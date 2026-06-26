package com.ExpenseTracker.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ExpenseTracker.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
//run JWT authentication filter exactly once per request
	
	private JwtService jwtService;
	private CustomUserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization"); //get header to string
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			//if header is null or doesnt start with "Bearer "
			//then continue, no need JWT validation
			filterChain.doFilter(request, response);
			
			return;
		}
		
		String jwtToken = authHeader.substring(7);//the token start from index 7
		//extract the token
		String username = jwtService.extractUsername(jwtToken);//we get username from JWT

		UserDetails userDetails = userDetailsService.loadUserByUsername(username); //we load userdetails from DB
		
		if(jwtService.validateToken(jwtToken, userDetails)) {
			//validate the token
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
			//creates authentication
			SecurityContextHolder.getContext().setAuthentication(authToken);
			//store the created authentication in SexurityContextHolder
		}
		//continue request now
		filterChain.doFilter(request, response);
	}
}
