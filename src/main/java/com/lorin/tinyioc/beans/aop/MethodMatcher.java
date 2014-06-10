package com.lorin.tinyioc.beans.aop;

import java.lang.reflect.Method;

/**
 */
public interface MethodMatcher {

    boolean matches(Method method, Class targetClass);
}
