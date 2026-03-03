package com.project.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.project.dto.CreateUserRequest;
import com.project.dto.UserResponse;
import com.project.modal.Users;

public interface UserService {
	
	public UserResponse createUser( UUID tenantId, CreateUserRequest request );
	
	public List<Users> findAllUsersByTenantId( UUID tenantId );
	
	public Optional<Users> findUserByIdAndTenantId( UUID userId, UUID tenantId );
	
	public boolean existsByEmailAndTenantId(String email, UUID tenantId);
	
	public void deleteByUserIdAndTenantId( UUID userId, UUID tenantId );
}
