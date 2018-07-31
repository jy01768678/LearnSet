package com.lorin.patterns.factory;

/**
 * Created by lorin on 2018/4/17.
 */
public class ElfKingdomFactory implements KingdomFactory{

    public Castle createCastle() {
        return new ElfCastle();
    }

    public King createKing() {
        return new ElfKing();
    }

    public Army createArmy() {
        return new ElfArmy();
    }
}
