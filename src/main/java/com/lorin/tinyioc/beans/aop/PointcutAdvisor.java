package com.lorin.tinyioc.beans.aop;

/**
 */
public interface PointcutAdvisor extends Advisor{

   Pointcut getPointcut();
}
