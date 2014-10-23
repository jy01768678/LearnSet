package com.lorin.jedis.service;

import com.lorin.jedis.NotifyShardedJedis;
import com.lorin.jedis.NotifyShardedJedisPool;

public class RedisTemplate {

	protected NotifyShardedJedisPool readShardedJedisPool;

	protected NotifyShardedJedisPool writeShardedJedisPool;

	public <T> T readExecutor(RedisCallBack<T> callback) {
		NotifyShardedJedis jedis = getReadJedis();
		T result = null;
		try{
			result = callback.doInRedis(jedis);
		}catch(Exception e){
			readShardedJedisPool.returnBrokenResource(jedis);
		} finally{
			readShardedJedisPool.returnResource(jedis);
		}
		return result;
	}

	public <T> T writeExecutor(RedisCallBack<T> callback) {
		NotifyShardedJedis jedis = getWriteJedis();
		T result = null;
		try{
			result = callback.doInRedis(jedis);
		}catch(Exception e){
			writeShardedJedisPool.returnBrokenResource(jedis);
		} finally{
			writeShardedJedisPool.returnResource(jedis);
		}
		return result;
	}
	
	public NotifyShardedJedis getReadJedis(){
		return readShardedJedisPool.getResource();
	}

	public NotifyShardedJedis getWriteJedis(){
		return writeShardedJedisPool.getResource();
	}

	public void setReadShardedJedisPool(
			NotifyShardedJedisPool readShardedJedisPool) {
		this.readShardedJedisPool = readShardedJedisPool;
	}

	public void setWriteShardedJedisPool(
			NotifyShardedJedisPool writeShardedJedisPool) {
		this.writeShardedJedisPool = writeShardedJedisPool;
	}
	
}
