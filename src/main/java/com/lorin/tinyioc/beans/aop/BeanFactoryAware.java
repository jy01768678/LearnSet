package com.lorin.tinyioc.beans.aop;

import com.lorin.tinyioc.beans.factory.BeanFactory;

/**
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
