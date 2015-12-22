package com.lorin.design.command;

import java.io.Serializable;

public class CommandContext implements Serializable {

    private String methodName;

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
