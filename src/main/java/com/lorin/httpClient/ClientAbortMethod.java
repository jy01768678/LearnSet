package com.lorin.httpClient;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;


public class ClientAbortMethod {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        HttpClient httpClient = new DefaultHttpClient();
        try{
            HttpGet httpGet = new HttpGet("http://www.apache.org/");
            System.out.println("executing request " + httpGet.getURI());
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
            }
            System.out.println("----------------------------------------");

            // Do not feel like reading the response body
            // Call abort on the request object
            httpGet.abort();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
         // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpClient.getConnectionManager().shutdown();
        }
    }

}
