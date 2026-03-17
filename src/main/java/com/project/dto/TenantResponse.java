
package com.project.dto;

import java.time.Instant;
import java.util.UUID;

import com.project.enums.StatusType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse
{
	
	private UUID id;
	private String name;
	private StatusType status;
	private Instant createdAt;
	
}
