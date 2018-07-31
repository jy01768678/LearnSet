package com.lorin.patterns.factory;

/**
 * Created by lorin on 2018/4/17.
 */
public interface KingdomFactory {
    Castle createCastle();

    King createKing();

    Army createArmy();
}
