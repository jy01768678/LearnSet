package com.lorin.docker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import org.apache.commons.httpclient.Header;
import org.apache.commons.lang.StringUtils;

import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;
import com.thoughtworks.xstream.core.util.Base64Encoder;


public class RegistryTest {

	public static String url = "https://10.9.120.21/v1/search";
	
	public static String url_V2_search = "https://10.9.120.24/v2/_catalog";
	
	public static String url2 = url_V2_search;
	
	public static String url_V2_metis = "https://10.9.120.24/v2/aether/spe/manifests/60";
	
	private static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	private static String username = "testuser";

	private static String password = "testpassword";
	
	public static void main(String[] args) {
//		TestRegistryV2_delBlob();
//		TestRegistryV2_getBlob();
//		TestRegistryV2_delManifest();
//		TestRegistryV2_getManifest();//获取docker-content-digest
//		TestRegistryV2_getTag(); // ok
//		TestRegistryV2(); //ok
//		TestRegistryV2_Del();
//		TestRegistryV1();
		TestJson();
	}

	public static void TestJson(){
//		String json = "{\"busybox\": [{\"tag\": \"v2\", \"create_time\": \"2016-01-27 10:44:08\"}], \"centos\": [{\"tag\": \"6v3\", \"create_time\": \"2016-01-26 14:47:03\"}, {\"tag\": \"6\", \"create_time\": \"2016-01-26 14:29:49\"}, {\"tag\": \"6v2\", \"create_time\": \"2016-01-26 14:36:24\"}], \"nginx\": [{\"tag\": \"1\", \"create_time\": \"2016-01-27 10:39:56\"}], \"bind\": [{\"tag\": \"v1\", \"create_time\": \"2016-01-29 14:33:39\"}, {\"tag\": \"v3\", \"create_time\": \"2016-01-29 14:42:58\"}, {\"tag\": \"v4\", \"create_time\": \"2016-01-29 14:47:16\"}, {\"tag\": \"v2\", \"create_time\": \"2016-01-29 14:34:53\"}], \"nginxtest\": [{\"tag\": \"v1\", \"create_time\": \"2016-01-27 10:43:06\"}], \"tengine\": [{\"tag\": \"v1\", \"create_time\": \"2016-01-27 10:41:57\"}], \"registry\": [{\"tag\": \"2.2.1\", \"create_time\": \"2016-01-27 11:45:20\"}]}";
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		Set<String> images = jsonObject.keySet();
//		for (String image : images){
//			JSONArray array = jsonObject.getJSONArray(image);
//			System.out.println("image:"+image+"--"+array.toString());
//		}
	}

	/**
	 * sha256:c4b0209f620b87c9c56014a30ccb47854ea59f4204ea752a5a43d22fe26a6723
	 * sha256:c053672ec8364dbfe716dd03aa65c2647665e1e3788256c4303f28bb069f4b29
	 * sha256:a3ed95caeb02ffe68cdd9fd84406680ae93d633cb16422d00e8a7c22955b46d4
	 * sha256:a2086e9272f40f8b7ed944ec1090d743794b48e53a7116a04c9800fb02f5dfb4
	 *
	 * mani:sha256:699963b358255086622ca5a8e766da045204fd0305b566dc896bfb639bd65924
	 */
	public static void TestRegistryV2_delBlob(){
		String url = "https://10.9.120.24/v2/ceshios6v1/blobs/sha256:a3ed95caeb02ffe68cdd9fd84406680ae93d633cb16422d00e8a7c22955b46d4";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		Map<String, String> headers = new HashMap<String, String>();
		if(!StringUtils.isEmpty(username)){
			headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
		}
		rs = tools.executeDeleteMethodHttps(url, null, headers);
		System.out.println(tools.getiGetResultCode()+"\t"+tools.getStrGetResponseBody());
	}
	
	public static void TestRegistryV2_getBlob(){
		String url = "https://10.9.120.24/v2/ceshios6v1/blobs/sha256:a2086e9272f40f8b7ed944ec1090d743794b48e53a7116a04c9800fb02f5dfb4";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		Map<String, String> headers = new HashMap<String, String>();
		if(!StringUtils.isEmpty(username)){
			headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
		}
		rs = tools.executeGetMethodHttps(url, null, headers);
		System.out.println(tools.getStrGetResponseBody()+"\n"+ "--code="+tools.getiGetResultCode());
		Header[] rheaders = tools.getRheaders();
		for(Header rh : rheaders){
			System.out.println(rh.getName()+"--"+rh.getValue());
		}
	}
	
	public static void TestRegistryV2_delManifest(){
		String url = "https://10.9.120.24/v2/ceshios6v1/manifests/sha256:699963b358255086622ca5a8e766da045204fd0305b566dc896bfb639bd65924";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		Map<String, String> headers = new HashMap<String, String>();
		if(!StringUtils.isEmpty(username)){
			headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
		}
		rs = tools.executeDeleteMethodHttps(url, null, headers);
		System.out.println(tools.getStrGetResponseBody() + "--code="+tools.getiGetResultCode());
	}
	
