
package com.project.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.dto.TenantRequest;
import com.project.dto.TenantResponse;

public interface TenantService
{
	
	public Page<TenantResponse> findAll( Pageable pageable);
	
	public TenantResponse findById( UUID tenantId );
	
	public TenantResponse createTenant( TenantRequest request );

	public TenantResponse updateTenant( UUID tenantId, TenantRequest request );

	public void deleteTenant( UUID tenantId );
	
}
