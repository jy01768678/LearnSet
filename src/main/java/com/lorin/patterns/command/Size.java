package com.lorin.patterns.command;

/**
 * Created by lorin on 2018/4/27.
 */
public enum Size {
    SMALL("small"), NORMAL("normal");

    private String title;

    Size(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
