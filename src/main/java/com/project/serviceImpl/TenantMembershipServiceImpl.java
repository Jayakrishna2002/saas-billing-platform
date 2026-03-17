
package com.project.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.exception.notFound.NotFoundException;
import com.project.modal.TenantMembership;
import com.project.repository.TenantMembershipRepository;
import com.project.service.TenantMembershipService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TenantMembershipServiceImpl implements TenantMembershipService
{
	private TenantMembershipRepository tenantMembershipRepository;

	@Override
	public TenantMembership findByTenantIdAndUserId( UUID tenantId, UUID userId )
	{
		if ( tenantId == null || userId == null )
		{
			throw new IllegalArgumentException( "Tenant ID or User Id must not be null" );
		}
		return tenantMembershipRepository.findByTenantIdAndUserId( tenantId, userId )
				.orElseThrow( () -> ( new NotFoundException( "Membership Not Found" ) ) );
	}

	@Override
	public List<TenantMembership> findByTenantId( UUID tenantId )
	{
		if ( tenantId == null )
		{
			throw new IllegalArgumentException( "Tenant ID must not be null" );
		}
		
		return tenantMembershipRepository.findByTenantId( tenantId );
	}
}
