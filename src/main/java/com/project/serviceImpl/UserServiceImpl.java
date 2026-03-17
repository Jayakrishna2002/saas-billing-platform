
package com.project.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dto.UserRequest;
import com.project.dto.UserResponse;
import com.project.enums.Role;
import com.project.enums.StatusType;
import com.project.exception.conflict.DuplicateUserException;
import com.project.exception.notFound.TenantNotFoundException;
import com.project.exception.notFound.UserNotFoundException;
import com.project.infrastructure.tenant.TenantContextHolder;
import com.project.modal.Tenant;
import com.project.modal.TenantMembership;
import com.project.modal.User;
import com.project.pagination.PageMapper;
import com.project.pagination.PagedResponse;
import com.project.repository.TenantMembershipRepository;
import com.project.repository.TenantRepository;
import com.project.repository.UserRepository;
import com.project.service.TenantMembershipService;
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
	@Autowired
	public TenantMembershipRepository membershipRepository;
	
	public UserServiceImpl(	UserRepository userRepository, TenantRepository tenantRepository,	TenantMembershipRepository membershipRepository	)
	{
		this.userRepository = userRepository;
		this.tenantRepository = tenantRepository;
		this.membershipRepository = membershipRepository;
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
		user.setEmail( emailLower );
		user.setName( request.getName() );
		user.setActive( true );
		
		boolean isEmailExist = userRepository.existsByTenantIdAndUserId( user.getEmail(), tenantId );
		log.error( "isEmailExist" + isEmailExist );
		
		log.error( "Email : " + user.getEmail() );
		
		if ( isEmailExist )
		{
			throw new DuplicateUserException( "Email already exists for this tenant" );
		}
		
		User savedUser = userRepository.save( user );
		
		TenantMembership membership = new TenantMembership();
		membership.setTenant( tenant );
		membership.setUser( savedUser );
		membership.setRole( Role.ADMIN );
		membershipRepository.save( membership );
		
		return UserResponse.builder().id( user.getId() ).email( user.getEmail() ).name( user.getName() ).role( membership.getRole() )
				.status( user.isActive() ? StatusType.ACTIVE : StatusType.INACTIVE ).createdAt( user.getCreatedAt() )
				.build();
	}
	
	@Override
	public PagedResponse<UserResponse> findAllUsers( Pageable pageable )
	{
		UUID tenantId = TenantContextHolder.getTenantId();
		
		if ( tenantId == null )
		{
			throw new IllegalArgumentException( "Tenant ID must not be null" );
		}
		
		Page<UserResponse> userResponse = userRepository.findUsersByTenant( tenantId, pageable )
				.map( this::mapToResponse );
		
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
		
		User user = userRepository.findUserById( userId, tenantId )
				.orElseThrow( () -> new UserNotFoundException( "User Not Found for the userId: " + userId ) );
		
		return mapToResponse( user );
	}
	
	private UserResponse mapToResponse( User user )
	{
		return UserResponse.builder().id( user.getId() ).email( user.getEmail() ).name( user.getName() )
				.status( user.isActive() ? StatusType.ACTIVE : StatusType.INACTIVE ).createdAt( user.getCreatedAt() )
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
		
		User user = userRepository.findUserById( userId, tenantId )
				.orElseThrow( () -> new UserNotFoundException( "User Not Found" ) );
		
		user.setActive( false );
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
			return userRepository.existsByTenantIdAndUserId( email, tenantId );
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
		
		User user = userRepository.findUserById( userId, tenantId )
				.orElseThrow( () -> new UserNotFoundException( "User Not Found for the userId: " + userId ) );
		
		if ( !user.isActive() )
		{
			throw new IllegalStateException( "Cannot update an inactive user. Please reactivate the user first." );
			
		}
		
		// Updating user object
		user.setName( request.getName() );
		userRepository.save( user );
		
		return mapToResponse( user );
	}
	
}
