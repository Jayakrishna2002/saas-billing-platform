package com.project.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.modal.Tenants;

public interface TenantRepository extends JpaRepository<Tenants, UUID> {
}
