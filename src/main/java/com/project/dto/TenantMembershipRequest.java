package com.project.dto;

import com.project.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TenantMembershipRequest
{
	@NotNull
	private Role role;
}
