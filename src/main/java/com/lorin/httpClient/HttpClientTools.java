package com.lorin.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author wanghongwei
 * @since 17.01.2012
 */
public class HttpClientTools {
	private static Logger logger = LoggerFactory.getLogger(HttpClientTools.class);
	private int iGetResultCode;
	private String strGetResponseBody;
	private Header rheaders[];
	private String errorInfo;
	/** HttpClient */
	private HttpClient httpclient;
	
	private String HTTP_CONTENT_DEFAULT_CHARSET = "UTF-8";

	public HttpClientTools(MultiThreadedHttpConnectionManager connectionManager) {
		httpclient = new HttpClient(connectionManager);
	}

	public boolean executeGetMethod(String url, String param) {
		return executeGetMethod(url, param, null);
	}
	
	public boolean executeGetMethod(String url, String param, Map<String, String> headers) {
		if (url == null || url.length() <= 0) {
			errorInfo = "url为空值";
			return false;
		}
		StringBuffer serverURL = new StringBuffer(url);
		if (param != null && param.length() > 0) {
			serverURL.append("?");
			serverURL.append(param);
		}
		// System.out.println("serverURL=" + serverURL);
		GetMethod httpget = new GetMethod(serverURL.toString());
		httpget.setFollowRedirects(true);
		//设置请求头
		if(null != headers && !headers.isEmpty()){
			Iterator<String> it = headers.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = headers.get(key);
				httpget.setRequestHeader(key, value);
			}
		}
		try {
			iGetResultCode = httpclient.executeMethod(httpget);
			strGetResponseBody = httpget.getResponseBodyAsString();
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			httpget.releaseConnection();
		}
		return false;
	}
	
	public boolean executeGetMethodReStream(String url, String param, Map<String, String> headers) {
		if (url == null || url.length() <= 0) {
			errorInfo = "url为空值";
			return false;
		}
		StringBuffer serverURL = new StringBuffer(url);
		if (param != null && param.length() > 0) {
			serverURL.append("?");
			serverURL.append(param);
		}
		// System.out.println("serverURL=" + serverURL);
		GetMethod httpget = new GetMethod(serverURL.toString());
		httpget.setFollowRedirects(true);
		//设置请求头
		if(null != headers && !headers.isEmpty()){
			Iterator<String> it = headers.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = headers.get(key);
				httpget.setRequestHeader(key, value);
			}
		}
		try {
			iGetResultCode = httpclient.executeMethod(httpget);
			 httpget.getResponseBodyAsStream();
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			httpget.releaseConnection();
		}
		return false;
	}
	
	/**
	 * 基于https的Get请求
	 * @param url	请求地址
	 * @param param	请求参数
	 * @param headers	请求头
	 * @return
	 * @author mahaiyuan@55tuan.com
	 * @since 2015年6月3日上午10:13:58
	 */
	public boolean executeGetMethodHttps(String url, String param, Map<String, String> headers) {
		if (url == null || url.length() <= 0) {
			errorInfo = "url为空值";
			return false;
		}
		StringBuffer serverURL = new StringBuffer(url);
		if (param != null && param.length() > 0) {
			serverURL.append("?");
			serverURL.append(param);
		}
		Protocol myhttps = new Protocol("https", new SimpleSSLProtocolSocketFactory(), 443); 
		Protocol.registerProtocol("https", myhttps); 
		GetMethod httpget = new GetMethod(serverURL.toString());
		if(null != headers){
			Iterator<String> it = headers.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = headers.get(key);
				httpget.setRequestHeader(key, value);
			}
		}
		httpget.setFollowRedirects(true);
		try {
			iGetResultCode = httpclient.executeMethod(httpget);
			strGetResponseBody = httpget.getResponseBodyAsString();
			rheaders= httpget.getResponseHeaders();
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			httpget.releaseConnection();
		}
		return false;
	}
	
	/**
	 * Http DELETE方法
	 * @param url	请求地址
	 * @param param	请求参数
	 * @return
	 * @author mahaiyuan@55tuan.com
	 * @since 2015年6月11日下午1:32:21
	 */
	public boolean executeDeleteMethod(String url, String param) {
		if (url == null || url.length() <= 0) {
			errorInfo = "url为空值";
			return false;
		}
		StringBuffer serverURL = new StringBuffer(url);
		if (param != null && param.length() > 0) {
			serverURL.append("?");
			serverURL.append(param);
		}
		DeleteMethod httpdelete = new DeleteMethod(serverURL.toString());
		httpdelete.setFollowRedirects(true);
		try {
			iGetResultCode = httpclient.executeMethod(httpdelete);
			strGetResponseBody = httpdelete.getResponseBodyAsString();
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			httpdelete.releaseConnection();
		}
		return false;
	}
	
	/**
	 * 基于https的Get请求
	 * @param url	请求地址
	 * @param param	请求参数
	 * @param headers	请求头
	 * @return
	 * @author mahaiyuan@55tuan.com
	 * @since 2015年6月3日上午10:13:58
	 */
	public boolean executeDeleteMethodHttps(String url, String param, Map<String, String> headers) {
		if (url == null || url.length() <= 0) {
			errorInfo = "url为空值";
			return false;
		}
		StringBuffer serverURL = new StringBuffer(url);
		if (param != null && param.length() > 0) {
			serverURL.append("?");
			serverURL.append(param);
		}
		Protocol myhttps = new Protocol("https", new SimpleSSLProtocolSocketFactory(), 443); 
		Protocol.registerProtocol("https", myhttps); 
		DeleteMethod httpdelete = new DeleteMethod(serverURL.toString());
		if(null != headers){
			Iterator<String> it = headers.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = headers.get(key);
				httpdelete.setRequestHeader(key, value);
			}
		}
		httpdelete.setFollowRedirects(true);
		try {
			iGetResultCode = httpclient.executeMethod(httpdelete);
			strGetResponseBody = httpdelete.getResponseBodyAsString();
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			httpdelete.releaseConnection();
		}
		return false;
	}
	/**
	 * HTTP PUT 方法
	 * @param url
	 * @return
	 * @author mahaiyuan@55tuan.com
	 * @since 2015年6月25日下午5:37:14
	 */
	public boolean executePutMethod(String strURL, String postBody, String contentCharSet,
			String contentType) {
		if (strURL == null || strURL.length() <= 0) {
			return false;
		}

		PutMethod put = new PutMethod(strURL);
		try {
			contentCharSet = contentCharSet==null?HTTP_CONTENT_DEFAULT_CHARSET:contentCharSet;
			put.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharSet);
			put.setRequestHeader("Content-type", contentType);
			put.setRequestEntity(new StringRequestEntity(postBody,
					contentType, null));

			iGetResultCode = httpclient.executeMethod(put);
			strGetResponseBody = put.getResponseBodyAsString();
			//logger.info(new String(strGetResponseBody));
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = strURL + "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = strURL + "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			put.releaseConnection();
		}
		return false;
	}
	
	private static byte[] getTextEntry(String fieldName, String fieldValue, String charset)
			throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private static byte[] getFileEntry(String fieldName, String fileName, String mimeType,
			String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}

	public boolean executePostMethod(String strURL, Map<String, String> param,
			String contentType) {
		if (strURL == null || strURL.length() <= 0) {
			errorInfo = "URL为空值";
			return false;
		}

		PostMethod post = new PostMethod(strURL);
		NameValuePair[] data = new NameValuePair[param.size()];
		int i = 0;
		for (Entry<String, String> set : param.entrySet()) {
			if(null != set.getValue()){
				data[i++] = new NameValuePair(set.getKey(), set.getValue()
						.toString());
			}
		}
		post.setRequestBody(data);
		return executePost(post,strURL,contentType);
	}
	private boolean executePost(PostMethod post,String strURL,String contentType){
		try {
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, contentType);
			post.setRequestHeader("Content-type", contentType);

			iGetResultCode = httpclient.executeMethod(post);
			strGetResponseBody = post.getResponseBodyAsString();
			logger.info(new String(strGetResponseBody));
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = strURL + "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = strURL + "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			post.releaseConnection();
		}
		return false;
	}
	public boolean executePostMethod(String strURL, String postBody,String contentCharSet,
			String contentType) {
		if (strURL == null || strURL.length() <= 0) {
			return false;
		}

		PostMethod post = new PostMethod(strURL);
		try {
			contentCharSet = contentCharSet==null?HTTP_CONTENT_DEFAULT_CHARSET:contentCharSet;
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharSet);
			post.setRequestHeader("Content-type", contentType);
			post.setRequestEntity(new StringRequestEntity(postBody,
					contentType, null));

			iGetResultCode = httpclient.executeMethod(post);
			strGetResponseBody = post.getResponseBodyAsString();
			//logger.info(new String(strGetResponseBody));
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = strURL + "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = strURL + "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			post.releaseConnection();
		}
		return false;
	}
	
	public boolean executePostMethodForSalt(String strURL, String postBody,String contentCharSet,
			String contentType) {
		if (strURL == null || strURL.length() <= 0) {
			return false;
		}

		PostMethod post = new PostMethod(strURL);
		try {
			contentCharSet = contentCharSet==null?HTTP_CONTENT_DEFAULT_CHARSET:contentCharSet;
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharSet);
			post.setRequestHeader("Content-type", contentType);
			post.setRequestHeader("ACCEPT", "application/json");
			post.setRequestEntity(new StringRequestEntity(postBody,contentType, null));
			
			Protocol myhttps = new Protocol("https", new SimpleSSLProtocolSocketFactory(), 443); 
			Protocol.registerProtocol("https", myhttps); 

			iGetResultCode = httpclient.executeMethod(post);
			strGetResponseBody = post.getResponseBodyAsString();
			//logger.info(new String(strGetResponseBody));
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = strURL + "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = strURL + "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			post.releaseConnection();
		}
		return false;
	}
	
	/** 
	* @Description:  忽略证书的http post 请求
	* @param strURL
	* @param postBody
	* @param contentCharSet
	* @param contentType
	* @return boolean
	* @Date 2015年8月5日 下午3:40:05
	* @auther zhangyi 
	*/
	public boolean executePostMethodIngoreCert(String strURL, String postBody,String contentCharSet,String contentType) {
		if (strURL == null || strURL.length() <= 0) {
			return false;
		}

		PostMethod post = new PostMethod(strURL);
		try {
			contentCharSet = contentCharSet==null?HTTP_CONTENT_DEFAULT_CHARSET:contentCharSet;
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharSet);
			post.setRequestHeader("Content-type", contentType);
			post.setRequestEntity(new StringRequestEntity(postBody,contentType, null));
			
			Protocol myhttps = new Protocol("https", new SimpleSSLProtocolSocketFactory(), 443); 
			Protocol.registerProtocol("https", myhttps); 
			
			iGetResultCode = httpclient.executeMethod(post);
			strGetResponseBody = post.getResponseBodyAsString();
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = strURL + "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = strURL + "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			post.releaseConnection();
		}
		return false;
	}
	
	public boolean executePostMethodStream(String strURL, String postBody,String contentCharSet,
			String contentType) {
		if (strURL == null || strURL.length() <= 0) {
			return false;
		}

		PostMethod post = new PostMethod(strURL);
		try {
			contentCharSet = contentCharSet==null?HTTP_CONTENT_DEFAULT_CHARSET:contentCharSet;
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharSet);
			post.setRequestHeader("Content-type", contentType);
			post.setRequestEntity(new StringRequestEntity(postBody,
					contentType, null));

			iGetResultCode = httpclient.executeMethod(post);
			InputStream is = post.getResponseBodyAsStream();
			 BufferedReader br = new BufferedReader(new InputStreamReader(is));   
		        StringBuffer stringBuffer = new StringBuffer();   
		        String str= "";   
		        while((str = br.readLine()) != null){   
		            stringBuffer .append(str );   
		        }   
		   System.out.println("ResponseBody:\n" + stringBuffer.toString());
			//logger.info(new String(strGetResponseBody));
			if (iGetResultCode >= 200 && iGetResultCode < 303) {
				return true;
			} else if (iGetResultCode >= 400 && iGetResultCode < 500) {
				errorInfo = strURL + "请求资源不存在或内部错误" + iGetResultCode;
			} else {
				errorInfo = strURL + "服务器出错" + iGetResultCode;
			}
		} catch (Exception ex) {
			errorInfo = ex.getMessage();
		} finally {
			post.releaseConnection();
		}
		return false;
	}
	

	
	public boolean executePostMethod(String strURL, String postBody,
			String contentType) {
		return  executePostMethod( strURL,  postBody, HTTP_CONTENT_DEFAULT_CHARSET,
				 contentType);
	}
	
	public boolean executePostMethodForSalt(String strURL, String postBody,
			String contentType) {
		return  executePostMethodForSalt( strURL,  postBody, HTTP_CONTENT_DEFAULT_CHARSET,
				 contentType);
	}
	
	public boolean executePostMethodStream(String strURL, String postBody,
			String contentType) {
		return  executePostMethodStream( strURL,  postBody, HTTP_CONTENT_DEFAULT_CHARSET,
				 contentType);
	}
	

	public int getiGetResultCode() {
		return iGetResultCode;
	}

	public String getStrGetResponseBody() {
		return strGetResponseBody;
	}

	public String getErrorInfo() {
		return errorInfo;
	}
	
	public Header[] getRheaders() {
		return rheaders;
	}

	public boolean executePutMethod(String strURL, String postBody,String contentType) {
		return  executePutMethod( strURL,  postBody, HTTP_CONTENT_DEFAULT_CHARSET,contentType);
	}

}
