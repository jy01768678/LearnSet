package com.lorin.jedis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;

public class NotifyShardedJedis extends ShardedJedis {

	public NotifyShardedJedis(List<JedisShardInfo> shards) {
		super(shards);
	}
	
	public NotifyShardedJedis(List<JedisShardInfo> shards, Hashing algo,
			Pattern keyTagPattern) {
		super(shards, algo, keyTagPattern);
	}
	
	/**
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern){
		Collection<Jedis> jedisList = (Collection<Jedis>) getAllShards(); 
		Set<String> keySet = new HashSet<String>();
		String temp = "";
		Set<String> part = null;
		Iterator<String> its= null;
		for(Jedis jedis : jedisList){
			part = jedis.keys(pattern);
			its = part.iterator();
			while(its.hasNext()){
				temp = its.next();
				if(!keySet.contains(temp)){
					keySet.add(temp);
				}
			}
		}
		return keySet;
	}
	
    public NotifyShardedJedisPipeline pipelined() {
    	NotifyShardedJedisPipeline pipeline = new NotifyShardedJedisPipeline();
		pipeline.setShardedJedis(this);
		return pipeline;
    }
}
