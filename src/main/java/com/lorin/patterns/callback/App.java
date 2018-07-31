package com.lorin.patterns.callback;

/**
 * Created by lorin on 2018/4/27.
 */
public class App {
    public static void main(String[] args) {
        Task task = new SimpleTask();
        CallBack callBack = () -> System.out.print("xxxx");
        task.excuteWith(callBack);
    }
}
