
package com.project.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse
{
	
	private UUID tenantId;
	private String email;
	private String name;
	private boolean status;
	private Instant createdAt;
	
}
