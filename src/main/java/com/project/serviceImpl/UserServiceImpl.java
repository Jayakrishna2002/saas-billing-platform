
package com.project.serviceImpl;

import java.time.Instant;
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
import com.project.exception.conflict.DuplicateUserException;
import com.project.exception.notFound.TenantNotFoundException;
import com.project.exception.notFound.UserNotFoundException;
import com.project.modal.Tenant;
import com.project.modal.User;
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
	public UserResponse createUser( UUID tenantId, CreateUserRequest request )
	{
		
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
		
		boolean isEmailExist = userRepository.existsByEmailAndTenant_Id( user.getEmail(), tenantId );
		log.error( "isEmailExist" + isEmailExist );
		
		log.error( "Email : " + user.getEmail() );
		
		if ( isEmailExist )
		{
			new DuplicateUserException( "Email already exists for this tenant" );
		}
		
		User savedUser = userRepository.save( user );
		return mapToResponse( savedUser );
	}
	
	@Override
	public Page<UserResponse> findAllUsersByTenantId( UUID tenantId, Pageable pageable )
	{
		
		if ( tenantId == null )
		{
			throw new IllegalArgumentException( "Tenant ID must not be null" );
		}
		
		return userRepository.findByTenant_IdAndStatus( tenantId, pageable, true ).map( this::mapToResponse );
	}
	
	@Override
	public Optional<UserResponse> findUserByIdAndTenantId( UUID userId, UUID tenantId )
	{
		
		if ( tenantId == null || userId == null )
		{
			throw new IllegalArgumentException( "Tenant ID or User Id must not be null" );
		}
		
		return userRepository.findByIdAndTenant_IdAndStatus( userId, tenantId, true ).map( this::mapToResponse );
	}
	
	private UserResponse mapToResponse( User user )
	{
		return new UserResponse(
				user.getTenant().getId(), user.getEmail(), user.getName(), user.isStatus(), user.getCreatedAt()
		);
	}
	
	@Override
	@Transactional
	public UserResponse deleteUser( UUID userId, UUID tenantId )
	{
		
		if ( tenantId == null || userId == null )
		{
			throw new IllegalArgumentException( "Tenant ID or User Id must not be null" );
		}
		
		User user = userRepository.findByIdAndTenant_IdAndStatus( userId, tenantId, true )
				.orElseThrow( () -> new UserNotFoundException( "User Not Found" ) );
		
		user.setStatus( false );
		user.setDeleteAt( Instant.now() );
		userRepository.save( user );
		
		UserResponse userRes = new UserResponse(
				user.getTenant().getId(), user.getEmail(), user.getName(), user.isStatus(), user.getCreatedAt()
		);
		
		return userRes;
	}
	
	@Override
	public boolean existsByEmailAndTenantId( String email, UUID tenantId )
	{
		
		if ( tenantId != null )
		{
			return userRepository.existsByEmailAndTenant_Id( email, tenantId );
		}
		
		return false;
	}
	
}
