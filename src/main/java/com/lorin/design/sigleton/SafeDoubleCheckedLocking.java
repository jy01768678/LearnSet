package com.lorin.design.sigleton;

/**
 * Created by taodong on 16/4/7.
 */
public class SafeDoubleCheckedLocking {
    private volatile static SafeDoubleCheckedLocking instance;

    public static SafeDoubleCheckedLocking getInstance(){
        if(instance == null){
            synchronized (SafeDoubleCheckedLocking.class){
                if (instance == null){
                    instance = new SafeDoubleCheckedLocking();
                }
            }
        }
        return instance;
    }
}
