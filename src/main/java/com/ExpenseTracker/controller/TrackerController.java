package com.ExpenseTracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ExpenseTracker.dto.ApiResponse;
import com.ExpenseTracker.dto.CategoryExpenseSummary;
import com.ExpenseTracker.dto.ExpenseUserResponse;
import com.ExpenseTracker.dto.TrackerRequest;
import com.ExpenseTracker.dto.TrackerResponse;
import com.ExpenseTracker.service.TrackerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(
		name ="Expenses",
		description = "Expense Management APIs")
public class TrackerController {

	private final TrackerService service;
	
	public TrackerController(TrackerService service) {
		this.service = service;
	}
	
	@PostMapping("/expenses")
	public ResponseEntity<ApiResponse<TrackerResponse>> addExpense(@Valid @RequestBody TrackerRequest request){
		
		TrackerResponse response = service.addExpense(request);
		
		ApiResponse<TrackerResponse> apiResponse = new ApiResponse<TrackerResponse>(true, "Expense added successfully", response);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}
	
	@GetMapping("/expenses/paginated")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getAllExpenses(@RequestParam int page, @RequestParam int size){
		
		List<TrackerResponse> expenses = service.getAllExpenses(page, size);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses fetched successfully", expenses);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/{id}")
	public ResponseEntity<ApiResponse<TrackerResponse>> getExpenseById(@PathVariable int id){
		
		TrackerResponse response = service.getExpenseById(id);
		
		ApiResponse<TrackerResponse> apiResponse = new ApiResponse<TrackerResponse>(true, "Expense fetched successfully", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PutMapping("/expenses/{id}")
	public ResponseEntity<ApiResponse<TrackerResponse>> updateExpenseById(@PathVariable int id, @Valid @RequestBody TrackerRequest request){
		
		TrackerResponse response = service.updateExpenseById(id, request);
		
		ApiResponse<TrackerResponse> apiResponse = new ApiResponse<TrackerResponse>(true, "Expense updated successfully", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/expenses/{id}")
	public ResponseEntity<ApiResponse<TrackerResponse>> deleteExpenseById(@PathVariable int id){
		
		TrackerResponse response = service.deleteExpenseById(id);
		
		ApiResponse<TrackerResponse> apiResponse = new ApiResponse<TrackerResponse>(true, "Expense deleted successfully", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/category/{category}")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpensesByCategory(@PathVariable String category){
		
		List<TrackerResponse> response = service.getExpensesByCategory(category);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses by category", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/title/{title}")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpenseByTitle(@PathVariable String title){
		
		List<TrackerResponse> response = service.getExpenseByTitle(title);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses by title", response);
		
		return ResponseEntity.ok(apiResponse);
 	}
	
	@GetMapping("/expenses/category/{category}/title/{title}")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpenseByCategoryAndTitle(@PathVariable String category, @PathVariable String title){
		
		List<TrackerResponse> response = service.getExpenseByCategoryAndTitle(category, title);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses fetched successfully", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/sorted")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getSortedExpenses(@RequestParam String sortBy){
		
		List<TrackerResponse> response = service.getSortedExpenses(sortBy);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses sorted ascending and fetched successfully", response);

		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/sortedDesc")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getSortedExpensesDescending(@RequestParam String sortBy){
		
		List<TrackerResponse> response = service.getSortedExpensesDescending(sortBy);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses sorted descending and fetched successfully", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/paginated&sorted")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getPaginatedAndSortedExpenses(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy){
		
		List<TrackerResponse> response = service.getPaginatedAndSortedExpenses(page, size, sortBy);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses sorted ascending and fetched successfully", response);

		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/paginated&sortedDesc")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getPaginatedAndSortedExpensesDesc(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy){
		
		List<TrackerResponse> response = service.getPaginatedAndSortedExpensesDesc(page, size, sortBy);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses sorted descending and fetched successfully", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/filter")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpensesByCategory(@RequestParam String category, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy){
		
		List<TrackerResponse> response = service.getExpensesByCategory(category, page, size, sortBy);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses filtered by category", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/search")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpensesContainingKeyword(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String keyword){
		
		List<TrackerResponse> response = service.getExpensesContainingKeyword(page, size, sortBy, keyword);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses filtered by keyword", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/amount")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpenseGreaterThan(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam double amount){
		
		List<TrackerResponse> response = service.getExpensesByAmountGreaterThan(page, size, sortBy, amount);
		
        ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses filtered by expenses greater than the specified amount", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/amountRange")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpenseByAmountBetween(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam double minAmount, @RequestParam double maxAmount){
		
		List<TrackerResponse> response = service.getExpensesByAmountBetween(page, size, sortBy, minAmount, maxAmount);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses filtered by amount in a range", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/category/keyword")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpensesByCategoryAndKeyword(@RequestParam int page, @RequestParam int size, @RequestParam String category, @RequestParam String keyword){
		
		List<TrackerResponse> expenses = service.getExpensesByCategoryAndKeyword(page,size,category,keyword);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expenses fetched by category: "+category+" and keyword: "+keyword, expenses);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/amount/keyword")
	public ResponseEntity<ApiResponse<List<TrackerResponse>>> getExpenseByAmountAndKeyword(@RequestParam int page, @RequestParam int size, @RequestParam double amount, @RequestParam String keyword){
		
		List<TrackerResponse> response = service.getExpensesByAmountAndKeyword(page, size, amount, keyword);
		
		ApiResponse<List<TrackerResponse>> apiResponse = new ApiResponse<List<TrackerResponse>>(true, "Expensse fetched by amount less than "+amount+" and keyword: "+keyword, response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/totalAmount/category")
	public ResponseEntity<ApiResponse<Double>> getTotalExpensesByCategory(@RequestParam String category){
		
		double expenses = service.getTotalExpensesByCategory(category);
		
		ApiResponse<Double> apiResponse = new ApiResponse<Double>(true, "Total expenses by category: "+category, expenses);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/expensesOverview")
	public ResponseEntity<ApiResponse<Map<String, Object>>> getExpensesOverview(){
		
		Map<String, Object> expenseDetails = service.getExpensesOverview();
		
		ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<Map<String,Object>>(true, "Expenses Overview", expenseDetails);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/expense-user")
	public ResponseEntity<ApiResponse<List<ExpenseUserResponse>>> getExpensesAndUser(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy){
		
		List<ExpenseUserResponse> response = service.getExpensesAndUser(page, size, sortBy);
		
		ApiResponse<List<ExpenseUserResponse>> apiResponse = new ApiResponse<List<ExpenseUserResponse>>(true, "Expenses and users", response);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/expenses/category-summary")
	public ResponseEntity<ApiResponse<List<CategoryExpenseSummary>>> getCategorySummary(@RequestParam Double amount){
		
		List<CategoryExpenseSummary> response = service.getCategorySummary(amount);
		
		ApiResponse<List<CategoryExpenseSummary>> apiResponse = new ApiResponse<List<CategoryExpenseSummary>>(true, "Category Summary", response);
		
		return ResponseEntity.ok(apiResponse);
	}
}
