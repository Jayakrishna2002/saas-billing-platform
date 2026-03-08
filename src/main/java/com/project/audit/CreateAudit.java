package com.project.audit;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CreateAudit {
	
	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private Instant createdAt;
	
	@Version
	@Column(name = "version")
	private Long version;
}
