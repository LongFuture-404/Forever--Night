package com.example.demoui.redis.Impl;

import com.example.demoui.redis.RedisService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("redisService")
public final class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> valueOperations;

    @PostConstruct
    public void init() {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void set(String key, String value) {
        /**
         * 过期时长
         */
        long DURATION = 24 * 60 * 60 * 1000L;
        valueOperations.set(key, value, DURATION, TimeUnit.MILLISECONDS);
//           log.info("key={}, value is: {} into redis cache", key, value);
    }

    @Override
    public String get(String key) {
        String redisValue = valueOperations.get(key);
//           log.info("get from redis, value is: {}", redisValue);
        return redisValue;
    }

    @Override
    public boolean delete(String key) {
        boolean result = Boolean.TRUE.equals(redisTemplate.delete(key));
//           log.info("delete from redis, key is: {}", key);
        return result;
    }

    @Override
    public Long getExpireTime(String key) {
            return valueOperations.getOperations().getExpire(key);
        }
}
