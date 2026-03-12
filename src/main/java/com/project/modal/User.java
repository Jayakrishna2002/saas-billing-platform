
package com.project.modal;

import java.time.Instant;
import java.util.UUID;

import com.project.audit.CreateAudit;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "users", uniqueConstraints =
{ @UniqueConstraint( columnNames =
{ "tenant_id", "email" } ) } )
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class User extends CreateAudit
{
	
	@Id
	@GeneratedValue( strategy = GenerationType.UUID )
	@Column( name = "id", updatable = false, nullable = false )
	private UUID id;
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "tenant_id", nullable = false )
	private Tenant tenant;
	
	@Column( name = "email", nullable = false )
	private String email;
	
	@Column( name = "name" )
	private String name;
	
	@Column( name = "active" )
	private boolean status = true;
	
	@Column( name = "deleted_at", updatable = false )
	private Instant deleteAt;
	
	public User( UUID id, Tenant tenant, String email, String name, boolean status, Instant deleteAt )
	{
		super();
		this.id = id;
		this.tenant = tenant;
		this.email = email;
		this.name = name;
		this.status = status;
		this.deleteAt = deleteAt;
	}
	
}
