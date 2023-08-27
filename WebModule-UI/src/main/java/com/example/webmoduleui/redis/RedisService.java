package com.example.webmoduleui.redis;

public interface RedisService {
    void init();
    void set(String key, String value);
    String get(String key);
    boolean delete(String key);
    Long getExpireTime(String key);
}
