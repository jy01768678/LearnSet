package com.lorin.zookeeper.obj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lorin.zookeeper.ZKProxy;


/**
 * 表示一项服务，是对配置节点的包装和抽象
 * 
 */
public class Service extends ZObject {
	
	private static final Logger logger = Logger.getLogger(Service.class);
	
	/** 如果节点的值是service，表示该节点标志子系统机器集群 */
	static final String SERVICE = "service";
	
	/**	服务名称 */
	private String name;
	
	/** 提供服务的机器 */
	private Map<String,Machine> machines = new HashMap<String, Machine>();
	
	/** 提供服务的机器IP */
	private List<String> machineIPs;
	
	/** 计数器 */
	private int counter = 0;
	
	
	// 检查给你路径是否是一个服务的路径
	public static boolean isServicePath(String path) {
		String desc = ZKProxy.getZKProxy().getZNodeDesc(path);
		return desc.equalsIgnoreCase(SERVICE);
	}

	// 从service节点路径中获取machine对应的服务名
	public static String getServNameFromPath(String path) {
		if(!isServicePath(path)) {
			throw new RuntimeException("zookeeper path is invalid");
		}
		return path.substring(path.indexOf('/') + 1);
	}

	/**
	 * 构造方法
	 * @param name		服务名称 
	 * @param zClient	zookeeper服务端的本地代理
	 */
	public Service(String name) {
		this.name = name;
	}

	/**
	 * 刷新本地缓存的服务提供者（机器对象）
	 */
	public synchronized void updateMachines() {
		
		logger.info("刷新提供服务的机器，服务名为：" + name + ",机器为：" + machineIPs);
		
		// 获取service对应的machine IP，并监听machine节点的创建于销毁
		machineIPs =  zkProxy.listenChild(getZPath());
		// 计数器清零
		counter = 0;
		
		// 清空缓存的机器对象
		machines.clear();
		
		for (String machineIP : machineIPs) {
			
			Machine machine = new Machine(machineIP, this);
			machine.listen();
			machines.put(machineIP, machine);
		}
		
		logger.info("刷新完毕，服务名为：" + name + ",机器为：" + machineIPs);
	}
	
	/**
	 * 获取一台可以活动状态的机器
	 * @return	机器
	 */
	public synchronized Machine getOneAliveMachine() {
		Machine aliveMachine;
		do {
			aliveMachine = machines.get(machineIPs.get(counter));
			counter = (counter + 1) % machineIPs.size();
		} while(!aliveMachine.isAlive());
		return aliveMachine;
	}
	
	/**
	 * @return the machines
	 */
	public Map<String, Machine> getMachines() {
		return machines;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获得服务在配置服务器上的路径
	 * @return	路径
	 */
	@Override
	public String getZPath() {
		return "/" + name;
	}

	@Override
	protected String getDescription() {
		return SERVICE;
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer("Service[");
		sb.append("Name:" + this.name + ";" + "Machines:" );
		
		for (Machine machine  : machines.values()) {
			sb.append( machine.getIP() + ",");
		}
		
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
}
