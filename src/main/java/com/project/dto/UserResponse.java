package com.project.dto;

import java.time.Instant;
import java.util.UUID;

public class UserResponse {
	
	private UUID tenantId;
	private String email;
	private String name;
	private String status;
	private Instant createdAt;
	
	public UserResponse() {}
	
	public UserResponse(UUID tenantId, String email, String name, String status, Instant createdAt) {
		super();
		this.tenantId = tenantId;
		this.email = email;
		this.name = name;
		this.status = status;
		this.createdAt = createdAt;
	}
		
	public UUID getTenantId() {
		return tenantId;
	}

	public void setTenantId(UUID tenantId) {
		this.tenantId = tenantId;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
