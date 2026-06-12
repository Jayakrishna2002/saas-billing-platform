
package com.project.serviceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dto.TenantMembershipRequest;
import com.project.dto.TenantMembershipResponse;
import com.project.enums.Role;
import com.project.exception.conflict.LastAdminDeletionException;
import com.project.exception.notFound.NotFoundException;
import com.project.exception.notFound.TenantMembershipNotFound;
import com.project.modal.TenantMembership;
import com.project.pagination.PageMapper;
import com.project.pagination.PagedResponse;
import com.project.repository.TenantMembershipRepository;
import com.project.service.TenantMembershipService;

import jakarta.validation.Valid;
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
	
	@Override
	public PagedResponse<TenantMembershipResponse> findUsersByRoleAndTenantId(
			UUID tenantId, Role role, Pageable pageable
	)
	{
		
		if ( tenantId == null )
		{
			throw new IllegalArgumentException( "Tenant ID must not be null" );
		}
		
		Page<TenantMembershipResponse> membership = tenantMembershipRepository
				.findByTenant_IDAndRoleAndStatusTrue( tenantId, role, pageable ).map( this::mapToResponse );
		
		return PageMapper.map( membership );
	}
	
	private TenantMembershipResponse mapToResponse( TenantMembership membership )
	{
		return TenantMembershipResponse.builder().userId( membership.getUser().getId() )
				.name( membership.getUser().getName() ).tenantId( membership.getTenant().getId() )
				.role( membership.getRole() ).build();
	}
	
	@Override
	public TenantMembershipResponse updateMembershipRole( UUID membershipId, @Valid TenantMembershipRequest request )
	{
		TenantMembership tenantMembership = tenantMembershipRepository.findById( membershipId )
				.orElseThrow( () -> new TenantMembershipNotFound( "Membership Not Found for the ID: " + membershipId ) );
		
		if ( tenantMembership.getRole() == Role.ADMIN && request.getRole().equals( Role.MEMBER ) )
		{
			boolean moreThanOneAdmin = tenantMembershipRepository
					.existsMoreThanOneAdminByTenantId( tenantMembership.getTenant().getId(), Role.ADMIN );
			
			if ( !moreThanOneAdmin )
			{
				throw new LastAdminDeletionException( "Cannot remove the last admin" );
			}
			
		}
		
		tenantMembership.setRole( request.getRole() );
		tenantMembershipRepository.save( tenantMembership );
		
		return mapToResponse( tenantMembership );
	}
	
	@Override
	public void deleteMembership( UUID membershipId )
	{
		TenantMembership membership = tenantMembershipRepository.findById( membershipId )
				.orElseThrow( () -> new TenantMembershipNotFound( "Membership Not Found for the ID: " + membershipId ) );
		
		if ( membership.getRole().equals( Role.ADMIN ) )
		{
			boolean moreThanOneAdmin = tenantMembershipRepository
					.existsMoreThanOneAdminByTenantId( membership.getTenant().getId(), Role.ADMIN );
			
			if ( !moreThanOneAdmin )
			{
				throw new LastAdminDeletionException( "Cannot remove the last admin" );
			}
			
		}
		
		membership.setStatus( false );
		membership.setDeleteAt( Instant.now() );
		tenantMembershipRepository.save( membership );
		
	}
	
}
