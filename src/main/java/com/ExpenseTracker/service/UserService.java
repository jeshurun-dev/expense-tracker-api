package com.ExpenseTracker.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ExpenseTracker.dto.PasswordUpdateRequest;
import com.ExpenseTracker.dto.UserDetailsResponse;
import com.ExpenseTracker.dto.UserRequest;
import com.ExpenseTracker.dto.UserResponse;
import com.ExpenseTracker.entity.UserEntity;
import com.ExpenseTracker.exceptions.InvalidPasswordException;
import com.ExpenseTracker.exceptions.UserNotFoundException;
import com.ExpenseTracker.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository repository;
	
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);
	
	private static UserResponse mapToResponse(UserEntity user) {
		
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
	}
	
	//helper method to get current user details
	private UserEntity getCurrentUser() {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		UserEntity user = repository.findByEmail(email).orElseThrow(
				() -> new UserNotFoundException("User not found"));
		return user;
		
	}
	public UserResponse addUser(UserRequest request) {
		
		UserEntity user = new UserEntity(request.getName(), request.getEmail(), request.getPassword());
		
		//only users can create new users/accounts. There will be default admins added directly to DB
		user.setRole("USER");
		
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		logger.info("Adding new user: {}", request.getName());
		
		repository.save(user);
		
		logger.info("User added successfully");
		
		return mapToResponse(user);
	}
	
	public List<UserResponse> getAllUsers() {
		
		return repository.findAll().stream().map(user -> mapToResponse(user))
				.toList();
	}
	
	public UserResponse getUserById(int id) {
		
		UserEntity user = repository.findById(id).orElseThrow(() -> {
			logger.error("User not found with id: {}",id);
			return new UserNotFoundException("User not found");
			});
		
		return mapToResponse(user);		
	}
	
	public UserDetailsResponse getExpensesByUser() {
		
		UserEntity user = getCurrentUser();
		
		return new UserDetailsResponse(user.getId(), user.getName(), user.getEmail(), user.getExpenses());
		
	}

	public String updatePassword(PasswordUpdateRequest password) {
		
		UserEntity user = getCurrentUser();
				
		if(passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(password.getNewPassword()));
		}
		else {
			throw new InvalidPasswordException("Please enter your old password correctky");
		}
		
		repository.save(user);
		
		return "Password updated successfully";
		
	}

	public UserResponse deleteUserById(int id) {
		
		UserEntity user = repository.findById(id).orElseThrow(() -> {
			logger.error("User not found with id: {}",id);
			return new UserNotFoundException("User not found");
			});
		
		repository.deleteById(id);
		
		return mapToResponse(user);
	}

}
