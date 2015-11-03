package com.lorin.docker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;
import com.thoughtworks.xstream.core.util.Base64Encoder;

public class MarathonTest {

	public static String maraUrl = "http://10.9.120.21:8080";
	public static String maraHttps = "https://10.9.120.21:8443";
	public static String maraApi = "/v2/apps/";
	public static  String appId = "aether-spe";
	
	public static void main(String[] args) {
//		getAppJson();
//		deleteTasks();
		getTaskHttps();
	}
	
	
	public static void getTaskHttps(){
		String realUrl = maraHttps+maraApi+"mars-cg";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Basic " + new Base64Encoder().encode(("wowotuan" + ":" + "wowotuan").getBytes()));
		boolean rs = tools.executeGetMethodHttps(realUrl, null, headers);
		if(rs){
			System.out.println("结果:" + tools.getStrGetResponseBody());
		}
	}
	
	public static void deleteTasks(){
		String realUrl = maraUrl+maraApi+"actcenter-nor"+"/tasks"; 
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		
		boolean rs = tools.executeDeleteMethod(realUrl, "scale=true");
		System.out.println(tools.getStrGetResponseBody());
	}
	
	public static void putAppJson(){
		String realUrl = maraUrl+maraApi+appId;
		
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		String json = getDockerJson();
		String part_json = "{\"instances\":2,\"mem\":4096,\"upgradeStrategy\":{\"minimumHealthCapacity\": 1, \"maximumOverCapacity\": 1}}";
		boolean rs = tools.executePutMethod(realUrl, part_json, "UTF-8", "application/json");
		
		if(!rs){
			System.out.println("应用发布失败。" + tools.getErrorInfo());
		}
		System.out.println("发布结果:" + tools.getStrGetResponseBody());
	}
	
	
	public static void getAppJson(){
		String realUrl = maraUrl+maraApi+appId;
		
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = tools.executeGetMethod(realUrl, null, null);
		if(rs){
			System.out.println("结果:" + tools.getStrGetResponseBody());
		}
	}
	
	public static String getDockerJson(){
		File dq = new File("");
		String fileName = "src/main/java/com/lorin/docker/mara_json.txt";
		File file = new File(dq.getAbsolutePath()+"/"+fileName);
		BufferedReader reader = null;
		String tempString = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            tempString = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return  tempString;
	}
}
