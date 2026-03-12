
package com.project.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.dto.CreateUserRequest;
import com.project.dto.UserResponse;

public interface UserService
{
	
	public UserResponse createUser( UUID tenantId, CreateUserRequest request );
	
	public Page<UserResponse> findAllUsersByTenantId( UUID tenantId, Pageable pageable );
	
	public Optional<UserResponse> findUserByIdAndTenantId( UUID userId, UUID tenantId );
	
	public boolean existsByEmailAndTenantId( String email, UUID tenantId );
	
	public UserResponse deleteUser( UUID userId, UUID tenantId );
	
}
