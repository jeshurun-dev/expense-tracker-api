package com.ExpenseTracker.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ExpenseTracker.entity.UserEntity;
import com.ExpenseTracker.exceptions.UserNotFoundException;
import com.ExpenseTracker.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email){
		
		UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found"));
		
		return User.withUsername(user.getEmail())
				   .password(user.getPassword())
				   .roles(user.getRole())
				   .build();
	}
}
