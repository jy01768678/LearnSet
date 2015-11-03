package com.lorin.docker;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;


/**
 * http 带ca证书访问工具类
 *
 */
public class HttpCertificateUtil {
	
	/** 
	*	通过输入流获取证书信息
	* @Date 2015年6月27日 下午3:12:34
	* @auther zhangyi 
	*/
	public static Certificate generateCertificate(InputStream inStream){
		if(null == inStream){
			throw new NullPointerException("inStream must all be specified");
		}
		Certificate cert = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			cert = cf.generateCertificate(inStream);
		} catch (CertificateException e) {
			throw new RuntimeException("Fialed to generate certificate");
		}
		return cert;
	}
	
	
	/** 
	* @Description: 根据流信息 获取 密钥 
	* @Date 2015年6月27日 下午3:42:24
	* @auther zhangyi 
	*/
	@SuppressWarnings("resource")
	public static PrivateKey generateClientKey(Reader reader){
		PrivateKey clientKey = null;
//		try {
//			PEMKeyPair clientKeyPair = (PEMKeyPair) new PEMParser(reader).readObject();
//			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(clientKeyPair.getPrivateKeyInfo().getEncoded());
//			KeyFactory kf = KeyFactory.getInstance("RSA");
//			clientKey = kf.generatePrivate(spec);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (InvalidKeySpecException e) {
//			e.printStackTrace();
//		}
		return clientKey;
	}
	
	
	/** 
	* @Description:  获取ssl 链接工厂
	* @param caCert ca认证中心
	* @param clientCert 客户端证书
	* @param clientKey  客户端密钥
	* @param password  保护密钥密码
	* @param protocol  协议
	* @Date 2015年6月27日 下午3:49:14
	* @auther zhangyi 
	*/
	@SuppressWarnings("deprecation")
	public static SSLConnectionSocketFactory getSSLConnectionSocketFactory(
			Certificate caCert, Certificate clientCert, PrivateKey clientKey,
			char[] password, String protocol) {
		SSLConnectionSocketFactory sslsf = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			// 用指定别名保存 keystore Entry
			trustStore.setEntry("ca", new KeyStore.TrustedCertificateEntry(caCert), null);

			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null, null);
			// 将给定可信证书分配给给定别名
			keyStore.setCertificateEntry("client", clientCert);
			// 将给定的密钥分配给给定的别名，并用给定密码保护它
			keyStore.setKeyEntry("key", clientKey, password,new Certificate[] { clientCert });

			SSLContext sslcontext = SSLContexts
					.custom()
					.loadTrustMaterial(trustStore,new TrustSelfSignedStrategy())// 加载认证中心
					.loadKeyMaterial(keyStore, password).useProtocol(protocol)// 加载密钥
					.build();

			// 只允许使用TLSv1协议
			HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();
			sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, hostnameVerifier);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return sslsf;
	}


	/** 
	* @Description:  发送Get请求
	* @param server  业务对象
	* @param url	请求url
	* @param password 密钥保护密码
	* @param protocol 协议
	* @param parammap 参数
	* @Date 2015年6月27日 下午4:38:12
	* @auther zhangyi 
	*/
	public static CloseableHttpResponse sendGetMethod(ServerNodeDO server,
			String url, char[] password, String protocol,Map<String, String> parammap) {
		if(null == server || null == url || "".equals(url)){
			return null;
		}
		CloseableHttpResponse response = null;
		String ca = server.getCaCertificate();
		String cert = server.getSslCertificate();
		String key = server.getSslKey();
		try {
			Certificate caCert = generateCertificate(new ByteArrayInputStream(ca.getBytes()));
			Certificate clientCert = generateCertificate(new ByteArrayInputStream(cert.getBytes()));
			PrivateKey clientKey = generateClientKey(new BufferedReader(new InputStreamReader(new
					 ByteArrayInputStream(key.getBytes()))));
			
			SSLConnectionSocketFactory sslsf = getSSLConnectionSocketFactory(
					caCert, clientCert, clientKey, password, protocol);

			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();
			
			String paramStr = null;
			StringBuilder sb = new StringBuilder();
			if(null != parammap && parammap.size() > 0){
				for(Entry<String, String> temp: parammap.entrySet()){
					if(url.contains("logs")){
						sb.append(temp.getKey()).append("=").append(temp.getValue()).append("&");
					}else{
						sb.append(temp.getKey()).append("=").append(URLEncoder.encode(temp.getValue(),"UTF-8")).append("&");
					}
				}
				String tmp = sb.toString();
				if(tmp.length() > 0){
					paramStr = tmp.substring(0, tmp.length() - 1);
					url = url + "?" +paramStr;
				}
			}
			HttpGet httpget = new HttpGet(url);
			// 发送请求
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}


	public static CloseableHttpResponse sendDeleteMethod(ServerNodeDO server, String url,
			char[] password, String protocol, Map<String, String> parammap) {
		if(null == server || null == url || "".equals(url)){
			return null;
		}
		CloseableHttpResponse response = null;
		String ca = server.getCaCertificate();
		String cert = server.getSslCertificate();
		String key = server.getSslKey();
		try {
			Certificate caCert = generateCertificate(new ByteArrayInputStream(ca.getBytes()));
			Certificate clientCert = generateCertificate(new ByteArrayInputStream(cert.getBytes()));
			PrivateKey clientKey = generateClientKey(new BufferedReader(new InputStreamReader(new
					 ByteArrayInputStream(key.getBytes()))));
			
			SSLConnectionSocketFactory sslsf = getSSLConnectionSocketFactory(
					caCert, clientCert, clientKey, password, protocol);

			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();
			
			String paramStr = null;
			StringBuilder sb = new StringBuilder();
			if(null != parammap && parammap.size() > 0){
				for(Entry<String, String> temp: parammap.entrySet()){
					sb.append(temp.getKey()).append("=").append(temp.getValue()).append("&");
				}
				String tmp = sb.toString();
				if(tmp.length() > 0){
					paramStr = tmp.substring(0, tmp.length() - 1);
					url = url + "?" +paramStr;
				}
			}
			
			HttpDelete httpDelete = new HttpDelete(url);
			// 发送请求
			response = httpclient.execute(httpDelete);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return response;
	}


	public static boolean sendPostMethod(ServerNodeDO server, String url,
			char[] password, String protocol, Map<String, String> parammap,
			Map<String, String> headermap) {
		if(null == server || null == url || "".equals(url)){
			return false;
		}
		CloseableHttpResponse response = null;
		String ca = server.getCaCertificate();
		String cert = server.getSslCertificate();
		String key = server.getSslKey();
		try {
			Certificate caCert = generateCertificate(new ByteArrayInputStream(ca.getBytes()));
			Certificate clientCert = generateCertificate(new ByteArrayInputStream(cert.getBytes()));
			PrivateKey clientKey = generateClientKey(new BufferedReader(new InputStreamReader(new
					 ByteArrayInputStream(key.getBytes()))));
			
			SSLConnectionSocketFactory sslsf = getSSLConnectionSocketFactory(
					caCert, clientCert, clientKey, password, protocol);

			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();
		
			HttpPost httpPost = new HttpPost(url);
			List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
			UrlEncodedFormEntity entity = null;
			if(null != parammap && parammap.size() >0){
				for(Entry<String, String> tmep : parammap.entrySet()){
					paramsList.add(new BasicNameValuePair(tmep.getKey(), tmep.getValue()));
				}
				entity = new UrlEncodedFormEntity(paramsList, "UTF-8");
			}
			httpPost.setEntity(entity);
			if(null != headermap && headermap.size() >0){
				for(Entry<String, String> tmep : headermap.entrySet()){
					httpPost.addHeader(tmep.getKey(), tmep.getValue());
				}
			}
			response =  httpclient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			if(code >= 200 && code < 303){
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(null != response){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		return false;
	}

	public static boolean sendPostMethodForSalt( String url,
			char[] password, String protocol, Map<String, String> parammap,
			Map<String, String> headermap) throws NoSuchAlgorithmException, KeyManagementException {
		CloseableHttpResponse response = null;
		try {
			SSLContext ctx = SSLContext.getInstance("SSL");
	        X509TrustManager tm = new X509TrustManager() {
	            @Override
	            public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
	            }
	 
	            @Override
	            public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
	            }
	 
	            @Override
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return new java.security.cert.X509Certificate[0];
	            }
	        };
	        ctx.init(null, new TrustManager[]{tm}, null);
	        HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();
	        SSLConnectionSocketFactory sslsf  = new SSLConnectionSocketFactory(ctx, new String[] { "" }, null, hostnameVerifier);

			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();
		
			HttpPost httpPost = new HttpPost(url);
			List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
			UrlEncodedFormEntity entity = null;
			if(null != parammap && parammap.size() >0){
				for(Entry<String, String> tmep : parammap.entrySet()){
					paramsList.add(new BasicNameValuePair(tmep.getKey(), tmep.getValue()));
				}
				entity = new UrlEncodedFormEntity(paramsList, "UTF-8");
			}
			httpPost.setEntity(entity);
			if(null != headermap && headermap.size() >0){
				for(Entry<String, String> tmep : headermap.entrySet()){
					httpPost.addHeader(tmep.getKey(), tmep.getValue());
				}
			}
			response =  httpclient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			if(code >= 200 && code < 303){
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(null != response){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		return false;
	}
}


