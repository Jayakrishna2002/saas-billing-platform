
package com.project.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.infrastructure.tenant.TenantContextHolder;
import com.project.security.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TenantFilter extends OncePerRequestFilter
{
	
	private final JwtTokenProvider tokenProvider;
	
	public TenantFilter( JwtTokenProvider tokenProvider )
	{
		this.tokenProvider = tokenProvider;
	}
	
	@Override
	protected boolean shouldNotFilter( HttpServletRequest request ) throws ServletException
	{
		String path = request.getRequestURI();
		AntPathMatcher matcher = new AntPathMatcher();
		
		// Skipping this URL
		return ( matcher.match( "/api/v1/tenant/**", path ) || matcher.match( "/swagger-ui/**", path )
				|| matcher.match( "/v3/api-docs/**", path ) || matcher.match( "/swagger-ui.html", path ) );
	}
	
	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
			throws ServletException, IOException
	{
		
		String authHeader = request.getHeader( "Authorization" );
		
		if ( authHeader != null && authHeader.startsWith( "Bearer " ) )
		{
			String token = authHeader.substring( 7 );
			
			if ( tokenProvider.validateToken( token ) )
			{
				Claims claims = tokenProvider.extractAllClaims( token );
				
				String tenantStr = claims.get( "tenantId", String.class );
				String roleStr = claims.get( "role", String.class );
				String email = claims.getSubject();
				
				try
				{
					if ( tenantStr != null )
					{
						// 1. Hydrate our custom system multi-tenant isolation context
						UUID tenantId = UUID.fromString( tenantStr );
						TenantContextHolder.setTenantId( tenantId );
						
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								email, null, Collections.singletonList( new SimpleGrantedAuthority( "ROLE_" + roleStr ) )
						);
						
						SecurityContextHolder.getContext().setAuthentication( authentication );
					}
					
				}
				catch ( IllegalArgumentException e )
				{
					response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Invalid Tenant ID format" );
				}
				
			}
			
		}
		
		try
		{
			filterChain.doFilter( request, response );
		}
		
		finally
		{
			// Always clean up ThreadLocal references post-execution to prevent container pool memory leaks
			TenantContextHolder.clear();
		}
		
	}
	
}
