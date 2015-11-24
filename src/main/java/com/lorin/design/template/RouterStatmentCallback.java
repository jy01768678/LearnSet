package com.lorin.design.template;


import com.lorin.httpClient.HttpClientTools;

/**
 * Created by taodong on 15/11/24.
 */
public interface RouterStatmentCallback<T> {
    T doInPhpClient(HttpClientTools tools);
}
