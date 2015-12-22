package com.lorin.design.command;

import java.util.Map;


public class ApiCommandSupport implements IApiCommand {

    protected void beforeExecute(CommandContext context) {

    }
    @Override
    public void execute(CommandContext context) {
        this.onExecute();
    }

    /**
     * 子类需要重写这个方法
     *
     * @return
     */
    protected void onExecute() {
        System.out.println("这里是父类OnExecute");
    }
}
