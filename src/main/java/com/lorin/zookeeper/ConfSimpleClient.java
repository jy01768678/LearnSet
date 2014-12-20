package com.lorin.zookeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ConfSimpleClient {
	/**	zookeeper服务端的本地代理 */
	private ZooKeeper zClient;
	
	
	/**
	 * 构造函数
	 */
	public ConfSimpleClient() {
		// 连接配置中心服务集群并获得服务代理
		this.zClient = connectServer();
	}
	
	/**
	 * 发布服务
	 */
	public boolean pubService(String system,String service,String providerIP) {
	
		try {
			zClient.create("/" + system + "/" + service + "/" + providerIP, "Hello,are you OK".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 引用服务
	 */
//	public boolean subService() {
//		
//	}

	/**
	 * 连接zookeeper服务端
	 * @throws IOException 
	 */
	private ZooKeeper connectServer(){
		
		ZooKeeper zCli = null;
		
		try {
			zCli = new ZooKeeper("10.9.28.92:2181", 2000, new WhatherDemo(this));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zCli;
	}
	
	/**
	 * 关闭连接
	 * @throws InterruptedException 
	 */
	public void shutConnect() throws InterruptedException {
		zClient.close();
	}
	
	/**
	 * 创建ZNode
	 * @return 节点的路径 
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public String createZNode() throws KeeperException, InterruptedException {
		return zClient.create("/dubbo", null, Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
	}
	
	/**
	 * 获得ZNode数据
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @throws UnsupportedEncodingException 
	 */
	public String getZNode() throws KeeperException, InterruptedException, UnsupportedEncodingException {
		Stat stat = new Stat();
		byte[] dataValue = zClient.getData("/dubbo", true, stat);
		return new String(dataValue) + stat;
	}
	
	/**
	 * 获取子节点
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 * consumers
	 * providers
	 * configurators
	 * routers
	 */
	public List<String> getChildrens() throws KeeperException, InterruptedException{
		return zClient.getChildren("/dubbo/com.tuan.notify.service.NotifyService/configurators", true);
	}
	
	/**
	 * 检验ZNode是否存在
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public boolean isZNodeExist() throws KeeperException, InterruptedException {
		Stat stat = zClient.exists("/dubbo/com.tuan.notify.service.NotifyService", false);
		
		if(null == stat) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 修改ZNode的值
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public void setZnodeValue() throws KeeperException, InterruptedException {
		zClient.setData("/dubbo", "负重致远".getBytes(), -1);
	}
	
	/** 
	 * 删除ZNode
	 * @throws KeeperException 
	 * @throws InterruptedException 
	 */
	public void deleteZNode() throws InterruptedException, KeeperException {
		// "-1"代表删除Znode所有版本
		zClient.delete("/dubbo", -1); 
	}
	
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ConfSimpleClient host = new ConfSimpleClient();
		host.connectServer();
		
//		String path = host.createZNode();
//		System.out.println("新建ZNode路径为：" + path);
		
//		boolean isExsit = host.isZNodeExist();
//		System.out.println(isExsit);
		
//		String value = client.getZNode();
		List<String> value = host.getChildrens();
		for(String service : value){
			System.out.println("获取数据为：" + service);
		}
		
//		String value1 = client.getZNode();
//		System.out.println("获取数据为：" + value1);
//		client.shutConnect();
//		Thread.sleep(TimeUnit.DAYS.toSeconds(20));
		
	}
}
