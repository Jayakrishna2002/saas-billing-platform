
package com.project.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User , UUID>
{
	
	@Query( """
			SELECT u
				FROM User u
			JOIN TenantMembership tm ON tm.user.id = u.id
			WHERE tm.tenant.id = :tenantId
			  AND u.active = true
			  AND tm.status = true
			""" )
	public Page<User> findUsersByTenant( UUID tenantId, Pageable pageable );
	
	
	@Query("""
			SELECT u
				FROM User u
			JOIN TenantMembership tm ON tm.user.id = :userId
			WHERE tm.tenant.id = :tenantId
			  AND u.active = true
			  AND tm.status = true
			""")
	public Optional<User> findUserById( UUID userId, UUID tenantId );
	
	@Query( """
			SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
				FROM User u
			JOIN TenantMembership tm ON tm.user.id = u.id
			WHERE u.email = :email
			  AND tm.tenant.id = :tenantId
			  AND u.active = true
			  AND tm.status = true
			""" )
	public boolean existsByTenantIdAndUserId( String email, UUID tenantId );
	
}
