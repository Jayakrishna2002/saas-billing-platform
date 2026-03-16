
package com.project.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.dto.UserRequest;
import com.project.dto.UserResponse;
import com.project.pagination.PagedResponse;

public interface UserService
{
	
	public UserResponse createUser( UserRequest request );
	
	public PagedResponse<UserResponse> findAllUsers( Pageable pageable );
	
	public UserResponse findUserById( UUID tenantId );
	
	public boolean existsByEmail( String email );
	
	public UserResponse deleteUser( UUID userId );

	public UserResponse updateUserById( UUID userId, UserRequest request );
	
}
