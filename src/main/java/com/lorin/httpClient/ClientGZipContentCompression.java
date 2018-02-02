package com.lorin.httpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


public class ClientGZipContentCompression {

    /**
     * @param args
     */
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        try {
//            httpclient.addRequestInterceptor(new HttpRequestInterceptor(){
//                @Override
//                public void process(HttpRequest request, HttpContext context) throws HttpException,
//                        IOException {
//                    if(!request.containsHeader("Accept-Encoding")){
//                        request.addHeader("Accept-Encoding", "gzip");
//                    }
//                }
//            });
//
//            httpclient.addResponseInterceptor(new HttpResponseInterceptor(){
//                @Override
//                public void process(HttpResponse response, HttpContext context) throws HttpException,
//                        IOException {
//                    HttpEntity entity = response.getEntity();
//                    Header cacherHeader = entity.getContentEncoding();
//                    if(cacherHeader != null){
//                        HeaderElement[] elements = cacherHeader.getElements();
//                        for (int i = 0; i < elements.length; i++) {
//                            if (elements[i].getName().equalsIgnoreCase("gzip")) {
//                                response.setEntity(
//                                        new GzipDecompressingEntity(response.getEntity()));
//                                return;
//                            }
//                        }
//                    }
//                }
//            });
//
//            HttpGet httpget = new HttpGet("http://www.apache.org/");
//
//            // Execute HTTP request
//            System.out.println("executing request " + httpget.getURI());
//            HttpResponse response = httpclient.execute(httpget);
//
//            System.out.println("----------------------------------------");
//            System.out.println(response.getStatusLine());
//            System.out.println(response.getLastHeader("Content-Encoding"));
//            System.out.println(response.getLastHeader("Content-Length"));
//            System.out.println("----------------------------------------");
//
//            HttpEntity entity = response.getEntity();
//
//            if (entity != null) {
//                String content = EntityUtils.toString(entity);
//                System.out.println(content);
//                System.out.println("----------------------------------------");
//                System.out.println("Uncompressed size: "+content.length());
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//            httpclient.getConnectionManager().shutdown();
//        }
//    }
//
//    static class GzipDecompressingEntity extends HttpEntityWrapper {
//
//        public GzipDecompressingEntity(final HttpEntity entity) {
//            super(entity);
//        }
//
//        @Override
//        public InputStream getContent()
//            throws IOException, IllegalStateException {
//            // the wrapped entity's getContent() decides about repeatability
//            InputStream wrappedin = wrappedEntity.getContent();
//            return new GZIPInputStream(wrappedin);
//        }
//
//        @Override
//        public long getContentLength() {
//            return -1;
//        }
//
//    }
}
