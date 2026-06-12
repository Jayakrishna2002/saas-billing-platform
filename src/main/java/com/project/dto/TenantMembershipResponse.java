package com.project.dto;

import java.util.UUID;

import com.project.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class TenantMembershipResponse
{
	private UUID userId;
	private String name;
	private UUID tenantId;
	private Role role;
}
