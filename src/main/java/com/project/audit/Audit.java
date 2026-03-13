
package com.project.audit;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners( AuditingEntityListener.class )
public class Audit
{
	
	@CreationTimestamp
	@Column( name = "created_at", updatable = false )
	private Instant createdAt;
	
	@UpdateTimestamp
	@Column( name = "updated_at" )
	private Instant updateAt;
	
	@Version
	@Column( name = "version" )
	private Long version;
	
}
