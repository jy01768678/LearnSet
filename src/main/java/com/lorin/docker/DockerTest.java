package com.lorin.docker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;

public class DockerTest {

	public static void getContainerLog(){
		
//		String url = "http://10.9.120.26:2376/containers/8c5f1152d364/logs?stdout=1&stderr=1";
		String url = "http://10.9.120.26:2376/containers/a2293a1e82dd/logs?stderr=1&stdout=1&timestamps=1";
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpClientTools tools = HttpClientManager.getHttpClientTools();
			boolean rs = tools.executeGetMethod(url, null);
			System.out.println("log:"+tools.getStrGetResponseBody());
		}
		
	}
	
	public static void getExec(){
		String url = "http://10.9.120.26:2376/containers/18fc405ee055/exec";
		//d8f8a62dd097b0b79f15050bf2a9be86f2502fedada04719f36a06938929dc23
		String url1 = "http://10.9.120.26:2376/exec/4d08ea6579b0c8b4c58e08563940db8b26156394b52f2a16a3687e9ccfff097f/start";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String postBody = "{\"AttachStdin\": true,\"AttachStdout\": true,\"AttachStderr\": true,\"Tty\": true, \"Cmd\": [ \"bash\"]}";
		String postBody1 = "{\"Detach\":false,\"Tty\":true}";
		boolean rs = tools.executePostMethodStream(url1, postBody1, "application/json");
		System.out.println(tools.getStrGetResponseBody());
	}
	
	
	public static void getTop() throws UnsupportedEncodingException{
		String url = "http://10.9.120.26:2376/containers/json";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("filters","{\"label\":[\"app=aether-nor\"]}");
		String paramStr = null;
		StringBuilder bui = new StringBuilder();
		if(null != params){
			Iterator<String> it = params.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = params.get(key);
				bui.append(key).append("=").append(URLEncoder.encode(value,"UTF-8")).append("&");
			}
			String tmp = bui.toString();
			if(tmp.length() > 0){
				paramStr = tmp.substring(0, tmp.length() - 1);
			}
		}
		//
		boolean rs = tools.executeGetMethod(url, paramStr);
		String resultStr = tools.getStrGetResponseBody();
		System.out.println("log:"+resultStr);
//		JSONArray json = JSONArray.fromObject(resultStr);
//		for(int i=0;i<json.size();i++){
//			JSONObject obj = json.getJSONObject(i);
//			System.out.println(obj.getJSONArray("Names").getString(0));
//		}	
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		getContainerLog();
//		getExec();
		getTop();
	}
}
