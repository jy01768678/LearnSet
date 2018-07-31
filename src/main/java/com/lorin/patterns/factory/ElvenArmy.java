package com.lorin.patterns.factory;

/**
 * Created by lorin on 2018/4/17.
 */
public class ElvenArmy implements Army{
    static final String DESCRIPTION = "This is the Elven Army!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
