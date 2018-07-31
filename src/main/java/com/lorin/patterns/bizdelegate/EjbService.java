package com.lorin.patterns.bizdelegate;

/**
 * Created by lorin on 2018/4/21.
 */
public class EjbService implements BusinessService {
    @Override
    public void doProcessing() {
        System.out.println("EjbService is now processing");
    }
}
