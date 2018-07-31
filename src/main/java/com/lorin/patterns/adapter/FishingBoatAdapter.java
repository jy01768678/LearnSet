package com.lorin.patterns.adapter;


/**
 * Created by lorin on 2018/4/17.
 */
public class FishingBoatAdapter implements RowingBoat{

    private FishingBoat boat;

    public FishingBoatAdapter() {
        boat = new FishingBoat();
    }

    @Override
    public void row() {
        boat.sail();
    }
}
