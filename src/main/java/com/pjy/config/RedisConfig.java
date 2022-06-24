package com.pjy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Autowired
    public RedisConfig(RedisTemplate redisTemplate) {
        // 设置hash与string键为spring类型,值为json
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        //7工厂创建redisTemplate对象之后在进行配置
        redisTemplate.afterPropertiesSet();
    }
}
