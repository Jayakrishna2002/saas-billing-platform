package com.project.serviceImpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dto.CreateUserRequest;
import com.project.dto.UserResponse;
import com.project.modal.Tenant;
import com.project.modal.User;
import com.project.repository.TenantRepository;
import com.project.repository.UserRepository;
import com.project.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public TenantRepository tenantRepository;
	
	public UserServiceImpl(UserRepository userRepository, TenantRepository tenantRepository) {
		this.userRepository = userRepository;
		this.tenantRepository = tenantRepository;
	}

	@Override
	public UserResponse createUser( UUID tenantId, CreateUserRequest request ) {
		
		if (tenantId == null) {
	        throw new IllegalArgumentException("Tenant ID must not be null");
	    }
		
		Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new RuntimeException("Tenant not found"));
		
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setTenant( tenant); 
		user.setEmail(request.getEmail());
	    user.setName(request.getName());
	    user.setStatus("ACTIVE");
	    user.setCreatedAt( Instant.now() );
	    
	    try {
	        userRepository.save(user);
	    } catch (DataIntegrityViolationException ex) {
	        throw new RuntimeException("Email already exists for this tenant");
	    }
	    
	    return new UserResponse( user.getTenant().getId(), user.getEmail(), user.getName(), user.getStatus(), user.getCreatedAt() );
	}

	@Override
	public Page<UserResponse> findAllUsersByTenantId(UUID tenantId, Pageable pageable) {

		if (tenantId == null) {
			throw new IllegalArgumentException("Tenant ID must not be null");	
		}

		return userRepository.findByTenant_Id(tenantId, pageable).map(this:: mapToResponse);
	}

	@Override
	public Optional<UserResponse> findUserByIdAndTenantId(UUID userId, UUID tenantId) {
		
		if (tenantId == null || userId == null) {
			throw new IllegalArgumentException("Tenant ID or User Id must not be null");
		}
				
		return userRepository.findByIdAndTenant_Id(userId, tenantId).map(this::mapToResponse);
	}
	
	private UserResponse mapToResponse(User user) {
		return new UserResponse(user.getTenant().getId(), user.getEmail(), user.getName(), user.getStatus(), user.getCreatedAt());
	}

	@Override
	@Transactional
	public void deleteByUserIdAndTenantId(UUID userId, UUID tenantId) {
		userRepository.deleteByIdAndTenant_Id( userId, tenantId );
	}

	@Override
	public boolean existsByEmailAndTenantId(String email, UUID tenantId) {
		
		if ( tenantId != null ) {
			return userRepository.existsByEmailAndTenant_Id( email, tenantId );
		}
		
		return false;
	}

}
