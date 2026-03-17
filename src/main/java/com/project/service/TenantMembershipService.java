
package com.project.service;

import java.util.List;
import java.util.UUID;

import com.project.modal.TenantMembership;

public interface TenantMembershipService
{
	
	public TenantMembership findByTenantIdAndUserId( UUID tenantId, UUID userId );
	
	public List<TenantMembership> findByTenantId( UUID tenantId );
	
}
