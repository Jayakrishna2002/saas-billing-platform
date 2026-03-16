
package com.project.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.modal.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant , UUID>
{
	
	public Optional<Tenant> findByIdAndStatus( UUID tenantId, boolean status );
	
	public Page<Tenant> findByStatus( Pageable pageable, boolean status );
	
	public boolean existsByNameIgnoreCase( String name );
	
}
