package com.lorin.jedis.service;

import java.util.List;
import java.util.Map;

public interface NotifyService {

	/**
	 * @param msgMap
	 * @return
	 */
	public boolean publishNotify(Map<String,String> msgMap);
	
	/**
	 * @param msgMap
	 * @return
	 */
	public void publishNotifyByPipe(Map<String,String> msgMap);
	
	/**
	 * @param msgMap
	 * @return
	 */
	public boolean publishNotify(String key,Map<String,String> msgMap);
	
	/**
	 * 
	 * @param userId
	 * @param status 
	 * @return
	 */
	public List<NotifyDO> getUserNotifyList(String userId,String status);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<NotifyDO> getUserNotifyList(String userId);
	
	
	/**
	 * @param userId
	 * @return
	 */
	public List<NotifyDO> getUserNotifyListByPipeline(String userId);
	
	/**
	 * @param userId
	 * @param notifyId
	 * @return
	 */
	public boolean updateNotifyStatus(String userId,String oldStatus,String newStatus,String notifyId);
	
	/**
	 */
	public boolean deleteNotify(String userId,String[] status,String[] notifyId);
}
