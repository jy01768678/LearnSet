package com.lorin;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Created by lorin on 2018/4/2.
 */
public class SpringRedisTest {
    public static void main(String[] args) {
        RedisTemplate redisTemplate = new RedisTemplate();
        ValueOperations<String, String> stringOperations = redisTemplate
                .opsForValue();
        stringOperations.set("string1", "fiala");
        // String类型数据存储，设置过期时间为80秒，采用TimeUnit控制时间单位
        stringOperations.set("string2", "fiala", 80, TimeUnit.SECONDS);
    }
}
