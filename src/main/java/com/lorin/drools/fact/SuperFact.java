package com.lorin.drools.fact;

import java.io.Serializable;

/**
 * Created by baidu on 16/7/7.
 */
public class SuperFact implements Serializable {
    private static final long serialVersionUID = -4463289001164493237L;
    protected boolean isError;
    protected String exceptionMsg;

    public SuperFact() {
    }

    public boolean isError() {
        return this.isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public String getExceptionMsg() {
        return this.exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
