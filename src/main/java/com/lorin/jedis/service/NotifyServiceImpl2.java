package com.lorin.jedis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;

import com.lorin.jedis.NotifyShardedJedis;
import com.lorin.jedis.NotifyShardedJedisPipeline;

public class NotifyServiceImpl2 extends RedisTemplate implements NotifyService {

	private final static String RESULT_OK = "OK";


	private static ExecutorService pool = Executors.newFixedThreadPool(100);
	
	private static final int TIME_OUT = 3;
	
	private final static String pattern = "%s_%s_*";
	
	@Override
	public boolean deleteNotify(String userId, String[] status,
			String[] notifyId) {
		final String _userId = userId;
		final String[] _status = status;
		final String[] _notifyId = notifyId;
		Future<Boolean> future = pool.submit(new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception {
				return (Boolean) writeExecutor(new RedisCallBack<Boolean>(){
					public Boolean doInRedis(NotifyShardedJedis jedis) {
						NotifyShardedJedisPipeline pipe = jedis.pipelined();
						String key = "";
						for(int i=0;i<_status.length;i++){
							key = generaterKey(_userId, _status[i], _notifyId[i]);
							pipe.del(key);
							pipe.srem(_userId, key);
						}
						List<Object> result = pipe.syncAndReturnAll();
						for(Object obj : result){
							System.out.println(obj.toString());
						}
						return true;
					}
				});
			}
		});
		try {
			boolean result = future.get(TIME_OUT, TimeUnit.SECONDS);
			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<NotifyDO> getUserNotifyList(String userId, String status) {
		final String _userId = userId;
		final String _status = status;
		Future<List<NotifyDO>> future = pool.submit(new Callable<List<NotifyDO>>(){
			@Override
			public List<NotifyDO> call() throws Exception {
				List<NotifyDO> object = (List<NotifyDO>)readExecutor(new RedisCallBack<List<NotifyDO>>(){
					@Override
					public List<NotifyDO> doInRedis(NotifyShardedJedis jedis) {
						Set<String> keySet = jedis.keys(String.format(pattern, _userId,_status));
						List<NotifyDO> list = new ArrayList<NotifyDO>();
						Iterator<String> its = keySet.iterator();
						NotifyDO notifyDO = null;
						while(its.hasNext()){
							Map<String,String> msgObject = jedis.hgetAll(its.next());
							notifyDO = new NotifyDO();
							notifyDO.setContent(msgObject.get("content"));
							notifyDO.setTitle(msgObject.get("title"));
							notifyDO.setNotify_level((String)msgObject.get("notify_level"));
							notifyDO.setNotify_type((String)msgObject.get("notify_type"));
							notifyDO.setNotify_way((String)msgObject.get("notify_way"));
							notifyDO.setStatus((String)msgObject.get("status"));
							notifyDO.setUserId(Integer.parseInt(msgObject.get("userId")));
							list.add(notifyDO);
						}	
						return list;
					}
				});
				return object;
			}
		});
		try {
			List<NotifyDO> list = future.get(TIME_OUT, TimeUnit.SECONDS);
			return list;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<NotifyDO> getUserNotifyList(String userId) {
		long start = System.currentTimeMillis();
		final String _userId = userId;
		Future<List<NotifyDO>> future = pool.submit(new Callable<List<NotifyDO>>(){
			@Override
			public List<NotifyDO> call() throws Exception {
				List<NotifyDO> object = (List<NotifyDO>) readExecutor(new RedisCallBack<List<NotifyDO>>(){
					@Override
					public List<NotifyDO> doInRedis(NotifyShardedJedis jedis) {
						List<NotifyDO> list = new ArrayList<NotifyDO>();
						Set<String> msgSet = jedis.smembers(_userId); 
						Iterator<String> its = msgSet.iterator();
						NotifyDO notifyDO = null;
						while(its.hasNext()){
							Map<String,String> msgObject = jedis.hgetAll(its.next());
							notifyDO = new NotifyDO();
							notifyDO.setContent(msgObject.get("content"));
							notifyDO.setTitle(msgObject.get("title"));
							notifyDO.setNotify_level((String)msgObject.get("notify_level"));
							notifyDO.setNotify_type((String)msgObject.get("notify_type"));
							notifyDO.setNotify_way((String)msgObject.get("notify_way"));
							notifyDO.setStatus((String)msgObject.get("status"));
							notifyDO.setUserId(Integer.parseInt(msgObject.get("userId")));
//							notifyDO.setCreTime(new Date(Long.valueOf(msgObject.get("creTime"))).getTime());
//							notifyDO.setModTime(new Date(Long.valueOf(msgObject.get("modTime"))).getTime());
							list.add(notifyDO);
						}
						return list;
					}});
				return object;
			}
		});
		try {
			List<NotifyDO> list = future.get(TIME_OUT, TimeUnit.SECONDS);
			return list;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} finally{
			System.out.println(System.currentTimeMillis()-start);
		}
		return null;
	}

	@Override
	public boolean publishNotify(Map<String, String> msgMap) {
		final Map<String, String> _msgMap = msgMap;
		Future<Boolean> future = pool.submit(new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception {
				boolean result = writeExecutor(new RedisCallBack<Boolean>(){
					@Override
					public Boolean doInRedis(NotifyShardedJedis jedis) {
						String userId = _msgMap.get("userId");
						String status = _msgMap.get("status");
						if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(status)){
							String key = generaterKey(userId, status, null);
							String result = jedis.hmset(key, _msgMap);
							if(!RESULT_OK.equals(result)){
								return false;
							}
							long code = jedis.sadd(userId, key);
							if(code == 0){
								return false;
							}
							return true;
						}
						return false;
					}
				});
				return result;
			}
		});
		boolean obj = false;
		try {
			 obj = future.get(TIME_OUT, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public boolean publishNotify(String key,Map<String, String> msgMap) {
		final String _key = key;
		final Map<String,String> _msgMap = msgMap;
		Future<Boolean> future = pool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				Boolean result = writeExecutor(new RedisCallBack<Boolean>() {
					@Override
					public Boolean doInRedis(NotifyShardedJedis jedis) {
						if(_msgMap.get("userId") != null && _msgMap.get("status") != null){
							String result = jedis.hmset(_key, _msgMap);
							if(!RESULT_OK.equals(result)){
								return false;
							}
							long code = jedis.sadd(_msgMap.get("userId"), _key);
							if(code == 0) return false;
							return true;
						}
						return false;
					}
				});
				return result;
			}
		});
		boolean result = false;
		try {
			result = future.get(TIME_OUT, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateNotifyStatus(final String userId, final String oldStatus,
			final String newStatus, final String notifyId) {
		Future<Boolean> future = pool.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() {
				Boolean result = writeExecutor(new RedisCallBack<Boolean>() {
					@Override
					public Boolean doInRedis(NotifyShardedJedis jedis) {
						if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(newStatus) && 
								StringUtils.isNotEmpty(oldStatus)){
							String key = generaterKey(userId,oldStatus,notifyId);
							if(jedis.exists(key)){
								Map<String,String> msgMap = jedis.hgetAll(key);
								msgMap.put("status", newStatus);
								boolean delResult = deleteNotify(userId, new String[]{oldStatus}, new String[]{notifyId});
								if(!delResult) return false;
								String newKey = generaterKey(userId, newStatus, notifyId);
								boolean pubResult = publishNotify(newKey,msgMap);
								if(!pubResult) return false;
								return true;
							}
							return false;
						}
						return false;
					}
				});
				return result;
			}
		});
		boolean object = false;
		try {
			 object = future.get(TIME_OUT, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
		}
		return object;
	}

	public String generaterKey(String userId,String status,String notifyId){
		if(StringUtils.isNotBlank(notifyId)){
			return userId+"_"+status+"_" + notifyId;
		}
		return userId+"_"+status+"_" + getUUID();
	}
	
	public String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void publishNotifyByPipe(Map<String, String> msgMap) {
		 final Map<String, String> _msgMap = msgMap;
		 pool.execute(new Runnable(){
			@Override
			public void run() {
				writeExecutor(new RedisCallBack() {
					@Override
					public Object doInRedis(NotifyShardedJedis jedis) {
						NotifyShardedJedisPipeline pipe = jedis.pipelined();
						if(_msgMap.get("userId") != null && _msgMap.get("status") != null){
							String key = generaterKey(_msgMap.get("userId"), _msgMap.get("status"),null);
							System.out.println("生成的key==="+key);
							pipe.hmset(key, _msgMap);
							pipe.sadd(_msgMap.get("userId"), key);
							pipe.sync();
						}
						return null;
					}
				});
			}
		});
	}

	@Override
	public List<NotifyDO> getUserNotifyListByPipeline(String userId) {
		long start = System.currentTimeMillis();
		final String _userId = userId;
		Future<List<NotifyDO>> future = pool.submit(new Callable<List<NotifyDO>>(){
			@Override
			public List<NotifyDO> call() throws Exception {
				List<NotifyDO> object = (List<NotifyDO>) readExecutor(new RedisCallBack<List<NotifyDO>>(){
					@Override
					public List<NotifyDO> doInRedis(NotifyShardedJedis jedis) {
						List<NotifyDO> list = new ArrayList<NotifyDO>();
						Set<String> msgSet = jedis.smembers(_userId); 
						Iterator<String> its = msgSet.iterator();
						NotifyDO notifyDO = null;
						NotifyShardedJedisPipeline pipeline = jedis.pipelined();
						Map<String,String> msgObject = null;
						while(its.hasNext()){
							pipeline.hgetAll(its.next());
						}
						List<Object> result = pipeline.syncAndReturnAll();
						for(Object obj : result){
							msgObject = (Map<String, String>) obj;
							notifyDO = new NotifyDO();
							notifyDO.setContent(msgObject.get("content"));
							notifyDO.setTitle(msgObject.get("title"));
							notifyDO.setNotify_level((String)msgObject.get("notify_level"));
							notifyDO.setNotify_type((String)msgObject.get("notify_type"));
							notifyDO.setNotify_way((String)msgObject.get("notify_way"));
							notifyDO.setStatus((String)msgObject.get("status"));
							notifyDO.setUserId(Integer.parseInt(msgObject.get("userId")));
//							notifyDO.setCreTime(new Date(Long.valueOf(msgObject.get("creTime"))).getTime());
//							notifyDO.setModTime(new Date(Long.valueOf(msgObject.get("modTime"))).getTime());
							list.add(notifyDO);
						}
						return list;
					}});
				return object;
			}
		});
		try {
			List<NotifyDO> list = future.get(TIME_OUT, TimeUnit.SECONDS);
			return list;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} finally {
			System.out.println(System.currentTimeMillis() - start);
		}
		return null;
	}
}
