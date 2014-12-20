package com.lorin.zookeeper.obj;

import com.lorin.zookeeper.ZKProxy;


/**
 * 节点对象，是对配置节点的包装和抽象
 */
public abstract class ZObject {
	
	/**	zookeeper服务端的本地代理 */
	protected ZKProxy zkProxy = ZKProxy.getZKProxy();
	
	/**
	 * 获得服务在配置服务器上的路径
	 * @return	路径
	 */
	protected abstract String getZPath();
	
	/**
	 * 获取对本对象的描述
	 * @return 描述
	 */
	protected abstract String getDescription();
	
	/**
	 * 向配置服务器注册自己
	 */
	protected void register() {
		
		// 如果子系统跟结点不存在，创建之
		if(!zkProxy.isZNodeExist(getZPath())) {
			zkProxy.createPersistNode(getZPath(), getDescription());
		}
	}
	
	/**
	 * 为对象设置监听变
	 */
	protected void listen() {
		zkProxy.listenChild(getZPath());
	}
	
}
