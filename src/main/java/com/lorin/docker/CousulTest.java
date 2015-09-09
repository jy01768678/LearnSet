package com.lorin.docker;

import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;


public class CousulTest {

	public static String DEFAULT_HTTP_HOST = "http://10.9.120.25:8500";
	public static String MEMBERS = "/v1/agent/members";
	private static String NODE = "/v1/health/node/";
	private static String SERVICE = "/v1/health/service/";
	private static String SERVICES = "/v1/catalog/services";
	
	
	public static String CATA_NODE = "/v1/catalog/nodes";
	public static String DC = "/v1/catalog/datacenters";
	public static String NODE_NODE = "/v1/catalog/node/";
	
	public static void main(String[] args) {
//		getMembers();
//		getNodeChecks("mesos25");
//		getServices();
//		getAllServiceInstances("billingcenter-dev");
		getServiceByNode("ubuntu21");
	}
	
	/**
	 * 节点获取
	 */
	public static void getMembers() {
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String url = DEFAULT_HTTP_HOST + MEMBERS;
		boolean rs = tools.executeGetMethod(url, null);
		if(rs){
			String message = tools.getStrGetResponseBody();
			System.out.println(message);
		}	
	}
	
	public static void getNodeChecks(String node) {
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String url = DEFAULT_HTTP_HOST + NODE + node;
		boolean rs = tools.executeGetMethod(url, null);
		if(rs){
			String message = tools.getStrGetResponseBody();
			System.out.println(message);
		}	
	}
	
	/**
	 * 获取所有节点上的该服务健康信息
	 * @param service
	 */
	public static void getAllServiceInstances(String service){
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String url = DEFAULT_HTTP_HOST + SERVICE + service;
		boolean rs = tools.executeGetMethod(url, null);
		if(rs){
			String message = tools.getStrGetResponseBody();
			System.out.println(message);
		}	
	}
	
	/**
	 * 获取所有服务信息
	 */
	public static void getServices(){
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String url = DEFAULT_HTTP_HOST + SERVICES;
		boolean rs = tools.executeGetMethod(url, null);
		if(rs){
			String message = tools.getStrGetResponseBody();
			System.out.println(message);
		}	
	}
	
	public static void getServiceByNode(String node){
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String url = DEFAULT_HTTP_HOST + NODE_NODE + node;
		boolean rs = tools.executeGetMethod(url, null);
		if(rs){
			String message = tools.getStrGetResponseBody();
			System.out.println(message);
		}
	}
}
