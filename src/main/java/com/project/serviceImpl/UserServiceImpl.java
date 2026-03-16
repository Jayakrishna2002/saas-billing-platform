
package com.project.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dto.StatusType;
import com.project.dto.UserRequest;
import com.project.dto.UserResponse;
import com.project.exception.conflict.DuplicateUserException;
import com.project.exception.notFound.TenantNotFoundException;
import com.project.exception.notFound.UserNotFoundException;
import com.project.infrastructure.tenant.TenantContextHolder;
import com.project.modal.Tenant;
import com.project.modal.User;
import com.project.pagination.PageMapper;
import com.project.pagination.PagedResponse;
import com.project.repository.TenantRepository;
import com.project.repository.UserRepository;
import com.project.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService
{
	
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public TenantRepository tenantRepository;
	
	public UserServiceImpl( UserRepository userRepository, TenantRepository tenantRepository )
	{
		this.userRepository = userRepository;
		this.tenantRepository = tenantRepository;
	}
	
	@Override
	public UserResponse createUser( UserRequest request )
	{
		
		UUID tenantId = TenantContextHolder.getTenantId();
		
		if ( tenantId == null )
		{
			throw new IllegalArgumentException( "Tenant ID must not be null" );
		}
		
		Tenant tenant = tenantRepository.findById( tenantId )
				.orElseThrow( () -> new TenantNotFoundException( "Tenant not found" ) );
		
		String emailLower = request.getEmail().toLowerCase().trim();
		
		User user = new User();
		user.setTenant( tenant );
		user.setEmail( emailLower );
		user.setName( request.getName() );
		user.setStatus( true );
		
		boolean isEmailExist = userRepository.existsByEmailAndTenant_IdAndStatus( user.getEmail(), tenantId, true );
		log.error( "isEmailExist" + isEmailExist );
		
		log.error( "Email : " + user.getEmail() );
		
		if ( isEmailExist )
		{
			throw new DuplicateUserException( "Email already exists for this tenant" );
		}
		
		User savedUser = userRepository.save( user );
		return mapToResponse( savedUser );
	}
	
	@Override
	public PagedResponse<UserResponse> findAllUsers( Pageable pageable )
	{
		UUID tenantId = TenantContextHolder.getTenantId();
		
		if ( tenantId == null )
		{
			throw new IllegalArgumentException( "Tenant ID must not be null" );
		}
		
		Page<UserResponse> userResponse = userRepository.findByTenant_IdAndStatus( tenantId, pageable, true ).map( this::mapToResponse );
		
		return PageMapper.map( userResponse );
	}
	
	@Override
	public UserResponse findUserById( UUID userId )
	{
		UUID tenantId = TenantContextHolder.getTenantId();
		
		if ( tenantId == null || userId == null )
		{
			throw new IllegalArgumentException( "Tenant ID or User Id must not be null" );
		}
		
		User user = userRepository.findByIdAndTenant_IdAndStatus( userId, tenantId, true )
				.orElseThrow( () -> new UserNotFoundException( "User Not Found for the userId: " + userId ) );
		
		return mapToResponse( user );
	}
	
	private UserResponse mapToResponse( User user )
	{
		return UserResponse.builder().tenantId( user.getTenant().getId() ).email( user.getEmail() ).name( user.getName() )
				.status( user.isStatus() ? StatusType.ACTIVE : StatusType.INACTIVE ).createdAt( user.getCreatedAt() )
				.build();
	}
	
	@Override
	@Transactional
	public UserResponse deleteUser( UUID userId )
	{
		UUID tenantId = TenantContextHolder.getTenantId();
		
		if ( tenantId == null || userId == null )
		{
			throw new IllegalArgumentException( "Tenant ID or User Id must not be null" );
		}
		
		User user = userRepository.findByIdAndTenant_IdAndStatus( userId, tenantId, true )
				.orElseThrow( () -> new UserNotFoundException( "User Not Found" ) );
		
		user.setStatus( false );
		user.setDeleteAt( Instant.now() );
		userRepository.save( user );
		
		return mapToResponse( user );
	}
	
	@Override
	public boolean existsByEmail( String email )
	{
		UUID tenantId = TenantContextHolder.getTenantId();
		
		if ( tenantId != null )
		{
			return userRepository.existsByEmailAndTenant_IdAndStatus( email, tenantId, true );
		}
		
		return false;
	}
	
	@Override
	public UserResponse updateUserById( UUID userId, UserRequest request )
	{
		UUID tenantId = TenantContextHolder.getTenantId();
		
		if ( tenantId == null || userId == null )
		{
			throw new IllegalArgumentException( "Tenant ID or User Id must not be null" );
		}
		
		User user = userRepository.findByIdAndTenant_IdAndStatus( userId, tenantId, true )
				.orElseThrow( () -> new UserNotFoundException( "User Not Found for the userId: " + userId ) );
		
		// Updating user object
		user.setName( request.getName() );
		userRepository.save( user );
		
		return mapToResponse( user );
	}
	
}
