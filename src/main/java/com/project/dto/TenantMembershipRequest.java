package com.project.dto;

import com.project.enums.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantMembershipRequest
{
	@NotBlank
	private Role role;
}
