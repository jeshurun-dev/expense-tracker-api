package com.ExpenseTracker.dto;

import jakarta.validation.constraints.Size;

public class PasswordUpdateRequest {

	@Size(min = 8, message ="password cannot be lesser than 8 characters")
	private String oldPassword;
	
	@Size(min = 8, message ="password cannot be lesser than 8 characters")
	private String newPassword;

	public PasswordUpdateRequest(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
