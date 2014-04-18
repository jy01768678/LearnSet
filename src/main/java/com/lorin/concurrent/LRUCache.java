package com.lorin.concurrent;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 375034167705939481L;

    private final int maxCapacity;
    
    private final static float DEFAULT_LOAD_FACTOR = 0.75f;
    
    /**
     * 读写锁
     */
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock(); 
    
    /**
     * 读锁
     */
    private final Lock readLock = rwLock.readLock();
    
    private final Lock writeLock = rwLock.writeLock();
    
    public LRUCache(int maxCapacity){
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public V get(Object key) {
        readLock.lock();
        try{
            return super.get(key);
        }finally{
            readLock.unlock();
        }
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return super.removeEldestEntry(eldest);
    }

    @Override
    public boolean containsKey(Object key) {
        readLock.lock();
        try{
            return super.containsKey(key);
        }finally{
            readLock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        writeLock.lock();
        try{
            return super.put(key, value);
        }finally{
            writeLock.unlock();
        }
    }
    
}
