
package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig
{
	
	@Bean
	public RedisTemplate<String , Object> redisTemplate( RedisConnectionFactory connectionFactory )
	{
		
		RedisTemplate<String , Object> template = new RedisTemplate<String , Object>();
		template.setConnectionFactory( connectionFactory );
		
		// 1. Leverage the safe, non-deprecated builder mapping provided by Spring Data Redis
		// This automatically wires Jackson 3 default typing and Java 8 date/time serialization safely.
		GenericJacksonJsonRedisSerializer jsonSerializer = GenericJacksonJsonRedisSerializer.builder()
				.enableUnsafeDefaultTyping() // Safely configures type hints for Object polymorphic structures
				.build();
		
		// 2. Map standard high-performance String key boundaries
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		
		template.setKeySerializer( stringSerializer );
		template.setHashKeySerializer( stringSerializer );
		template.setValueSerializer( jsonSerializer );
		template.setHashValueSerializer( jsonSerializer );
		
		template.afterPropertiesSet();
		
		return template;
	}
	
}
