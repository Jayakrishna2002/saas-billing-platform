package com.project.service;

import java.util.UUID;

public interface CachedTenantResolverService
{
	public boolean isTenantActive( UUID tenantId ); 
}
