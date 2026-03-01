package com.project.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.project.modal.Tenants;

public interface TenantService {
	
	public ArrayList<Tenants> findAll();
	
	public Optional<Tenants> findById( UUID tenantId );
	
}
