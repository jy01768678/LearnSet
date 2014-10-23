package com.lorin.jedis;

import redis.clients.jedis.ShardedJedisPipeline;

public class NotifyShardedJedisPipeline extends ShardedJedisPipeline{

	private NotifyShardedJedis notifyShardedJedis;

	public NotifyShardedJedis getNotifyShardedJedis() {
		return notifyShardedJedis;
	}

	public void setNotifyShardedJedis(NotifyShardedJedis notifyShardedJedis) {
		this.notifyShardedJedis = notifyShardedJedis;
	}
}
