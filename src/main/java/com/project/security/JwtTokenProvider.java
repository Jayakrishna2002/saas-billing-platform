
package com.project.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider
{
	
	private final SecretKey key;
	private final long validityInMilliseconds;
	
	public JwtTokenProvider(
			@Value(
				"${app.security.jwt.secret:enterprise-secret-key-must-be-at-least-256-bits-long-for-hmac}"
			) String key, @Value( "${app.security.jwt.expiration:86400000}" ) long validityInMilliseconds
	)
	{
		this.key = Keys.hmacShaKeyFor( key.getBytes( StandardCharsets.UTF_8 ) );
		this.validityInMilliseconds = validityInMilliseconds;
	}
	
	public String generateToken( UUID userId, UUID tenantId, String role, String email )
	{
		Date now = new Date();
		Date validity = new Date( now.getTime() + validityInMilliseconds );
		
		return Jwts.builder().subject( email ).claim( "userId", userId.toString() )
				.claim( "tenantId", tenantId.toString() ).claim( "role", role ).issuedAt( now ).expiration( validity )
				.signWith( key ).compact();
	}
	
	public Claims extractAllClaims( String token )
	{
		return Jwts.parser().verifyWith( key ).build().parseSignedClaims( token ).getPayload();
	}
	
	public boolean validateToken( String token )
	{
		
		try
		{
			Date expration = extractAllClaims( token ).getExpiration();
			return !expration.before( new Date() );
		}
		catch ( Exception e )
		{
			return false;
		}
		
	}
	
}
