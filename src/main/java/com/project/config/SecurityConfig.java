package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.filter.TenantFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	
	private TenantFilter tenantFilter;
	
	public SecurityConfig(TenantFilter tenantFilter)
	{
		this.tenantFilter = tenantFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception
	{
		
		http
				// 1. Disable CSRF(Cross-Site Request Forgery) as our application will transition to a fully stateless JWT model
				.csrf( AbstractHttpConfigurer::disable )
				
				.formLogin( AbstractHttpConfigurer :: disable )
				
				.httpBasic( AbstractHttpConfigurer :: disable )
				
				// 2. Enforce a completely stateless execution footprint (No HTTP Sessions allowed)
				.sessionManagement( session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
				
				// 3. Define the deterministic endpoint whitelist
				.authorizeHttpRequests(
						
						// Public Onboarding and Documentation endpoints explicitly allowed open access
						auth -> auth.requestMatchers( "/api/v1/tenant/**" ).permitAll()
								.requestMatchers( "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html" ).permitAll()
								.anyRequest().permitAll()
				)
				
				// 4. Injecting custom TenantFilter into the pipeline
				.addFilterBefore( tenantFilter, UsernamePasswordAuthenticationFilter.class );
		return http.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder( 12 );
		
	}
	
}
