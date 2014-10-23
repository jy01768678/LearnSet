package com.lorin.jedis.service;

import com.lorin.jedis.NotifyShardedJedis;

public interface RedisCallBack<T> {
	public T doInRedis(NotifyShardedJedis jedis);
}
