package com.project.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.dto.CreateUserRequest;
import com.project.dto.UserResponse;
import com.project.modal.User;

public interface UserService {
	
	public UserResponse createUser( UUID tenantId, CreateUserRequest request );
	
	public Page<UserResponse> findAllUsersByTenantId( UUID tenantId, Pageable pageable );
	
	public Optional<UserResponse> findUserByIdAndTenantId( UUID userId, UUID tenantId );
	
	public boolean existsByEmailAndTenantId(String email, UUID tenantId);
	
	public void deleteByUserIdAndTenantId( UUID userId, UUID tenantId );
}
