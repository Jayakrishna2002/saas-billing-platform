
package com.project.dto;

import java.time.Instant;
import java.util.UUID;

import com.project.enums.Role;
import com.project.enums.StatusType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse
{
	
	private UUID id;
	private String email;
	private String name;
	private Role role;
	private StatusType status;
	private Instant createdAt;
	
}
