/**
 * 
 */
package com.lorin.zookeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lorin.zookeeper.listener.ConfServListener;
import com.lorin.zookeeper.obj.Machine;
import com.lorin.zookeeper.obj.Service;


/**
 * 配置服务客户端
 * 是一个单例
 * 
 */
public class ConfSerCli{
	
	private static final Logger logger = Logger.getLogger(ConfSerCli.class);
	
	/** 配置服务客户端单例 */
	private static ConfSerCli confSerCli = null;
	
	/**
	 * 本地所依赖服务的缓存
	 */
	private Map<String,Service> services = new HashMap<String,Service> ();
	
	/** 仅用作同步锁 */
	private static Object lock = new Object(); 
	
	/**
	 * 构造函数
	 */
	private ConfSerCli() {
		logger.info("配置服务客户端单例已创建");
	}
	
	/**
	 * 获取配置服务客户端单例
	 * 
	 * @return 配置服务客户端单例
	 */
	static public ConfSerCli getConfSerCli() {
		
		synchronized (lock) {
			if(confSerCli == null) {
				confSerCli = new ConfSerCli();
				init();
			}
		}
		
		return confSerCli;
	}
	
	static private void init() {
		ZKProxy.getZKProxy().bindListener(new ConfServListener());
	}
	
	/**
	 * 注册服务
	 * 
	 * @param service		提供的服务
	 * @param machineIP		提供服务的主机IP
	 */
	public void registerService(String serviceName,String machineIP) {
		
		Service service = new Service(serviceName);
		Machine machine = new Machine(machineIP, service);
		machine.register();
		logger.info("服务成功注册：服务名称" + serviceName + "机器IP：" + machineIP);
	}
	
	/**
	 * 监听提供某项服务的主机
	 * <p>
	 * 副作用是更新本地service缓存,将service对应的所有机器重新添加至缓存
	 * </p>
	 * 
	 * @param serviceName	服务名称
	 * @return	是否成功设置监听
	 */
	public void listenService(String serviceName) {
		
		Service service = new Service(serviceName);
		service.updateMachines();
		services.put(serviceName, service);
		logger.info("服务成功监听：" + service);
	}

	/**
	 * 获取某项服务所有提供者的主机IP
	 * <p>
	 * 获取当前提供该项服务的所有主机IP，不管这些主机提供的服务是否可用
	 * </p>
	 * 
	 * @param serviceName	服务名称
	 * @return		服务停供者的主机IP列表
	 */
	public List<String> getAllMachineIPs(String serviceName) {
		
		List<String> IPs = new ArrayList<String>();
		
		Service service = services.get(serviceName);
		
		// 如果没有取到service，刷新一下缓存再取一遍
		if(null == service) {
			listenService(serviceName);
			service = services.get(serviceName);
		}
		
		// 刷缓存后还是取不到，直接返回
		if(null == service) {
			return IPs;
		}
		
		
		IPs.addAll(service.getMachines().keySet());
		return IPs;
	}
	
	/**
	 * 获取某项服务提供者的主机IP，这些主机都处于存活状态
	 * 
	 * @param serviceName	服务名称
	 * @return		服务停供者的主机IP列表
	 */
	public List<String> getAliveMachineIPs(String serviceName) {
		
		List<String> IPs = new ArrayList<String>();
		
		Service service = services.get(serviceName);
		
		// 如果没有取到service，刷新一下缓存再取一遍
		if(null == service) {
			listenService(serviceName);
			service = services.get(serviceName);
		}
		
		// 刷缓存后还是取不到，直接返回
		if(null == service) {
			return IPs;
		}
		
		for (Machine machine : service.getMachines().values()) {
			if(machine.isAlive()) {
				IPs.add(machine.getIP());
			}
		}
		
		return IPs;
	}
	
	/**
	 * 根据服务名称获取一台可用机器的IP
	 * 
	 * @param serviceName	服务名称
	 * @return	机器IP
	 */
	public String getOneAliveMachine(String serviceName) {
		Service  service = services.get(serviceName);
		return service.getOneAliveMachine().getIP();
	}
	
	/**
	 * 获取指定的某台机器
	 * 
	 * @param serviceName	提供的服务名
	 * @param machineIP		机器IP
	 * @return	机器对象
	 */
	public Machine getMachine(String serviceName,String machineIP) {
	    Service  service = services.get(serviceName);
	    return service.getMachines().get(machineIP);
	}


	/**
	 * 关闭与配置服务器的连接
	 */
	public void shutConnect() throws InterruptedException {
		ZKProxy.getZKProxy().shutConnect();
	}

}
