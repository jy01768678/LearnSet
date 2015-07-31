package com.lorin.docker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;

public class MarathonTest {

	public static void main(String[] args) {
		String maraUrl = "http://10.9.120.21:8080";
		String maraApi = "/v2/apps/";
		String appId = "aether002";
		
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
