
package com.project.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.exception.notFound.UserNotFoundException;
import com.project.modal.TenantMembership;
import com.project.modal.User;
import com.project.repository.TenantMembershipRepository;
import com.project.repository.UserRepository;
import com.project.security.JwtTokenProvider;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping( "/api/v1/auth" )
@AllArgsConstructor
public class MockAuthController
{
	
	private final JwtTokenProvider tokenProvider;
	private final TenantMembershipRepository tenantMembershipRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@PostMapping( "/login/mock" )
	private ResponseEntity<Map<String , String>> mockLogin( @RequestBody Map<String , String> payload )
	{
		String email = payload.get( "email" );
		String rawPassword = payload.get( "password" );
		
		// 1. Locate the user by normalized email
		User user = userRepository.findByEmail( email.toString().trim() )
				.orElseThrow( () -> new UserNotFoundException( " Invalid email or password " ) );
		
		// 2. Cryptographically verify the raw password against the stored database hash
		if ( !passwordEncoder.matches( rawPassword, user.getPassword() ) )
		{
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( Map.of( "error", "Invalid email or password" ) );
		}
		
		// 3. Extract the active tenant membership role context for this user
		TenantMembership tenantMembership = tenantMembershipRepository.findByUserId( user.getId() ).stream().findFirst()
				.orElseThrow( () -> new RuntimeException( "User has no active tenant workspaces" ) );
		
		// 4. Mint the cryptographically secured multi-tenant access token
		String token = tokenProvider.generateToken(
				user.getId(), tenantMembership.getTenant().getId(), tenantMembership.getRole().toString(), user.getEmail()
		);
		
		return ResponseEntity.ok( Map.of( "token", token, "type", "Bearer" ) );
		
	}
	
}
