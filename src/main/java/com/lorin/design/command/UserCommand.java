package com.lorin.design.command;

import java.util.Map;


public class UserCommand extends AbstractApiCommand{

    public static void main(String[] args) {
        UserCommand uc = new UserCommand();
        uc.execute(null);
    }

    @Override
    protected void onExecute() {
        System.out.println("这里是子类onExecute");
    }
}
