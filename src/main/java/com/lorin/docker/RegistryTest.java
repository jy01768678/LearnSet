package com.lorin.docker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	
	public static void main(String[] args) {
//		TestRegistryV2_delBlob();
//		TestRegistryV2_getBlob();
//		TestRegistryV2_delManifest();
//		TestRegistryV2_getManifest();//获取docker-content-digest
//		TestRegistryV2_getTag(); // ok
		TestRegistryV2(); //ok
//		TestRegistryV2_Del();
//		TestRegistryV1();
	}
	
	public static void TestRegistryV2_delBlob(){
		String username = "wowotuanops";
		String password = "registrywowotuan";
		String url = "https://10.9.120.24/v2/officalcentos/blobs/sha256:296d74605c033deed84fefbe2c259f7bafbb906cc2d021c096727edcc9328920";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		Map<String, String> headers = new HashMap<String, String>();
		if(!StringUtils.isEmpty(username)){
			headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
		}
		rs = tools.executeDeleteMethodHttps(url, null, headers);
		System.out.println(tools.getStrGetResponseBody());
	}
	
	public static void TestRegistryV2_getBlob(){
		String username = "wowotuanops";
		String password = "registrywowotuan";
		String url = "https://10.9.120.24/v2/centos/blobs/sha256:fa4007ccc57768b8721ce4a18d889fd70c1b5feaf72a11981dc714594ab9fde3";
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
		String username = "wowotuanops";
		String password = "registrywowotuan";
		String url = "https://10.9.120.24/v2/centos/manifests/sha256:fa4007ccc57768b8721ce4a18d889fd70c1b5feaf72a11981dc714594ab9fde3";
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
		String username = "wowotuanops";
		String password = "registrywowotuan";
		String url = "https://10.9.120.24/v2/centos/manifests/test";
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean rs = false;
		Map<String, String> headers = new HashMap<String, String>();
		if(!StringUtils.isEmpty(username)){
			headers.put("Authorization", "Basic " + new Base64Encoder().encode((username + ":" + password).getBytes()));
		}
		rs = tools.executeGetMethodHttps(url, null, headers);
		System.out.println(tools.getStrGetResponseBody());
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
		String username = "wowotuanops";
		String password = "registrywowotuan";
		String url = "https://10.9.120.24/v2/centos/tags/list";
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
	
	//["centos","centos2","centosjdk1.6","centostest","officalcentos","phpnginx","registry"]
	public static void TestRegistryV2(){
		String username = "wowotuanops";
		String password = "registrywowotuan";
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
			RegistrySearchRepoData rsrd = JSONUtil.jsonToBean(message, RegistrySearchRepoData.class);
			System.out.println(rsrd.getRepositories().size());
		}	
	}
	
   public static void TestRegistryV2_Del(){
	   //sha256:a9cd00d35e41b9cb51768e599d42dd4f84a291f9cade66325f12c9476467d02d
//	   String url_del = "https://10.9.120.24/v2/aether/spe/manifests/60";
	   String url_del = "https://10.9.120.24/v2/cadvisor/manifests/latest";
	   String username = "wowotuanops";
		String password = "registrywowotuan";
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

