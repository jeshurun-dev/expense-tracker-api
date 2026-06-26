package com.ExpenseTracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ExpenseTracker.dto.ApiResponse;
import com.ExpenseTracker.dto.LoginRequest;
import com.ExpenseTracker.security.jwt.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(
		name = "Authentication",
		description = "Login and authentication APIs")
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;
	
	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	@Operation(
			summary = "Login user")
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request){
		
		
		//System.out.println("LOGIN HIT");
	    //System.out.println(request.getEmail());
	
			 Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(), 
						request.getPassword()
				)
		);
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			String token = jwtService.generateToken(userDetails);
			
			ApiResponse<String> ApiResponse = new ApiResponse<String>(true, "Login Successfull", token);
			
			return ResponseEntity.ok(ApiResponse);

	}

}
