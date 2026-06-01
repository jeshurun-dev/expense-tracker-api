package com.ExpenseTracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TrackerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private double amount;
	
	private String category;
	
	private String description;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private UserEntity user;
	
	public TrackerEntity() {
		
	}
	
	public TrackerEntity(String title, double amount, String category, String description) {
		this.title = title;
		this.amount = amount;
		this.category = category;
		this.description = description;
	}

	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
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

	public void setId(int id) {
		this.id = id;
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
