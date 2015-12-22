package com.lorin.design.template;


import com.lorin.httpClient.HttpClientManager;
import com.lorin.httpClient.HttpClientTools;


public class SkylarkRouterTemplate implements PhpRouterOperations{

    public static String PHP_ROUTER_ADDRESS;

    public static final String CONTENT_TYPE= "application/x-www-form-urlencoded";

    public static final String ENCODE_KEY = "GgVtm7p9dVoyAdc";

    public <T> T execute(RouterStatmentCallback<T> callback,String requestType){
        HttpClientTools tools = HttpClientManager.getHttpClientTools();
        return callback.doInPhpClient(tools);
    }


    @Override
    public Object getWikiCategoryList(final String method, final int level, final int category_id) {
        this.execute(new RouterStatmentCallback<String>() {

            @Override
            public String doInPhpClient(HttpClientTools tools) {
                return null;
            }
        },"POST");
        return null;
    }

    @Override
    public Object getWikiDetail(String method, int wikiId) {
        return null;
    }
}
