package com.lorin.concurrent;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class DelayItem<T> implements Delayed{

    private final static long NANO_ORIGIN = System.nanoTime();
    
    private final static long Now(){
        return System.nanoTime() - NANO_ORIGIN;
    }
    
    private final static AtomicLong sequencer = new AtomicLong(0);
    
    private final long time;
    
    private final long sequenceNumber;
    
    private final T item;
    
    public DelayItem(T submit, long timeout){
        this.item = submit;
        this.time = Now() + timeout;
        this.sequenceNumber = sequencer.getAndIncrement();
    }
    
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time-Now(), TimeUnit.NANOSECONDS);
    }
    public T getItem() {
        return this.item;
    }
    @Override
    public int compareTo(Delayed other) {
        if (other == this) // compare zero ONLY if same object
            return 0;
        if (other instanceof DelayItem) {
            DelayItem x = (DelayItem) other;
            long diff = time - x.time;
            if (diff < 0)
                return -1;
            else if (diff > 0)
                return 1;
            else if (sequenceNumber < x.sequenceNumber)
                return -1;
            else
                return 1;
        }
        long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

}
