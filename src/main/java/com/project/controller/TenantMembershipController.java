
package com.project.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.TenantMembershipRequest;
import com.project.dto.TenantMembershipResponse;
import com.project.service.TenantMembershipService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping( "api/v1/memberships" )
@AllArgsConstructor
@Slf4j
public class TenantMembershipController
{
	
	private TenantMembershipService membershipService;
	
	@PutMapping( "/{membershipId}/role" )
	public ResponseEntity<TenantMembershipResponse> updateMembership(
			@RequestHeader( "X-Tenant-ID" ) UUID tenantId, @PathVariable UUID membershipId,
			@Valid @RequestBody TenantMembershipRequest request
	)
	{
		
		TenantMembershipResponse membershipResponse = membershipService.updateMembershipRole( tenantId, membershipId, request );
		return ResponseEntity.status( HttpStatus.OK ).body( membershipResponse );
		
	}
	
	@DeleteMapping( "/{membershipId}" )
	public ResponseEntity<?> deleteMembership(
			@RequestHeader( "X-Tenant-ID" ) UUID tenantId, @PathVariable UUID membershipId
	)
	{
		membershipService.deleteMembership( tenantId, membershipId );
		return ResponseEntity.noContent().build();
	}
	
}
