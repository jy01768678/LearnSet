package com.lorin.design.template;


import com.lorin.httpClient.HttpClientTools;


public interface RouterStatmentCallback<T> {
    T doInPhpClient(HttpClientTools tools);
}
