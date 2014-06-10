package com.lorin.tinyioc.beans.aop;

/**
 */
public interface ClassFilter {

    boolean matches(Class targetClass);
}
