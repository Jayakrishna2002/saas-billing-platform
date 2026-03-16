
package com.project.filter;

import java.io.IOException;
import java.util.UUID;

import org.apache.tomcat.util.file.Matcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.infrastructure.tenant.TenantContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TenantFilter extends OncePerRequestFilter
{
	
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
		
		String tenantId = request.getHeader( "X-Tenant-ID" );
		
		if ( tenantId == null )
		{
			response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Missing X-Tenant-ID header" );
			return;
		}
		
		try
		{
			UUID id = UUID.fromString( tenantId );
			TenantContextHolder.setTenantId( id );
			
			filterChain.doFilter( request, response );
			
		}
		catch ( IllegalArgumentException e )
		{
			response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Invalid Tenant ID format" );
		}
		finally
		{
			TenantContextHolder.clear();
		}
		
	}	
}
