package com.lorin.httpClient;

import java.io.IOException;
import java.net.CookieStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class ClientCustomContext {

    /**
     * @param args
     */
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        HttpClient httpClient = new DefaultHttpClient();
//        try{
//            CookieStore cookieStore = new BasicCookieStore();
//            HttpContext httpContext = new BasicHttpContext();
//
//            httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
//
//            HttpGet httpget = new HttpGet("http://test1.touch.renren.com/?sid=9l7KqLwUTBV63w7Zt3y9Ov");
//
//            System.out.println("executing request " + httpget.getURI());
//            httpClient.getParams().setIntParameter("http.socket.timeout", 1000);
//            HttpResponse httpResponse = httpClient.execute(httpget, httpContext);
//            HttpEntity entity = httpResponse.getEntity();
//
//            System.out.println("----------------------------------------");
//            System.out.println(httpResponse.getStatusLine());
//            if (entity != null) {
//                System.out.println(EntityUtils.toString(entity));
//            }
////            List<Cookie> cookies = cookieStore.getCookies();
////            for (int i = 0; i < cookies.size(); i++) {
////                System.out.println("Local cookie: " + cookies.get(i));
////            }
//
//            // Consume response content
//            EntityUtils.consume(entity);
//
//            System.out.println("----------------------------------------");
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//            httpClient.getConnectionManager().shutdown();
//        }
//    }

}
