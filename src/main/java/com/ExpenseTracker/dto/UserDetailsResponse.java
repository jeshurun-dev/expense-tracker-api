package com.ExpenseTracker.dto;

import java.util.List;

import com.ExpenseTracker.entity.TrackerEntity;

public class UserDetailsResponse {

	private int userId;
	
	private String name;
	
	private String email;

	private List<TrackerEntity> expenses;

	public UserDetailsResponse(int userId, String name, String email, List<TrackerEntity> expenses) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.setExpenses(expenses);
	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public List<TrackerEntity> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<TrackerEntity> expenses) {
		this.expenses = expenses;
	}
	
}
