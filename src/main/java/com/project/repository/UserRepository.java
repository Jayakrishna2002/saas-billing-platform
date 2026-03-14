
package com.project.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User , UUID>
{
	
	public Page<User> findByTenant_IdAndStatus( UUID tenantId, Pageable pageable, boolean status );
	
	public Optional<User> findByIdAndTenant_IdAndStatus( UUID userId, UUID tenantId, boolean status );
	
	public boolean existsByEmailAndTenant_Id( String email, UUID tenantId, boolean status );
	
}
