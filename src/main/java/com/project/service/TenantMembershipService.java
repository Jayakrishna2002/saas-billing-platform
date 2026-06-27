
package com.project.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.project.dto.TenantMembershipRequest;
import com.project.dto.TenantMembershipResponse;
import com.project.enums.Role;
import com.project.modal.TenantMembership;
import com.project.pagination.PagedResponse;

import jakarta.validation.Valid;

public interface TenantMembershipService
{
	
	public TenantMembership findByTenantIdAndUserId( UUID tenantId, UUID userId );
	
	public List<TenantMembership> findByTenantId( UUID tenantId );
	
	public PagedResponse<TenantMembershipResponse> findUsersByRoleAndTenantId( UUID tenantId, Role role, Pageable pageable );

	public TenantMembershipResponse updateMembershipRole( UUID tenantId, UUID membershipId, @Valid TenantMembershipRequest request );

	public void deleteMembership( UUID tenantId, UUID membershipId );
	
}
