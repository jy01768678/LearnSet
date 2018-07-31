package com.lorin.patterns.adapter;

/**
 * Created by lorin on 2018/4/17.
 */
public class Captain {

    private RowingBoat rowingBoat;

    public Captain() {

    }

    public Captain(RowingBoat rowingBoat) {
        this.rowingBoat = rowingBoat;
    }

    public RowingBoat getRowingBoat() {
        return rowingBoat;
    }

    public void setRowingBoat(RowingBoat rowingBoat) {
        this.rowingBoat = rowingBoat;
    }

    public void row() {
        rowingBoat.row();
    }

}
