package com.lorin.httpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class test {

    public static void main(String[] args) throws IOException {
        int code = 0;
        String body = null;
        String rmsg = null;
        int rcode = 0;
//        try {
//            HttpClient hc = new HttpClient();
//            hc.setConnTimeOut(10000);// 最多等10秒
//            hc.setEncode("UTF8");
//            String sUrl = "http://zhan.renren.com/meishike/3602888497996813697/share";
//            HttpClient.HttpResponse hr = hc.Post(sUrl, "ugcId=3602888497996813697&comment=" +
//            		"很喜欢吃以前&sync=true&_rtk=474bef1b&tag=美食&syncDouban=false&syncSina=false&syncQqweibo=false");
//            code = hr.getCode();
//            body = hr.Body();
//            rcode = hr.ResponseCode();
//            rmsg = hr.ResponseMsg();
//
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
        // for debug
        String test = "http://file11.mafengwo.net/M00/34/60/wKgBpU4mShPKpTkCAAGRs_yOQ7U22.s_weng.w460.jpeg";
        String test2 = "http://shopfs.myoppo.com/uploads/images/finder-alpha.png";
        URL tranUrl = new URL(test);
        URLConnection test3 = tranUrl.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection)test3;  
        int responseCode = httpConnection.getResponseCode();  
        System.out.println("test = " + responseCode);
//        System.out.println("Response Code = " + code);
//        System.out.println("Body = " + body);
//        System.out.println("rcode = " + rcode);
//        System.out.println("rmsg = " + rmsg);
    }
}
