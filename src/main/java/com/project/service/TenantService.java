
package com.project.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.dto.TenantRequest;
import com.project.dto.TenantResponse;
import com.project.pagination.PagedResponse;

public interface TenantService
{
	
	public PagedResponse<TenantResponse> findAll( Pageable pageable);
	
	public TenantResponse findById( UUID tenantId );
	
	public TenantResponse createTenant( TenantRequest request );

	public TenantResponse updateTenant( UUID tenantId, TenantRequest request );

	public void deleteTenant( UUID tenantId );
	
}
