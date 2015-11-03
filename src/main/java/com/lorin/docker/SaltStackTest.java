package com.lorin.docker;

import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;



public class SaltStackTest {
	
	public static final String URL = "https://10.9.120.21:8001";

	public static final String username = "salt";
	
	public static final String password = "salt";
	
	public static void testSalTest(){
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String postBody = "[{\"username\":\"salt\",\"password\":\"salt\",\"eauth\":\"pam\",\"client\":\"local\"," +
				"\"tgt\":\"10.9.120.20\",\"expr_form\":\"ipcidr\",\"fun\":\"cmd.run\",\"arg\":\"df -h\"}]";
		boolean rs = tools.executePostMethodForSalt(URL+"/run", postBody, "application/json");
		System.out.println("result:"+rs+",content:"+tools.getStrGetResponseBody());
	}
	
	
	
	public static void testSaltLogin(){
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String postBody = "{\"username\":\"salt\",\"password\":\"salt\",\"eauth\":\"pam\"}";
		boolean rs = tools.executePostMethodForSalt(URL+"/login", postBody, "application/json");
		System.out.println("result:"+rs+",content:"+tools.getStrGetResponseBody()+",code:"+tools.getiGetResultCode());
	}
	
	public static void main(String[] args) {
		testSalTest();
//		testSaltLogin();
	}
}
