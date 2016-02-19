package com.lorin.design.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by taodong on 16/2/18.
 */
public class OberverExample {
    public static void main(String[] args) {
        Observer consumer = new Consumer();
        MilkProvider provider = new MilkProvider();
        provider.addObserver(consumer);
        provider.milkProduced();
    }

    static class MilkProvider extends Observable {
        public void milkProduced() {
            setChanged();//状态改变，必须调用
            notifyObservers();
        }
        public float getPrice() {
            return 2.5f;
        }

    }

    static class Consumer implements Observer {
        @Override
        public void update(Observable arg0, Object arg1) {
            MilkProvider provider = (MilkProvider)arg0;
            System.out.println("milk price =" + provider.getPrice());

        }
    }

}
