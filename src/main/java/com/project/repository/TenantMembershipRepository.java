
package com.project.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.enums.Role;
import com.project.modal.TenantMembership;

@Repository
public interface TenantMembershipRepository extends JpaRepository<TenantMembership , UUID>
{
	
	public Optional<TenantMembership> findByTenantIdAndUserId( UUID tenantId, UUID userId );
	
	public List<TenantMembership> findByTenantId( UUID tenantId );
	
	public boolean existsByTenant_Id (UUID tenantId );
	
	public Page<TenantMembership> findByTenantIdAndRoleAndStatusTrue( UUID tenantId, Role role, Pageable pageable );

	@Query(""" 
			SELECT COUNT(*) > 1
				FROM TenantMembership tm
			WHERE tm.tenant.id = :id
			AND tm.role = :role
			AND tm.status = true
			""")
	public boolean existsMoreThanOneAdminByTenantId( UUID id, Role role );

	public Optional<TenantMembership> findByIdAndTenant_Id( UUID membershipId, UUID tenantId ); 
	
	public Optional<TenantMembership> findByUserId( UUID userId );
	
}
