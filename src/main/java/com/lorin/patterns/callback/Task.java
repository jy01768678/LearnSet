package com.lorin.patterns.callback;

/**
 * Created by lorin on 2018/4/27.
 */
public abstract class Task {

    public final void excuteWith(CallBack callBack) {

        execute();
        if (callBack != null) {
            callBack.call();
        }
    }

    public abstract void execute();

}
