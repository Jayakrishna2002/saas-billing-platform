package com.project.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.project.modal.Tenant;

public interface TenantService {
	
	public ArrayList<Tenant> findAll();
	
	public Optional<Tenant> findById( UUID tenantId );
	
}
