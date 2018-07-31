package com.lorin.design.sigleton;

/**
 * Created by lorin on 2018/5/14.
 */
public class InstanceStaticFactory {
    //initailzed during class loading
    private static final InstanceStaticFactory INSTANCE = new InstanceStaticFactory();

    //to prevent creating another instance of Singleton
    private InstanceStaticFactory() {
    }

    public static InstanceStaticFactory getSingleton() {
        return INSTANCE;
    }
}
