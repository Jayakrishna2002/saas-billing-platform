package com.project.controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.CreateUserRequest;
import com.project.dto.UserResponse;
import com.project.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
        this.userService = userService;
    }
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestHeader("X-Tenant-ID") UUID tenantId,
			@Valid @RequestBody CreateUserRequest request) {
		
		return ResponseEntity.ok(
                userService.createUser(tenantId, request) );
	}
	
}
