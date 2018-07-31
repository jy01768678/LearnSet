package com.lorin.patterns.command;

/**
 * Created by lorin on 2018/4/27.
 */
public enum Visibility {
    VISIBLE("visible"), INVISIBLE("invisible");

    private String title;

    Visibility(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
