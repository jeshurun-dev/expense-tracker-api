package com.ExpenseTracker.dto;

public class CategoryExpenseSummary {

	private String category;
	
	private Double amount;
	
	public CategoryExpenseSummary(String category, Double amount) {
		this.category = category;
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public Double getAmount() {
		return amount;
	}

}
