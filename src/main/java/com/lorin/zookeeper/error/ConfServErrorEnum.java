
package com.lorin.zookeeper.error;

/**
 * 配置中心，错误枚举
 */
public enum ConfServErrorEnum {
	
	SERV_CONNECT_FAIL("SERV_CONNECT_FAIL","连接配置服务器失败"),
	
	NO_LISTENER_BINDED("NO_LISTENER_BINDED","没有绑定配置服务器的监听器"),
	
	SERV_SYSTEM_ERROR("SERV_SYSTEM_ERROR","未知系统异常");
	
	/** 错误描述 */
	private String desc;
	
	/** 错误码 */
	private String code;

	private ConfServErrorEnum(String desc, String code) {
		this.desc = desc;
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
}
