package com.lorin.httpClient;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class HttpClientManager {
	private static MultiThreadedHttpConnectionManager connectionManager = null;//
	private static HttpClientManager instance = new HttpClientManager();

	public static HttpClientManager getInstance() {
		return instance;
	}

	private int maxThreadsTotal = 128;

	private int maxThreadsPerHost = 32; 

	private int connectionTimeout = 10000;

	private int soTimeout = 60000;

	private HttpClientManager() {
		init();
	}

	public void init() {
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(connectionTimeout);
		params.setMaxTotalConnections(maxThreadsTotal);
		params.setSoTimeout(soTimeout);
		if (maxThreadsTotal > maxThreadsPerHost) {
			params.setDefaultMaxConnectionsPerHost(maxThreadsPerHost);
		} else {
			params.setDefaultMaxConnectionsPerHost(maxThreadsTotal);
		}
		connectionManager.setParams(params);
	}

	public static HttpClientTools getHttpClientTools(){
		return new HttpClientTools(connectionManager);
	}
	
	public int getMaxThreadsTotal() {
		return maxThreadsTotal;
	}

	public void setMaxThreadsTotal(int maxThreadsTotal) {
		this.maxThreadsTotal = maxThreadsTotal;
	}

	public int getMaxThreadsPerHost() {
		return maxThreadsPerHost;
	}

	public void setMaxThreadsPerHost(int maxThreadsPerHost) {
		this.maxThreadsPerHost = maxThreadsPerHost;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	
	
	public static void main(String[] args) {
		getInstance();
		System.out.println(HttpClientManager.connectionManager==null);
	}

	public static MultiThreadedHttpConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public static void setConnectionManager(
			MultiThreadedHttpConnectionManager connectionManager) {
		HttpClientManager.connectionManager = connectionManager;
	}
}
