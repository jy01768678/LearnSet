package com.lorin.zookeeper.listener;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.lorin.zookeeper.ConfSerCli;
import com.lorin.zookeeper.obj.Machine;
import com.lorin.zookeeper.obj.Service;


/**
 * zookeeper服务端监听器
 * 
 */
public class ConfServListener implements Watcher{
	
	private static final Logger logger = Logger.getLogger(ConfServListener.class);
	
	/**
	 * 处理监听到事件
	 */
	public void process(WatchedEvent event) {
		
		logger.info("收到事件：" + event);
		
		if(event.getType() != Event.EventType.NodeChildrenChanged) {
			return;
		}
		
		String path = event.getPath();
		
		if(Machine.isMachinePath(path)) {
        	
			// 机器发生变更，更新机器可用状态
        	String serviceName = Machine.getServNameFromPath(path);
        	String machineIP = Machine.getIPFromPath(path);
        	Machine machine = ConfSerCli.getConfSerCli().getMachine(serviceName, machineIP);
        	machine.setAlive();
        	
		} else if(Service.isServicePath(path)) {
        	
			// 服务发生变更，重新加载一下服务对应的所有机器
        	String serviceName = path.substring(path.indexOf('/') + 1);
        	Service.getServNameFromPath(path);
        	ConfSerCli.getConfSerCli().listenService(serviceName);
		}
	}




}
