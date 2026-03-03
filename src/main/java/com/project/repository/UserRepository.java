package com.project.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.modal.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID>{
	
	public List<Users> findByTenant_Id( UUID tenantId );
	
	public Optional<Users> findByIdAndTenant_Id( UUID userId, UUID tenantId );
	
	public void deleteByIdAndTenant_Id( UUID userId, UUID tenantId );
	
	public boolean existsByEmailAndTenant_Id( String email, UUID tenantId );
}
