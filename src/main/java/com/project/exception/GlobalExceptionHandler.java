package com.project.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest( IllegalArgumentException ex, HttpServletRequest request){
		
		ErrorResponse error = new ErrorResponse(
									LocalDateTime.now(),
									HttpStatus.BAD_REQUEST.value(),
									"Bad Request",
									ex.getMessage(),
									request.getRequestURI()
								);
		
		return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleNotFound( NoSuchElementException ex, HttpServletRequest request ){
		
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				"Not Found",
				"The requested resource was not found",
				request.getRequestURI()
			);

		return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException( Exception ex, HttpServletRequest request ){
		
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error",
                "An unexpected error occurred. Please contact support.",
				request.getRequestURI()
			);

		return new ResponseEntity<>(error , HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
