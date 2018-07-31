package com.lorin.patterns.asyncinvocation;

import java.util.concurrent.ExecutionException;

/**
 * Created by lorin on 2018/4/21.
 */
public interface AsyncResult<T> {

    boolean isCompleted();

    T getValue() throws ExecutionException;

    void await() throws InterruptedException;
}
