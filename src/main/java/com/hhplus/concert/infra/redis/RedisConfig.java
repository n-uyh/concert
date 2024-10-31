package com.hhplus.concert.infra.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Integer> redisTemplate( LettuceConnectionFactory lettuceConnectionFactory ) {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory( lettuceConnectionFactory );

        template.setKeySerializer( RedisSerializer.string() );
        template.setHashKeySerializer( RedisSerializer.string() );

        template.setDefaultSerializer( RedisSerializer.json() );
        template.setValueSerializer( RedisSerializer.json() );
        template.setHashValueSerializer( RedisSerializer.json() );

        template.afterPropertiesSet();

        return template;
    }
}
