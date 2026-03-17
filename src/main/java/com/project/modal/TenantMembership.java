
package com.project.modal;

import java.util.UUID;

import com.project.audit.Audit;
import com.project.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "tenant_membership", uniqueConstraints =
{ @UniqueConstraint( columnNames =
{ "tenant_id", "user_id" } ) } )
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class TenantMembership extends Audit
{
	
	@Id
	@GeneratedValue( strategy = GenerationType.UUID )
	@Column( updatable = false, nullable = false )
	private UUID id;
	
	@ManyToOne
	private Tenant tenant;
	
	@ManyToOne
	private User user;
	
	@Enumerated( EnumType.STRING )
	private Role role;
	
	private boolean status = true;
	
}
