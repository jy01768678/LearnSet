package com.lorin.tinyioc.beans.aop;

/**
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
