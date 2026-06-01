package com.ExpenseTracker.dto;

public class ExpenseUserResponse {

	private String title;
	
	private double amount;
	
	private String userName;

	public ExpenseUserResponse(String title, double amount, String userName) {
		this.title = title;
		this.amount = amount;
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public double getAmount() {
		return amount;
	}

	public String getUserName() {
		return userName;
	}

}
