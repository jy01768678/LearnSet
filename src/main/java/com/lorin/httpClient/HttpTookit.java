package com.lorin.httpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient; 
import org.apache.commons.httpclient.HttpMethod; 
import org.apache.commons.httpclient.HttpStatus; 
import org.apache.commons.httpclient.URIException; 
import org.apache.commons.httpclient.methods.GetMethod; 
import org.apache.commons.httpclient.methods.PostMethod; 
import org.apache.commons.httpclient.params.HttpMethodParams; 
import org.apache.commons.httpclient.util.URIUtil; 
import org.apache.commons.lang.StringUtils;


public class HttpTookit {
    /** 
     * 执行一个HTTP GET请求，返回请求响应的HTML 
     * 
     * @param url                 请求的URL地址 
     * @param queryString 请求的查询参数,可以为null 
     * @return 返回请求响应的HTML 
     */ 
    public static String doGet(String url, String queryString) { 
            String response = null; 
            HttpClient client = new HttpClient(); 
            HttpMethod method = new GetMethod(url); 
            try { 
                    if (StringUtils.isNotBlank(queryString)) 
                            method.setQueryString(URIUtil.encodeQuery(queryString)); 
                    client.executeMethod(method); 
                    if (method.getStatusCode() == HttpStatus.SC_OK) { 
                            response = method.getResponseBodyAsString(); 
                    } 
            } catch (URIException e) { 
                    //log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e); 
            } catch (IOException e) { 
                   // log.error("执行HTTP Get请求" + url + "时，发生异常！", e); 
            } finally { 
                    method.releaseConnection(); 
            } 
            return response; 
    } 

    /** 
     * 执行一个HTTP POST请求，返回请求响应的HTML 
     * 
     * @param url        请求的URL地址 
     * @param params 请求的查询参数,可以为null 
     * @return 返回请求响应的HTML 
     */ 
    public static String doPost(String url, Map<String, String> params) { 
            String response = null; 
            HttpClient client = new HttpClient(); 
            HttpMethod method = new PostMethod(url); 
            for (Iterator it = params.entrySet().iterator(); it.hasNext();) { 

            } 
            //设置Http Post数据 
            if (params != null) { 
                    HttpMethodParams p = new HttpMethodParams(); 
                    for (Map.Entry<String, String> entry : params.entrySet()) { 
                            p.setParameter(entry.getKey(), entry.getValue()); 
                    } 
                    method.setParams(p); 
            } 
            try { 
                    client.executeMethod(method); 
                    if (method.getStatusCode() == HttpStatus.SC_OK) { 
                            response = method.getResponseBodyAsString(); 
                    } 
            } catch (IOException e) { 
                    //log.error("执行HTTP Post请求" + url + "时，发生异常！", e); 
            } finally { 
                    method.releaseConnection(); 
            } 

            return response; 
    } 

    public static void main(String[] args) { 
            HashMap map = new HashMap();
            map.put("ugcId", "3602888497996813697");
            map.put("comment", "以前很喜欢吃 ");
            map.put("syncDouban", false);
            map.put("syncSina", false);
            map.put("syncQqweibo", false);
            map.put("sync", true);
            map.put("_rtk", "474bcf1b");
            map.put("tag", "美食");
            String x = doPost("http://zhan.renren.com/meishike/3602888497996813697/share", map); 
            System.out.println(x); 
    } 
}
