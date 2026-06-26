package com.ExpenseTracker.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse<MessageResponse>> handleAccessDeniedException(AccessDeniedException ex){
		
		ApiResponse<MessageResponse> apiResponse = new ApiResponse<MessageResponse>(false, ex.getMessage(), null);
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ApiResponse<MessageResponse>> handleInvalidPasswordException(InvalidPasswordException ex){
		
		ApiResponse<MessageResponse> apiResponse = new ApiResponse<MessageResponse>(false, ex.getMessage(), null);
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<MessageResponse>> handleBadCredentialsException(BadCredentialsException ex){
		
		ApiResponse<MessageResponse> apiResponse = new ApiResponse<MessageResponse>(false, "Invalid Email or password", null);
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
	}
}

