package com.ExpenseTracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ExpenseTracker.dto.ApiResponse;
import com.ExpenseTracker.dto.UserDetailsResponse;
import com.ExpenseTracker.dto.UserRequest;
import com.ExpenseTracker.dto.UserResponse;
import com.ExpenseTracker.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	private final UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@PostMapping("/users")
	public ResponseEntity<ApiResponse<UserResponse>> addUser(@Valid @RequestBody UserRequest request){
	
		UserResponse response = service.addUser(request);
		
		ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>(true, "User added successfully", response);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}
	
	@GetMapping("users/all-users")
	public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
		
		List<UserResponse> response = service.getAllUsers();
		
		ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<List<UserResponse>>(true, "Users fetched successfully", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable int id) {

		UserResponse user = service.getUserById(id);
		
	    ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>(true, "User fetched successfully", user);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> udpateUser(@PathVariable int id, @Valid @RequestBody UserRequest request){
		
		UserResponse user = service.updateUserById(id, request);
		
		ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>(true, "User updated successfuly", user);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/users/my-expenses")
	public ResponseEntity<ApiResponse<UserDetailsResponse>> getExpensesByUser() {

		UserDetailsResponse user = service.getExpensesByUser();
		
	    ApiResponse<UserDetailsResponse> apiResponse = new ApiResponse<UserDetailsResponse>(true, "User fetched successfully", user);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}
	
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable int id){
		
		UserResponse user = service.deleteUserById(id);
		
		ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>(true, "User deleted successfully", user);
		
		return ResponseEntity.ok(apiResponse);
	}
}
