package com.lorin.jedis;

import redis.clients.jedis.Jedis;

/**
 * Created by baidu on 16/7/7.
 */
public class RedisClient {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.94.21.52", 6379);
        System.out.print(jedis.get("username"));
    }
}
