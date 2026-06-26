package com.ExpenseTracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class TrackerRequest {
	
	@NotBlank(message = "Title is required")
	private String title;
	
	@Min(1)
	private double amount;
	
	@NotBlank(message = "Category is required")
	private String category;
	
	@NotBlank(message = "Description is required")
	private String description;
	
	
	public TrackerRequest(String title, double amount, String category, String description) {
		this.title = title;
		this.amount = amount;
		this.category = category;
		this.description = description;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
