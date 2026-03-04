package com.project.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	
	public Page<User> findByTenant_Id( UUID tenantId, Pageable pageable );
	
	public Optional<User> findByIdAndTenant_Id( UUID userId, UUID tenantId );
	
	public void deleteByIdAndTenant_Id( UUID userId, UUID tenantId );
	
	public boolean existsByEmailAndTenant_Id( String email, UUID tenantId );
}
