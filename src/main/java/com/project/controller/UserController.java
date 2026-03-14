
package com.project.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.UserRequest;
import com.project.dto.UserResponse;
import com.project.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping( "/api/v1/users" )
@Slf4j
public class UserController
{
	
	private final UserService userService;
	
	public UserController( UserService userService )
	{
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(
			@RequestHeader( "X-Tenant-ID" ) UUID tenantId, @Valid @RequestBody UserRequest request
	)
	{
		
		log.debug( "DEBUG: Tenant ID is " + tenantId );
		return ResponseEntity.status( HttpStatus.CREATED ).body( ( userService.createUser( tenantId, request ) ) );
	}
	
	@GetMapping
	public ResponseEntity<Page<UserResponse>> getAllUsers(
			@RequestHeader( "X-Tenant-ID" ) UUID tenantId,
			@PageableDefault( size = 20, sort = "createdAt", direction = Direction.DESC ) Pageable pageable
	)
	{
		
		log.debug( "DEBUG: Tenant ID is " + tenantId );
		Page<UserResponse> userRes = userService.findAllUsersByTenantId( tenantId, pageable );
		return ResponseEntity.status( HttpStatus.FOUND ).body( userRes );
	}
	
	@GetMapping( "/{userId}" )
	public ResponseEntity<UserResponse> getUser(
			@RequestHeader( "X-Tenant-ID" ) UUID tenantId, @PathVariable UUID userId
	)
	{
		log.debug( "DEBUG: User ID is " + userId );
		
		UserResponse userResponse = userService.findUserByIdAndTenantId( userId, tenantId );
		
		return ResponseEntity.status( HttpStatus.FOUND ).body( userResponse );
	}
	
	@PutMapping( "/{userId}" )
	public ResponseEntity<UserResponse> updateUser(
			@RequestHeader( "X-Tenant-ID" ) UUID tenantId, @PathVariable UUID userId,
			@Valid @RequestBody UserRequest request
	)
	{
		log.debug( "DEBUG: User ID is " + userId );
		
		UserResponse userResponse = userService.updateUserByIdAndTenantId( userId, tenantId, request );
		
		return ResponseEntity.status( HttpStatus.OK ).body( userResponse );
	}
	
	@DeleteMapping( "/{userId}" )
	public ResponseEntity<?> removeUser( @RequestHeader( "X-Tenant-ID" ) UUID tenantId, @PathVariable UUID userId )
	{
		
		log.debug( "DEBUG: Tenant ID is " + tenantId );
		log.debug( "DEBUG: User ID is " + userId );
		
		UserResponse userRes = userService.deleteUser( userId, tenantId );
		return ResponseEntity.status( HttpStatus.NO_CONTENT ).body( userRes );
	}
	
}
