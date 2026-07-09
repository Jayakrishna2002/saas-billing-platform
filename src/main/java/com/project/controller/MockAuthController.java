
package com.project.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.security.JwtTokenProvider;

@Controller
@RequestMapping( "/api/v1/auth" )
public class MockAuthController
{
	
	private final JwtTokenProvider tokenProvider;
	
	public MockAuthController( JwtTokenProvider tokenProvider )
	{
		this.tokenProvider = tokenProvider;
	}
	
	@PostMapping( "/login/mock" )
	private ResponseEntity<Map<String , String>> mockLogin( @RequestParam Map<String , String> payload )
	{
		String email = payload.getOrDefault( "email", "dev@saas.com" );
		
		// Generate an arbitrary valid mock workspace context
		UUID mockUserId = UUID.randomUUID();
		UUID mockTenantId = UUID.fromString( "00000000-0000-0000-0000-000000000000" ); // Standard base UUID format
		
		String token = tokenProvider.generateToken( mockUserId, mockTenantId, "ADMIN", email );
		
		return ResponseEntity.ok( Map.of( "token", token, "type", "Bearer" ) );
		
	}
	
}
