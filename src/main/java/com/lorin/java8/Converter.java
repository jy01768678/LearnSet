package com.lorin.java8;

/**
 * Created by taodong on 16/1/30.
 */

@FunctionalInterface
interface Converter<F,T> {
    T convert(F from);
}
