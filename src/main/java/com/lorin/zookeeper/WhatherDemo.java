package com.lorin.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class WhatherDemo implements Watcher {

private ConfSimpleClient zClient;
	
	public WhatherDemo(ConfSimpleClient zClient) {
		super();
		this.zClient = zClient;
	}

	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("<--收到消息-->" + event + "\n");
//		try {
//			System.out.println("读取子节点：" + zClient.getChildrens());
//		} catch (KeeperException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
