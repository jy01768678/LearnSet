package com.lorin.patterns.factory;

/**
 * Created by lorin on 2018/4/17.
 */
public class ElfKing implements King {

    static final String DESCRIPTION = "This is the Elven king!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
