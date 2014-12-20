package com.lorin.zookeeper.obj;

import org.apache.log4j.Logger;

import com.lorin.zookeeper.ZKProxy;

/**
 * 表示一台提供服务的机器，是对配置节点的包装和抽象
 */
public class Machine extends ZObject{
	
	private static final Logger logger = Logger.getLogger(Machine.class);
	
	/** 如果节点的值是machine，表示该节点标志一台物理机器 */
	static final String MACHINE = "machine";

	/** 如果节点的值是aliveFlag，表示该节点对应的machine是可用的 */
	static final String ALIVE_FLAG = "aliveFlag";
	
	/** 机器IP */
	private String IP;
	
	/** 机器所提供的服务 */
	private Service service;
	
	/** 机器是否可用 */
	private boolean alive;
	
	// 检查给你路径是否是一个机器的路径
	static public boolean isMachinePath(String path) {
		
		String desc = ZKProxy.getZKProxy().getZNodeDesc(path);
		return desc.equalsIgnoreCase(MACHINE);
	}

	// 从machine节点路径中获取machine对应的服务名
	public static String getServNameFromPath(String path) {
		
		if(!isMachinePath(path)) {
			throw new RuntimeException("zookeeper path is invalid");
		}
		return path.substring(path.indexOf('/') + 1, path.lastIndexOf('/'));
	}

	// 从machine节点路径中获取machine的IP
	public static String getIPFromPath(String path) {
		if(!isMachinePath(path)) {
			throw new RuntimeException("zookeeper path is invalid");
		}
		return path.substring(path.lastIndexOf('/') + 1);
	}

	/**
	 * 构造方法
	 * 
	 * @param zClient	zookeeper服务端的本地代理
	 * @param iP		机器IP
	 * @param service	机器所提供的服务
	 */
	public Machine(String iP, Service service) {
		IP = iP;
		this.service = service;
		setAlive();
	}

	/**
	 * 设置机器的可用标志
	 */
	public void setAlive() {
		
		// 构造机器可用标志所在的路径
		String aliveFlagPath = getZPath() + "/" + ALIVE_FLAG; 
		
		this.alive = zkProxy.isZNodeExist(aliveFlagPath);
		logger.info("机器运行状态设置：" + this);
	}
	
	/**
	 * @return the iP
	 */
	public String getIP() {
		return IP;
	}

	/**
	 * @return the service
	 */
	public Service getService() {
		return service;
	}

	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * 向配置服务器注册自己
	 */
	public void register() {
		// 先注册所在的服务，在注册自己
		service.register();
		super.register();
		
		// 设置机器可用标志
		zkProxy.createTempNode(getZPath() + '/' + ALIVE_FLAG, null);
	}

	/**
	 * 获得机器在配置服务器上的路径
	 * @return	路径
	 */
	@Override
	public String getZPath() {
		return "/" + service.getName() + "/" + IP;
	}


	@Override
	protected String getDescription() {
		return MACHINE;
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer("Machine[");
		sb.append("IP:" + this.IP + ",");
		sb.append("Servic:" + this.service.getName() + ",");
		sb.append("Alive:" + this.alive + "]");
		
		return sb.toString();
	}
}
