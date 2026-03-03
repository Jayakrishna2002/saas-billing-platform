package com.project.modal;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.SoftDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "tenant_id", "email" }) })
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tenant_id", nullable = false)
	private Tenants tenant;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name ="name")
	private String name;

	@Column(name ="status")
	private String status;
	
	@Column(name = "created_at", updatable = false)
	private Instant createdAt;
	
	public Users() {}

	public Users(UUID id, Tenants tenant, String email, String name, String status, Instant createdAt) {
		super();
		this.id = id;
		this.tenant = tenant;
		this.email = email;
		this.name = name;
		this.status = status;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Tenants getTenant() {
		return tenant;
	}

	public void setTenant(Tenants tenant) {
		this.tenant = tenant;
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
