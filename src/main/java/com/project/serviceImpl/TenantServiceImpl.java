
package com.project.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dto.StatusType;
import com.project.dto.TenantRequest;
import com.project.dto.TenantResponse;
import com.project.exception.notFound.TenantNotFoundException;
import com.project.modal.Tenant;
import com.project.pagination.PageMapper;
import com.project.pagination.PagedResponse;
import com.project.repository.TenantRepository;
import com.project.service.TenantService;

@Service
public class TenantServiceImpl implements TenantService
{
	
	@Autowired
	public TenantRepository tenantRepository;
	
	public void setTenantRepository( TenantRepository tenantRepository )
	{
		this.tenantRepository = tenantRepository;
	}
	
	@Override
	public PagedResponse<TenantResponse> findAll( Pageable pageable )
	{
		 Page<TenantResponse> tenantResponse = tenantRepository.findByStatus( pageable, true ).map( this::mapToResponse );
		 return PageMapper.map( tenantResponse );
	}
	
	@Override
	public TenantResponse findById( UUID tenantId )
	{
		Tenant tenant = tenantRepository.findByIdAndStatus( tenantId, true )
				.orElseThrow( () -> new TenantNotFoundException( "Tenant Not Found for the ID: " + tenantId ) );
		
		return mapToResponse( tenant );
	}
	
	@Override
	public TenantResponse createTenant( TenantRequest request )
	{
		Tenant tenant = new Tenant();
		tenant.setName( request.getName() );
		
		Tenant savedTenant = tenantRepository.save( tenant );
		return mapToResponse( savedTenant );
	}
	
	private TenantResponse mapToResponse( Tenant savedTenant )
	{
		return TenantResponse.builder().id( savedTenant.getId() ).name( savedTenant.getName() )
				.status( savedTenant.isStatus() ? StatusType.ACTIVE : StatusType.INACTIVE )
				.createdAt( savedTenant.getCreatedAt() ).build();
	}
	
	@Override
	public TenantResponse updateTenant( UUID tenantId, TenantRequest request )
	{
		Tenant tenant = tenantRepository.findById( tenantId )
				.orElseThrow( () -> new TenantNotFoundException( "Tenant Not Found for the ID: " + tenantId ) );
		
		tenant.setName( request.getName() );
		Tenant savedTenant = tenantRepository.save( tenant );
		return mapToResponse( savedTenant );
	}
	
	@Override
	public void deleteTenant( UUID tenantId )
	{
		Tenant tenant = tenantRepository.findById( tenantId )
				.orElseThrow( () -> new TenantNotFoundException( "Tenant Not Found for the ID: " + tenantId ) );
		tenant.setStatus( false );
		tenant.setDeleteAt( Instant.now() );
		
		tenantRepository.save( tenant );
		
	}
	
}