	//centos-test : sha256:fa4007ccc57768b8721ce4a18d889fd70c1b5feaf72a11981dc714594ab9fde3
	public static void TestRegistryV2_getManifest(){
		String url = "https://10.9.120.24/v2/busybox/manifests/v1";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		Map<String, String> headers = new HashMap<String, String>();
		if(!StringUtils.isEmpty(username)){
			headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
		}
		rs = tools.executeGetMethodHttps(url, null, headers);
		System.out.println(tools.getStrGetResponseBody()+"\n\r"+tools.getiGetResultCode());
		Header[] rheaders = tools.getRheaders();
		for(Header rh : rheaders){
			System.out.println(rh.getName()+"--"+rh.getValue());
		}
	}
	
	/**
	 * {"name":"centos","tags":["base","test"]}
	 * {"name":"registry","tags":["2"]}
	 * {"name":"phpnginx","tags":["test"]}
	 * {"name":"officalcentos","tags":["6"]}
	 * {"name":"centostest","tags":["6"]}
	 * {"name":"centosjdk1.6","tags":["v1"]}
	 * {"name":"centos2","tags":["6"]}
	 */
	public static void TestRegistryV2_getTag(){
		String url = "https://10.9.120.24/v2/busybox/tags/list";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		Map<String, String> headers = new HashMap<String, String>();
		if(!StringUtils.isEmpty(username)){
			headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
		}
		rs = tools.executeGetMethodHttps(url, null, headers);
		System.out.println(tools.getStrGetResponseBody());
		//{"name":"phpnginx","tags":["test"]}
		Header[] rheaders = tools.getRheaders();
		for(Header rh : rheaders){
			System.out.println(rh.getName()+"--"+rh.getValue());
		}
	}
	
	//["centos","nginxtest","registry","tengine"]
	public static void TestRegistryV2(){
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		if(url_V2_metis.startsWith("https")){	//https协议
			Map<String, String> headers = new HashMap<String, String>();
			if(!StringUtils.isEmpty(username)){
				headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
			}
			rs = tools.executeGetMethodHttps(url2, null, headers);
		}else{
			rs = tools.executeGetMethod(url2, null);
		}
		System.out.println(tools.getStrGetResponseBody());
//		if(rs){
//			String message = tools.getStrGetResponseBody();
//			JSONObject json = JSONObject.fromObject(message);
//			JSONArray array = JSONArray.fromObject(json.getString("history")) ;
//			JSONObject v1CObj =JSONObject.fromObject(JSONObject.fromObject(array.get(0)).getString("v1Compatibility"));
//			System.out.println(v1CObj.getString("id") + "" +v1CObj.getString("created"));
//			try {
//				Date date = new SimpleDateFormat(DATE_PATTERN).parse(v1CObj.getString("created"));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		if(rs){
			String message = tools.getStrGetResponseBody();
			System.out.println(message + "\n\r"+tools.getiGetResultCode());
//			RegistrySearchRepoData rsrd = JSONUtil.jsonToBean(message, RegistrySearchRepoData.class);
//			System.out.println(rsrd.getRepositories().size());
		}	
	}
	
   public static void TestRegistryV2_Del(){
	   //sha256:a9cd00d35e41b9cb51768e599d42dd4f84a291f9cade66325f12c9476467d02d
//	   String url_del = "https://10.9.120.24/v2/aether/spe/manifests/60";
	   String url_del = "https://10.9.120.24/v2/cadvisor/manifests/latest";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		if(url_del.startsWith("https")){	//https协议
			Map<String, String> headers = new HashMap<String, String>();
			if(!StringUtils.isEmpty(username)){
				headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
			}
			rs = tools.executeDeleteMethodHttps(url_del, null, headers);
		}else{
			rs = tools.executeDeleteMethod(url_del, null);
		}
		System.out.println(tools.getStrGetResponseBody());
		if(rs){
			String message = tools.getStrGetResponseBody();
			System.out.println(message);
		}
   }
	
	
	public static void  TestRegistryV1(){
		String username = "ops";
		String password = "wowotuan";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		Map<String,String> params = new HashMap<String,String>();
		params.put("q", "aether");
		String paramStr = null;
		StringBuilder bui = new StringBuilder();
		if(null != params){
			Iterator<String> it = params.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = params.get(key);
				bui.append(key).append("=").append(value).append("&");
			}
			String tmp = bui.toString();
			if(tmp.length() > 0){
				paramStr = tmp.substring(0, tmp.length() - 1);
			}
		}
		boolean rs = false;
		if(url.startsWith("https")){	//https协议
			Map<String, String> headers = new HashMap<String, String>();
			if(!StringUtils.isEmpty(username)){
				headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
			}
			rs = tools.executeGetMethodHttps(url, paramStr, headers);
		}else{
			rs = tools.executeGetMethod(url, paramStr);
		}
		
		if(rs){
			String message = tools.getStrGetResponseBody();
			System.out.println(message);
		}	
	}
	
}

