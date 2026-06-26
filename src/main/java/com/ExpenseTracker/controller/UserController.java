package com.ExpenseTracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ExpenseTracker.dto.ApiResponse;
import com.ExpenseTracker.dto.PasswordUpdateRequest;
import com.ExpenseTracker.dto.UserDetailsResponse;
import com.ExpenseTracker.dto.UserRequest;
import com.ExpenseTracker.dto.UserResponse;
import com.ExpenseTracker.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(
		name ="Users",
		description = "User manangement APIs")
public class UserController {

	private final UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@Operation(
			summary = "Register a new user")
	@PostMapping("/users")
	public ResponseEntity<ApiResponse<UserResponse>> addUser(@Valid @RequestBody UserRequest request){
	
		UserResponse response = service.addUser(request);
		
		ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>(true, "User added successfully", response);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}
	
	@Operation(
			summary = "Get all users (Admin only)")
	@GetMapping("users/all-users")
	public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
		
		List<UserResponse> response = service.getAllUsers();
		
		ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<List<UserResponse>>(true, "Users fetched successfully", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	@Operation(
			summary = "Get user details by User ID (Admin only)")
	@GetMapping("/users/user-details/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable int id) {

		UserResponse user = service.getUserById(id);
		
	    ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>(true, "User fetched successfully", user);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}
	
	@Operation(
			summary = "Change/Update password")
	@PatchMapping("/users/change-password")
	public ResponseEntity<ApiResponse<String>> udpateUser(@Valid @RequestBody PasswordUpdateRequest request){
		
		String response =  service.updatePassword(request);
		
		ApiResponse<String> apiResponse = new ApiResponse<String>(true, "User updated successfuly", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@Operation(
			summary = "Get current user's expeneses")
	@GetMapping("/users/my-expenses")
	public ResponseEntity<ApiResponse<UserDetailsResponse>> getExpensesByUser() {

		UserDetailsResponse user = service.getExpensesByUser();
		
	    ApiResponse<UserDetailsResponse> apiResponse = new ApiResponse<UserDetailsResponse>(true, "User fetched successfully", user);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}
	
	@Operation(
			summary = "Delete an expense")
	@DeleteMapping("/users/delete-user/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable int id){
		
		UserResponse user = service.deleteUserById(id);
		
		ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>(true, "User deleted successfully", user);
		
		return ResponseEntity.ok(apiResponse);
	}
}
