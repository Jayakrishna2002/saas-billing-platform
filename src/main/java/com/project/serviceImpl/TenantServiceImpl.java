package com.project.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.modal.Tenants;
import com.project.repository.TenantRepository;
import com.project.service.TenantService;

@Service
public class TenantServiceImpl implements TenantService{
	
	@Autowired
	public TenantRepository tenantRepository;

	public void setTenantRepository(TenantRepository tenantRepository) {
		this.tenantRepository = tenantRepository;
	}

	@Override
	public ArrayList<Tenants> findAll() {
		return (ArrayList<Tenants>) tenantRepository.findAll();
	}

	@Override
	public Optional<Tenants> findById( UUID tenantId ) {
		return tenantRepository.findById(tenantId);
	}
	
}
