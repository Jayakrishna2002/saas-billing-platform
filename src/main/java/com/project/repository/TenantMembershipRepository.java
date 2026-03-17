
package com.project.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.modal.TenantMembership;

@Repository
public interface TenantMembershipRepository extends JpaRepository<TenantMembership , UUID>
{
	
	public Optional<TenantMembership> findByTenantIdAndUserId( UUID tenantId, UUID userId );
	
	public List<TenantMembership> findByTenantId( UUID tenantId );
	
}
