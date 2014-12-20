package com.lorin.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import com.lorin.zookeeper.error.ConfServErrorEnum;
import com.lorin.zookeeper.error.ConfServException;
import com.lorin.zookeeper.util.DigestLogUtil;


/**
 * ZooKeeper客户端代理
 * <p>
 * 是一个单例
 */
public class ZKProxy implements Watcher {
	
	private static final Logger logger = Logger.getLogger(ZKProxy.class);
	
	/**	zookeeper服务端的本地代理 */
	private ZooKeeper zooKeeper;
	
	/** 配置服务客户端单例 */
	private static ZKProxy zKProxy = null;
	
	/** 仅用作同步锁 */
	private static Object lock = new Object(); 
	
	/** 是否绑定监听器 */
	private boolean bindListener = false;
	
	/**
	 * 构造函数
	 */
	private ZKProxy() {
		// 连接配置中心服务集群并获得服务代理
		this.zooKeeper = connectServer();
	}
	
	/**
	 * 连接zookeeper服务端
	 * @throws IOException 
	 */
	private ZooKeeper connectServer(){
		
		DigestLogUtil.beforeExecute("连接配置服务器");
		boolean success = true;
		
		ZooKeeper zk = null;
		
		try {
			zk = new ZooKeeper("10.3.20.14:2181", 2000, this);
			logger.info("连接配置中心成功：" + zk);
		} catch (IOException e) {
			success = false;
			logger.error("连接配置中心失败，服务停止启动",e);
			throw new ConfServException(ConfServErrorEnum.SERV_CONNECT_FAIL);
		} finally {
			DigestLogUtil.afterExecute(success);
		}
		return zk;
	}
	
	/**
	 * 获取配置服务客户端单例
	 * 
	 * @return 配置服务客户端单例
	 */
	static public ZKProxy getZKProxy() {
		
		synchronized (lock) {
			if(zKProxy == null) {
				zKProxy = new ZKProxy();
			}
		}
		
		return zKProxy;
	}
	
	/**
	 * 关闭连接
	 */
	public void shutConnect(){
		
		DigestLogUtil.beforeExecute("关闭配置中心连接");
		boolean success = true;
		
		try {
			zooKeeper.close();
			logger.info("成功关闭与配置中心的连接");
		} catch (InterruptedException e) {
			success = false;
			logger.error("关闭配置中心连接请求失败",e);
			throw new ConfServException(ConfServErrorEnum.SERV_CONNECT_FAIL);
		} finally {
			DigestLogUtil.afterExecute(success);
		}
	}

	/**
	 * 检验ZNode是否存在
	 */
	public boolean isZNodeExist(String path){
		
		DigestLogUtil.beforeExecute("检验ZNode是否存在");
		boolean success = true;
		
		isBindListener();
		
		Stat stat = null;
		try {
			stat = zooKeeper.exists(path, false);
		} catch (Exception e) {
			success = false;
			logger.error("操作时发生未知系统异常",e);
			throw new ConfServException(ConfServErrorEnum.SERV_SYSTEM_ERROR);
		} finally {
			DigestLogUtil.afterExecute(success);
		}
		
		if(null == stat) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 创建固定节点
	 * 
	 * @param zPath	节点路径
	 * @param desc	节点描述
	 */
	public void createPersistNode(String zPath, String desc) {
		
		DigestLogUtil.beforeExecute("创建固定节点");
		boolean success = true;
		
		isBindListener();
		
		byte[] data = null;
		
		if(null != desc) {
			data = desc.getBytes();
		}
		
		try {
			zooKeeper.create(zPath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) {
			success = false;
			logger.error("操作时发生未知系统异常",e);
			throw new ConfServException(ConfServErrorEnum.SERV_SYSTEM_ERROR);
		} finally {
			DigestLogUtil.afterExecute(success);
		}
	}
	
	/**
	 * 创建临时节点
	 * 
	 * @param zPath	节点路径
	 * @param desc	节点描述
	 */
	public void createTempNode(String zPath, String desc) {
		
		DigestLogUtil.beforeExecute("创建临时节点");
		boolean success = true;
		
		isBindListener();
		
		byte[] data = null;
		
		if(null != desc) {
			data = desc.getBytes();
		}
		
		try {
			zooKeeper.create(zPath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} catch (Exception e) {
			success = false;
			logger.error("操作时发生未知系统异常",e);
			throw new ConfServException(ConfServErrorEnum.SERV_SYSTEM_ERROR);
		} finally {
			DigestLogUtil.afterExecute(success);
		}
	}

	/**
	 * 监听子节点变化（只监听子节点的创建和销毁）
	 */
	public List<String> listenChild(String zPath) {
		
		DigestLogUtil.beforeExecute("获取子节点");
		boolean success = true;
		
		isBindListener();
		
		List<String> children = null;
		try {
			children = zooKeeper.getChildren(zPath, true);
		} catch (Exception e) {
			success = false;
			logger.error("操作时发生未知系统异常",e);
			throw new ConfServException(ConfServErrorEnum.SERV_SYSTEM_ERROR);
		} finally {
			DigestLogUtil.afterExecute(success);
		}
		
		return children;
	}

	/**
	 * 获取节点的描述
	 * 
	 * @param path	节点路径
	 * @return	节点描述
	 */
	public String getZNodeDesc(String path) {
		
		DigestLogUtil.beforeExecute("获取子节点");
		boolean success = true;
		
		isBindListener();
		
		if(null == path) {
			return null;
		}
		
		byte[] dataValue = null;
		try {
			dataValue = zooKeeper.getData(path, false, null);
		} catch (Exception e) {
			success = false;
			logger.error("操作时发生未知系统异常",e);
			throw new ConfServException(ConfServErrorEnum.SERV_SYSTEM_ERROR);
		} finally {
			DigestLogUtil.afterExecute(success);
		} 
		
		return new String(dataValue);
	}
	
	/**
	 * 绑定监听器
	 * @param listener	监听器
	 */
	public void bindListener(Watcher listener) {
		zooKeeper.register(listener);
		bindListener = true;
		logger.info("监听器已绑定,监听器类型为：" + listener);
	}
	
	/**
	 * 判断是否绑定监听器，若未绑定，抛出异常
	 */
	private void isBindListener() {
		
		if(!this.bindListener) {
			logger.error("严重错误,没有绑定配置服务器的监听器");
			throw new ConfServException(ConfServErrorEnum.NO_LISTENER_BINDED);
		}
	}

	// 该监听器只是打印出收到的事件，若要正常处理监听事件请绑定特定的监听器
	public void process(WatchedEvent event) {
		logger.info("收到事件：" + event);
	}
	
}
