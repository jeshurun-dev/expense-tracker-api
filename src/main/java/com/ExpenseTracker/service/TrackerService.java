package com.ExpenseTracker.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ExpenseTracker.dto.CategoryExpenseSummary;
import com.ExpenseTracker.dto.ExpenseUserResponse;
import com.ExpenseTracker.dto.TrackerRequest;
import com.ExpenseTracker.dto.TrackerResponse;
import com.ExpenseTracker.entity.TrackerEntity;
import com.ExpenseTracker.entity.UserEntity;
import com.ExpenseTracker.exceptions.ExpenseNotFoundException;
import com.ExpenseTracker.exceptions.UserNotFoundException;
import com.ExpenseTracker.repository.TrackerRepository;
import com.ExpenseTracker.repository.UserRepository;


@Service
public class TrackerService {

	private final TrackerRepository repository;
	
	private final UserRepository userRepository;
	
	public TrackerService(TrackerRepository repository, UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(TrackerService.class);
	
	//helper method for handling nullpointer exception due to no relationship between user and expense early on
	private TrackerResponse mapToResponse(TrackerEntity expense) {
		
		String userName = null;
		
		if(expense.getUser() != null) {
			userName = expense.getUser().getName();
		}
		
		return new TrackerResponse(expense.getId(), expense.getTitle(), expense.getAmount(), expense.getCategory(), expense.getDescription(), userName);
	}
	
	//Service Methods
	public TrackerResponse addExpense(TrackerRequest request) {
		
		TrackerEntity expense = new TrackerEntity(request.getTitle(), request.getAmount(), request.getCategory(), request.getDescription());
		
		UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
		
		expense.setUser(user);
		
		logger.info("Adding new expense: {}",request.getTitle());
		
		repository.save(expense);
		
		logger.info(
			    "Expense added successfully"
			);
		
		return new TrackerResponse(expense.getId(), expense.getTitle(), expense.getAmount(), expense.getCategory(), expense.getDescription(), expense.getUser().getName());
	}
	
	public List<TrackerResponse> getAllExpenses(int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<TrackerEntity> pageResult = repository.findAll(pageable);
		
		logger.info("expenses fetched successfully");
		//page<T> contains not just records, but total pages, total elements, current page and page size, etc
		
		return pageResult.getContent().stream().map(expense -> mapToResponse(expense)).toList();

		//return repository.findAll(pageable).stream().map(expense -> new TrackerResponse(expense.getId(), expense.getTitle(), expense.getAmount(), expense.getCategory(), expense.getDescription())).toList();
	}
	
	public TrackerResponse getExpenseById(int id) {
		
		
		TrackerEntity expense = repository.findById(id).orElseThrow(() -> {
			logger.error("Expense not found with id: {}",id);
			return new ExpenseNotFoundException("Expense not found");
		});
		
		//since some expenses were created prior to the relationship to user, 
		//the function expense.getUser() = null and when we use expense.getUser().getName it is essentially null.getName() which throws null pointer exception
		//hence this tiny null exception escaping condition
		String userName = null;
		
		if(expense.getUser() != null) {
			userName = expense.getUser().getName();
		}
		
		return new TrackerResponse(expense.getId(), expense.getTitle(), expense.getAmount(), expense.getCategory(), expense.getDescription(), userName);
	}

	public TrackerResponse updateExpenseById(int id, TrackerRequest request) {
		
		TrackerEntity expense = repository.findById(id).orElseThrow(() -> {
			logger.error("Expense not found with id: {}",id);
			return new ExpenseNotFoundException("Expense not found");
		});
		
		UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(() -> {
			logger.error("User not found with id: {}",request.getUserId());
		return new UserNotFoundException("User not found");
		});
		
		expense.setTitle(request.getTitle());
		expense.setAmount(request.getAmount());
		expense.setCategory(request.getCategory());
		expense.setDescription(request.getDescription());
		expense.setUser(user);
		
		logger.info("updating expense with id: {}",id);
		
		repository.save(expense);
		
		logger.info("Expense update successfully");
		
		return new TrackerResponse(expense.getId(), expense.getTitle(), expense.getAmount(), expense.getCategory(), expense.getDescription(), expense.getUser().getName());
	}

	public TrackerResponse deleteExpenseById(int id) {
		
		TrackerEntity expense = repository.findById(id).orElseThrow(() -> {
			logger.error("Expense not found with id: {}",id);
			return new ExpenseNotFoundException("Expense not found");
		});
		
		logger.info("Deleting expense with id: {}",id);
		
		repository.delete(expense);
		
		logger.info("Expense deleted successfully");
		
		return new TrackerResponse(id, expense.getTitle(), expense.getAmount(), expense.getCategory(), expense.getDescription());
	}

	public List<TrackerResponse> getExpensesByCategory(String category) {
		
		return repository.findByCategory(category).stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getExpenseByTitle(String title) {
		
		return repository.findByTitle(title).stream().map(expense -> mapToResponse(expense)).toList();
		
	}

	public List<TrackerResponse> getExpenseByCategoryAndTitle(String category, String title) {
		
		return repository.findByCategoryAndTitle(category, title).stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getSortedExpenses(String sortBy) {
		
		Sort sort = Sort.by(sortBy);
		
		return repository.findAll(sort).stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getSortedExpensesDescending(String sortBy) {
		
		Sort sort = Sort.by(sortBy).descending();
		
		return repository.findAll(sort).stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getPaginatedAndSortedExpenses(int page, int size, String sortBy) {
		
		Pageable pageable  = PageRequest.of(page, size, Sort.by(sortBy));
		
		return repository.findAll(pageable).stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getPaginatedAndSortedExpensesDesc(int page, int size, String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
		
		Page<TrackerEntity> pageResult = repository.findAll(pageable);
		
		return pageResult.getContent().stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getExpensesByCategory(String category, int page, int size, String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		
		Page<TrackerEntity> pageResult = repository.findByCategory(category, pageable);
		
		return pageResult.getContent().stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getExpensesContainingKeyword(int page, int size, String sortBy, String keyword) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		
		Page<TrackerEntity> pageResult = repository.findByTitleContainingIgnoreCase(keyword, pageable);
		
		return pageResult.getContent().stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getExpensesByAmountGreaterThan(int page, int size, String sortBy, double amount) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		
		Page<TrackerEntity> pageResult = repository.findByAmountGreaterThan(amount, pageable);
		
		return pageResult.getContent().stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getExpensesByAmountBetween(int page, int size, String sortBy, double minAmount,
			double maxAmount) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		
		Page<TrackerEntity> pageResult = repository.findByAmountBetween(minAmount, maxAmount, pageable);
		
		return pageResult.getContent().stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getExpensesByCategoryAndKeyword(int page, int size, String category, String keyword) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<TrackerEntity> pageResult = repository.findExpensesByCategoryAndKeyword(category, keyword, pageable);
		
		return pageResult.getContent().stream().map(expense -> mapToResponse(expense)).toList();
	}

	public List<TrackerResponse> getExpensesByAmountAndKeyword(int page, int size, double amount, String keyword) {

		Pageable pageable = PageRequest.of(page, size);
		
		Page<TrackerEntity> pageResult = repository.findByAmountAndKeyword(amount, keyword, pageable);
		
		return pageResult.stream().map(expense -> mapToResponse(expense)).toList();
	}

	public Double getTotalExpensesByCategory(String category) {
		
		return repository.getTotalAmountByCategory(category); 
	}

	public Map<String, Object> getExpensesOverview() {
	
		Double totalExpense = repository.getTotalExpenses();
		Double avgExpenses = repository.getAvgExpenses();
		Double maxExpense = repository.getHighestExpense();
		Double minExpense = repository.getLowestExpense();
		Long expenseCount = repository.getExpensesCount();
		
		Map<String, Object> expensesOverview = new HashMap<>();
		
		expensesOverview.put("Total Expenses", totalExpense);
		expensesOverview.put("Average Expenses", avgExpenses);
		expensesOverview.put("Highest Expense", maxExpense);
		expensesOverview.put("Lowest Expense", minExpense);
		expensesOverview.put("Expenses Count", expenseCount);
		
		return expensesOverview;
	}

	public List<ExpenseUserResponse> getExpensesAndUser(int page, int size, String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		
		Page<ExpenseUserResponse> pageResult = repository.getAllExpensesAndUsers(pageable);
		
		return pageResult.getContent();
	}

	public List<CategoryExpenseSummary> getCategorySummary(Double amount) {
		
		return repository.getCategorySummary(amount);
	}

}
