
package com.project.serviceImpl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.project.repository.TenantRepository;
import com.project.service.CachedTenantResolverService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CachedTenantResolverServiceImpl implements CachedTenantResolverService
{
	
	private static final String CACHE_PREFIX = "tenant:status:";
	private final RedisTemplate<String , Object> redisTemplate;
	private final TenantRepository tenantRepository;
	
	public CachedTenantResolverServiceImpl(
			RedisTemplate<String , Object> redisTemplate, TenantRepository tenantRepository
	)
	{
		this.redisTemplate = redisTemplate;
		this.tenantRepository = tenantRepository;
	}
	
	@Override
	public boolean isTenantActive( UUID tenantId )
	{
		
		String cahceKey = CACHE_PREFIX + tenantId;
		
		try
		{
			// 1. Hit the memory layer first (Redis Lookup)
			Boolean cachedStatus = (Boolean) redisTemplate.opsForValue().get( cahceKey );
			
			if ( cachedStatus != null )
			{
				return cachedStatus;
			}
			
			// 2. Cache Miss -> Fallback to PostgreSQL Datastore
			boolean isActive = tenantRepository.findById( tenantId ).map( tenant -> tenant.isStatus() ).orElse( false );
			
			// 3. Hydrate cache asynchronously/inline to prevent subsequent DB hits
			redisTemplate.opsForValue().set( cahceKey, isActive, 1, TimeUnit.HOURS );
			
			return isActive;
		}
		catch ( Exception e )
		{
			// Production Resilience Rule: Cache-Failure Fallback
			// If the Redis cluster goes offline or encounters a socket timeout,
			// fallback directly to the primary database to prevent dropping live traffic.
			return tenantRepository.findById( tenantId ).map( tenant -> tenant.isStatus() ).orElse( false );
		}
		
	}
	
}
