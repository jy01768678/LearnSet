package com.lorin.docker;

import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;
import com.thoughtworks.xstream.core.util.Base64Encoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taodong on 15/11/14.
 */
public class SkyLarkTest {

    public static String url = "http://skylark.htp.me/router/rest";

    public static void main(String[] args) {
        getWiki();
    }


    public static void getWiki(){
        HttpClientTools tools = HttpClientManager.getHttpClientTools();
        Map<String, String> param = new HashMap<String, String>();
        param.put("method","skylark.wiki.categorys");
        //application/json  text/plain
        boolean rs = tools.executePostMethod(url,"method=skylark.wiki.categorys","application/x-www-form-urlencoded");
        System.out.println("结果:" + tools.getStrGetResponseBody());
    }
}
