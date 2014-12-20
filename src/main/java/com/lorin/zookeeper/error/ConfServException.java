
package com.lorin.zookeeper.error;

/**
 * 自定义异常，与配置中心交互出现问题的时候，抛出
 * 
 */
public class ConfServException extends RuntimeException {
	
	private static final long serialVersionUID = -3431962248270968133L;
	
	/** 配置中心错误枚举 */
	private ConfServErrorEnum errorEnum;
	
	public ConfServException(ConfServErrorEnum errorEnum) {
		super();
		this.errorEnum = errorEnum;
	}

	/**
	 * @return the errorEnum
	 */
	public ConfServErrorEnum getErrorEnum() {
		return errorEnum;
	}
	
	
}
