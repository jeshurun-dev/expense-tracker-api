package com.ExpenseTracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {

	@NotBlank(message = "name is required")
	private String name;
	
	@Email(message = "please use proper email format")
	@NotBlank(message = "email is required")
	private String email;
	
	@Size(min = 6, message ="password should be atleast 6 characters long")
	private String password;
	
//	private String role;

	public UserRequest(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public UserRequest(String name, String email, String password) {//, String role) {
		this.name = name;
		this.email = email;
		this.password = password;
//		this.role = role;
	}

	public UserRequest() {
	
	}
	
	public String getPassword() {
		return password;
	}

//	public String getRole() {
//		return role;
//	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public void setRole(String role) {
//		this.role = role;
//	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
