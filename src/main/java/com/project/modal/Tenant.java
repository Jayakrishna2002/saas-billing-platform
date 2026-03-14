
package com.project.modal;

import java.time.Instant;
import java.util.UUID;

import com.project.audit.Audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "tenants" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class Tenant extends Audit
{
	
	@Id
	@GeneratedValue( strategy = GenerationType.UUID )
	@Column( name = "id", updatable = false, nullable = false )
	private UUID id;
	
	@Column( name = "name", nullable = false )
	private String name;
	
	@Column( name = "status" )
	private boolean status = true;
	
	@Column( name = "delete_at" )
	private Instant deleteAt;
	
}
