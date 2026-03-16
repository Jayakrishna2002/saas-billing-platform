
package com.project.exception.handler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.dto.ErrorResponse;
import com.project.exception.conflict.DuplicateUserException;
import com.project.exception.notFound.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
	
	@ExceptionHandler( IllegalArgumentException.class )
	public ResponseEntity<ErrorResponse> handleBadRequest( IllegalArgumentException ex, HttpServletRequest request )
	{
		
		log.error( "Illegal Argument Exception", ex );
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage(), request.getRequestURI()
		);
		
		return new ResponseEntity<>( error, HttpStatus.BAD_REQUEST );
	}
	
	@ExceptionHandler( NoSuchElementException.class )
	public ResponseEntity<ErrorResponse> handleNotFound( NoSuchElementException ex, HttpServletRequest request )
	{
		
		log.error( "No Such Element Exception", ex );
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(), request.getRequestURI()
		);
		
		return new ResponseEntity<>( error, HttpStatus.NOT_FOUND );
	}
	
	@ExceptionHandler( Exception.class )
	public ResponseEntity<ErrorResponse> handleGlobalException( Exception ex, HttpServletRequest request )
	{
		
		log.error( "Generic Exception", ex );
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", ex.getMessage(),
				request.getRequestURI()
		);
		
		return new ResponseEntity<>( error, HttpStatus.INTERNAL_SERVER_ERROR );
	}
	
	@ExceptionHandler( DuplicateUserException.class )
	public ResponseEntity<ErrorResponse> handleDuplicateUserException(
			DuplicateUserException ex, HttpServletRequest request
	)
	{
		
		log.error( "Duplicate User Exception", ex );
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Duplicate User Exception", ex.getMessage(),
				request.getRequestURI()
		);
		
		return new ResponseEntity<>( error, HttpStatus.CONFLICT );
	}
	
	@ExceptionHandler( NotFoundException.class )
	public ResponseEntity<ErrorResponse> handleNotFoundException( NotFoundException ex, HttpServletRequest request )
	{
		
		log.error( "Resource Not Found Exception", ex );
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Resource Not Found Exception", ex.getMessage(),
				request.getRequestURI()
		);
		
		return new ResponseEntity<>( error, HttpStatus.NOT_FOUND );
	}
	
}
