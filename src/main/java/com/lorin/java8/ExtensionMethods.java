package com.lorin.java8;

/**
 * Created by taodong on 16/1/30.
 */
public interface ExtensionMethods {
    double calculate(int a);
    default double sqrt(int a){
        return Math.sqrt(a);
    }
}
