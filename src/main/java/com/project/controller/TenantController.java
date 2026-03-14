
package com.project.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.TenantRequest;
import com.project.dto.TenantResponse;
import com.project.service.TenantService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping( "api/v1/tenant" )
@AllArgsConstructor
@Slf4j
public class TenantController
{
	
	private TenantService tenantService;
	
	@PostMapping
	public ResponseEntity<TenantResponse> createTenant( @Valid @RequestBody TenantRequest request )
	{
		return ResponseEntity.status( HttpStatus.CREATED ).body( tenantService.createTenant( request ) );
	}
	
	@GetMapping
	public ResponseEntity<Page<TenantResponse>> getAll(
			@PageableDefault( size = 10, sort = "name", direction = Direction.ASC ) Pageable pageable
	)
	{
		return ResponseEntity.status( HttpStatus.FOUND ).body( tenantService.findAll( pageable ) );
	}
	
	@GetMapping( "/{tenantId}" )
	public ResponseEntity<TenantResponse> getTenant( @PathVariable UUID tenantId )
	{
		return ResponseEntity.status( HttpStatus.FOUND ).body( tenantService.findById( tenantId ) );
	}
	
	@PutMapping( "/{tenantId}" )
	public ResponseEntity<TenantResponse> updateTenant(
			@PathVariable UUID tenantId, @Valid @RequestBody TenantRequest request
	)
	{
		return ResponseEntity.status( HttpStatus.OK ).body( tenantService.updateTenant( tenantId, request ) );
	}
	
	@DeleteMapping( "/{tenantId}" )
	public ResponseEntity<TenantResponse> deleteTenant( @PathVariable UUID tenantId )
	{
		tenantService.deleteTenant( tenantId );
		
		return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
	}
	
}
