package com.ExpenseTracker.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ExpenseTracker.dto.ApiResponse;
import com.ExpenseTracker.dto.MessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpenseNotFoundException.class)
	public ResponseEntity<ApiResponse<MessageResponse>> handleExpenseNotFoundException(ExpenseNotFoundException ex){
		
		ApiResponse<MessageResponse> apiResponse = new ApiResponse<MessageResponse>(false, ex.getMessage(), null);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<MessageResponse>> handleUserNotFoundException(UserNotFoundException ex){
		
		ApiResponse<MessageResponse> response = new ApiResponse<MessageResponse>(false, ex.getMessage(), null);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleInputValidationError(MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<String, String>();
		
		ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		
		ApiResponse<Map<String, String>> apiResponse = new ApiResponse<Map<String,String>>(false, "Validation errors", errors);
		
		return ResponseEntity.badRequest().body(apiResponse);
	}
}

