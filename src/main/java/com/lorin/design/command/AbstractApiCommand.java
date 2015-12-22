package com.lorin.design.command;

import java.util.Map;


public abstract class AbstractApiCommand extends ApiCommandSupport {
    /**
     * 子类需要实现这个方法
     * @return
     */
    protected abstract void onExecute();
}
