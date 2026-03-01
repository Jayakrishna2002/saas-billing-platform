package com.project.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.project.modal.Users;

public interface UserService {
	
	public ArrayList<Users> findAllUsersByTenantId( UUID tenantId );
	
	public Optional<Users> findUserByIdAndTenantId( UUID userId, UUID tenantId );
	
	public void deleteByUserIdAndTenantId( UUID userId, UUID tenantId );
}
