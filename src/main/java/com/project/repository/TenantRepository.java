package com.project.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.modal.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
}
