package com.lorin.docker;

import java.net.URLEncoder;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;


import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;

public class WeChatTest {
	
	public static void getAccessToken(){
		String appId = "wx9b3687cc2ed31940";
		String appserect = "3add3cf75a377bcbe163a5a3371f7893";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appserect;
		HttpClientTools tools = HttpClientManager.getHttpClientTools();
		boolean result = tools.executeGetMethodHttps(url, null, null);
		WeChatAccessToken wat = null;
//		if(result){
//			String content = tools.getStrGetResponseBody();
//			wat = (WeChatAccessToken) JSONObject.toBean(JSONObject.fromObject(content), WeChatAccessToken.class);
//			System.out.println(wat.getAccess_token()+"\n\r"+wat.getExpires_in());
//		}
		
	}
	
	public static void sigtureTest(){
		String token = "jmww20151016";
		String signature = "6325811e2b94b088a1f1719d99b3ee7bbdcd7223";
		String timestamp ="1445310245"; 
		String nonce = "107170785";
		String[] param = new String[]{token, timestamp, nonce};
		Arrays.sort(param);
		String encrypt = DigestUtils.shaHex(StringUtils.join(param));
		if (encrypt.equals(signature)) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}
	
	public static void generateOAuthUrl(){
		StringBuffer url = new StringBuffer("");
		url.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b3687cc2ed31940");
		url.append("&redirect_uri="+URLEncoder.encode("http://118.26.73.44/jmwwserver/wechat/oauth.do"));
		url.append("&response_type=code");
		url.append("&scope=snsapi_base");
		url.append("&state=1");
		url.append("#wechat_redirect");
		System.out.println(url.toString());
	}

	public static void main(String[] args) {
		generateOAuthUrl();
//		sigtureTest();
//		getAccessToken();
//		String result = "{\"access_token\":\"ACCESS_TOKEN\", \"expires_in\":7200,\"refresh_token\":\"REFRESH_TOKEN\",\"openid\":\"OPENID\",\"scope\":\"SCOPE\", \"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"}";
//		WeChatAccessToken wat = null;
//		wat = (WeChatAccessToken) JSONObject.toBean(JSONObject.fromObject(result), WeChatAccessToken.class);
//		System.out.println(wat.getAccess_token()+"\n\r"+wat.getExpires_in()+"\n"+wat.getOpenid()+"\n"+wat.getErrcode());
	}
	
}
