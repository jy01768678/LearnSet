package com.lorin.design.sigleton;

/**
 * Created by taodong on 16/4/7.
 */
public class InstanceFactory {
    private static class InstanceHolder{
        public static InstanceFactory instanceFactory = new InstanceFactory();
    }

    public static InstanceFactory getInstance(){
        return InstanceHolder.instanceFactory;
    }
}
