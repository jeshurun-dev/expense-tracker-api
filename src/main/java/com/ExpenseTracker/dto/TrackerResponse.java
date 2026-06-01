package com.ExpenseTracker.dto;

public class TrackerResponse {

    private int id;
	
	private String title;
	
	private double amount;
	
	private String category;
	
	private String description;
	
	private int userId;
	
	private String userName;

	public TrackerResponse(int id, String title, double amount, String category, String description) {
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.category = category;
		this.description = description;
	}
	
	public TrackerResponse(int id, String title, double amount, String category, String description, int userId) {
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.category = category;
		this.description = description;
		this.userId = userId;
	}
	
	public TrackerResponse(int id, String title, double amount, String category, String description, String userName) {
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.category = category;
		this.description = description;
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public int userId() {
		return userId;
	}
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

}
