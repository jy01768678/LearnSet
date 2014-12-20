package com.lorin.zookeeper.util;

import org.apache.log4j.Logger;

/**
 * 摘要日志打印工具
 */
public class DigestLogUtil {
	
	/** 打印操作摘要 */
	private static final Logger digestlLog = Logger.getLogger("OP_DIGEST");
	
	/** 暂时存储执行时间 */
	private static long time;
	
	/** 暂时存储操作名称 */
	private static String operation;
	
	/** 表示在执行afterExecute之前是否执行了beforeExecute */
	private static boolean flag = false;
	
	/**
	 * 置于在方法执行前
	 * @param operate	操作名称
	 */
	public static void beforeExecute(String operate) {
		time = System.currentTimeMillis();
		operation = operate;
		flag = true;
	}
	
	/**
	 * 置于在方法执行后
	 * @param isSuccess	是否执行成功
	 */
	public static void afterExecute(boolean isSuccess) {
		
		if(!flag) {
			throw new RuntimeException("在执行afterExecute方法时，必须先执行beforeExecute方法");
		}
		
		time = System.currentTimeMillis() - time;
		
		if(isSuccess) {
			digestlLog.info(operation + "," + "S" + "," + time + "ms");
		} else {
			digestlLog.info(operation + "," + "F" + "," + time + "ms");
		}
		
		flag = false;
	}
}
