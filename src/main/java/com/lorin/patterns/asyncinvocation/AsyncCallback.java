package com.lorin.patterns.asyncinvocation;

import java.util.Optional;

/**
 * Created by lorin on 2018/4/21.
 */
public interface AsyncCallback<T> {

    void onComplete(T value, Optional<Exception> ex);
}
