package com.project.serviceImpl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dto.CreateUserRequest;
import com.project.dto.UserResponse;
import com.project.modal.Tenants;
import com.project.modal.Users;
import com.project.repository.TenantRepository;
import com.project.repository.UserRepository;
import com.project.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public TenantRepository tenantRepository;

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void setTenantRepository(TenantRepository tenantRepository) {
		this.tenantRepository = tenantRepository;
	}
	
	@Override
	public UserResponse createUser( UUID tenantId, CreateUserRequest request ) {
		
		if (tenantId == null) {
	        throw new IllegalArgumentException("Tenant ID must not be null");
	    }
		
		Tenants tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new RuntimeException("Tenant not found"));
		
		Users user = new Users();
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
	    
	    return new UserResponse( user.getId(), user.getEmail(), user.getName(), user.getStatus(), user.getCreatedAt() );
	}

	@Override
	public List<Users> findAllUsersByTenantId(UUID tenantId) {
		if (tenantId != null) {
			return userRepository.findByTenant_Id( tenantId );
		}
		return (List<Users>) new Users();
	}

	@Override
	public Optional<Users> findUserByIdAndTenantId(UUID userId, UUID tenantId) {
		
		if ( tenantId != null ) {
			return userRepository.findByIdAndTenant_Id(userId, tenantId);
		}
		
		return Optional.empty();
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
