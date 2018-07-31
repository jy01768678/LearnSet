package com.lorin.patterns.factory;

/**
 * Created by lorin on 2018/4/17.
 */
public class ElfCastle implements Castle {
    static final String DESCRIPTION = "This is the Elven castle!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
