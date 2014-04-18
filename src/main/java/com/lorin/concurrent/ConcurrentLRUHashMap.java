package com.lorin.concurrent;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLRUHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>,
        Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -723470327884860064L;

    /* ---------------- Constants -------------- */
    /**
     * Segement默认最大数
     */
    static final int DEFAULT_SEGEMENT_MAX_CAPACITY = 100;

    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int DEFAULT_CURRENCY_LEVEL = 16;

    static final int MAXIMUM_CAPACITY = 1 << 30;
    
    static final int MAX_SEGMENTS = 1 << 16; // slightly conservative

    static final int RETRIES_BEFORE_LOCK = 2;

    /* ---------------- Fields -------------- */

    final int segmentMark;

    final int segmentShift;

    final Segment<K, V>[] segments;

    transient Set<K> keySet;

    transient Set<Map.Entry<K, V>> entrySet;

    transient Collection<V> values;

    /* ---------------- Small Utilities -------------- */
    private static int hash(int h) {
        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        h += (h << 15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h << 3);
        h ^= (h >>> 6);
        h += (h << 2) + (h << 14);
        return h ^ (h >>> 16);
    }

    final Segment<K, V> segmentFor(int hash) {
        return segments[(hash >> segmentShift) & segmentMark];
    }

    /* ---------------- Inner Classes -------------- */
    /**
     * 修改原HashEntry，
     */
    static final class HashEntry<K, V> {

        final K key;

        final int hash;

        volatile V value;

        /**
         * hash链指针
         */
        final HashEntry<K, V> next;

        /**
         * 双向链表的下一个节点
         */
        HashEntry<K, V> linkNext;

        /**
         * 双向链表的上一个节点
         */
        HashEntry<K, V> linkPrev;

        /**
         * 死亡标记
         */
        AtomicBoolean dead;

        HashEntry(K key, int hash, HashEntry<K, V> next, V value) {
            this.key = key;
            this.hash = hash;
            this.next = next;
            this.value = value;
            dead = new AtomicBoolean(false);
        }

        @SuppressWarnings("unchecked")
        static final <K, V> HashEntry<K, V>[] newArray(int i) {
            return new HashEntry[i];
        }
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V putIfAbsent(K arg0, V arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(Object arg0, Object arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public V replace(K arg0, V arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean replace(K arg0, V arg1, V arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * 基于原Segment修改，内部实现一个双向列表
     * 
     * @author noah
     * 
     * @param <K>
     * @param <V>
     */
    static final class Segment<K, V> extends ReentrantLock implements Serializable {

        private static final long serialVersionUID = 7113606290539570987L;

        transient volatile int count;

        transient int modCount;

        transient int threshold;

        transient volatile HashEntry<K, V>[] table;

        final float loadFactor;

        /**
         * 头节点
         */
        transient final HashEntry<K, V> header;

        /**
         * Segement最大容量
         */
        final int maxCapacity;
        
        Segment(int maxCapacity, float lf, ConcurrentLRUHashMap<K, V> lruMap) {
            this.maxCapacity = maxCapacity;
            loadFactor = lf;
            setTable(HashEntry.<K,V>newArray(maxCapacity));
            header = new HashEntry<K, V>(null, -1, null, null);
            header.linkNext = header;
            header.linkPrev = header;
        }
        
        @SuppressWarnings("unchecked")
        static final <K, V> Segment<K, V>[] newArray(int i) {
                return new Segment[i];
        }

        /**
         * Sets table to new HashEntry array. Call only while holding lock or in
         * constructor.
         */
        void setTable(HashEntry<K, V>[] newTable) {
                threshold = (int) (newTable.length * loadFactor);
                table = newTable;
        }
        
        /**
         * Returns properly casted first entry of bin for given hash.
         */
        HashEntry<K, V> getFirst(int hash) {
                HashEntry<K, V>[] tab = table;
                return tab[hash & (tab.length - 1)];
        }
        
        V readValueUnderLock(HashEntry<K, V> e) {
            lock();
            try {
                    return e.value;
            } finally {
                    unlock();
            }
        }

        V get(Object key, int hash) {
            lock();
            try{
                if(count != 0){
                    HashEntry<K,V> e = getFirst(hash);
                    while(e != null){
                        if(e.hash == hash && key.equals(key)){
                            V v = e.value;
                            // 将节点移动到头节点之前
                            moveNodeToHeader(e);
                            if(v != null) return v;
                            return readValueUnderLock(e);//recheck
                        }
                        e = e.next;
                    }
                }
                return null;
            }finally{
                unlock();
            }
        }
        
        /**
         * 将节点移动到头节点之前
         * 
         * @param entry
         */
        void moveNodeToHeader(HashEntry<K, V> entry){
           // 先移除，然后插入到头节点的前面
           removeNode(entry); 
           addBefore(entry, header);
        }
        /**
         * 将第一个参数代表的节点插入到第二个参数代表的节点之前
         * 
         * @param newEntry
         *            需要插入的节点
         * @param entry
         *            被插入的节点
         */
        void addBefore(HashEntry<K,V> newEntry,HashEntry<K,V> entry){
            newEntry.linkNext = entry;
            newEntry.linkPrev = entry.linkPrev;
            entry.linkPrev.linkNext = newEntry;
            entry.linkPrev = newEntry;
        }
        /**
         * 从双向链中删除该Entry
         * 
         * @param entry
         */ 
        void removeNode(HashEntry<K,V> entry){
            entry.linkPrev.linkNext = entry.linkNext;
            entry.linkNext.linkPrev = entry.linkPrev;
        }
        
        boolean containsKey(Object key, int hash) {
            lock();
            try{
                if(count != 0){
                    HashEntry<K,V> e = getFirst(hash);
                    if(e != null){
                        if(e.hash == hash && key.equals(key)){
                            moveNodeToHeader(e);
                            return true;
                        }
                    }
                }
                return false;
            }finally{
                unlock();
            }
        }
        
        boolean containsValue(Object value) {
            lock();
            try{
                if(count != 0){
                    HashEntry<K, V>[] tab = table;
                    int len = tab.length;
                    for(int i = 0; i < len; i++){
                        for (HashEntry<K, V> e = tab[i]; e != null; e = e.next) {
                            V v = e.value;
                            if(v == null){
                                v = readValueUnderLock(e);
                            }
                            if(v.equals(value)){
                                moveNodeToHeader(e);
                                return true;
                            }
                        }
                    }
                }
                return false;
            }finally{
                unlock();
            }
        }
        
        V replace(K key, int hash, V oldValue, V newValue) {
            lock();
            try{
                HashEntry<K,V> e = getFirst(hash);
                while(e != null && (hash != e.hash || key.equals(e.key))){
                    e = e.next;
                }
                boolean replace = false;
                if(e != null && oldValue.equals(e.value)){
                    replace = true;
                    e.value = newValue;
                    // 移动到头部
                    moveNodeToHeader(e);
                }
                return oldValue;
            }finally{
                unlock();
            }
        }
        
        V put(K key, int hash, V value, boolean onlyIfAbsent) {
            lock();
            try{
                int c = count;
                if(c++ > threshold){
                    rehash();
                }
                HashEntry<K,V>[] tab = table;
                int index = hash & (tab.length - 1);
                HashEntry<K,V> frist = tab[index];
                HashEntry<K,V> e = frist;
                while(e != null && (e.hash != hash || !key.equals(e.key))) {
                    e = e.next;
                }
                V oldValue = null;
                if(e != null){
                    oldValue = e.value;
                    if(!onlyIfAbsent){
                        e.value = value;
                        moveNodeToHeader(e);
                    }
                } else {
                    oldValue = null;
                    ++modCount;
                    HashEntry<K,V> newEntry = new HashEntry<K, V>(key, hash, frist, value);
                    tab[index] = newEntry;
                    count = c;
                    // 添加到双向链
                    addBefore(newEntry, header);
                    //判断是否达到最大值
                    removeEldestEntry();
                }
                return oldValue;
            }finally{
                unlock();
            }
        }
        
        /**
         * Remove; match on key only if value null, else match both.
         */
        V remove(Object key, int hash, Object value) {
            lock();
            try{
                int c = count - 1;
                HashEntry<K,V>[] tab = table;
                int index = hash & (tab.length - 1);
                HashEntry<K,V> frist = tab[index];
                HashEntry<K,V> e = frist;
                while (e != null && (e.hash != hash || !key.equals(e.key)))
                    e = e.next;
                V oldValue = null;
                if(e != null){
                    V v = e.value;
                    if(v == null || value.equals(v)){
                        oldValue = v;
                        ++modCount;
                        HashEntry<K,V> newFrist = e.next;
                        for(HashEntry<K,V> p = frist;p!=e ;p = p.next){
                            newFrist = new HashEntry<K, V>(p.key, p.hash, newFrist, p.value);
                            newFrist.linkNext = p.linkNext;
                            newFrist.linkPrev = p.linkPrev;
                        }
                        tab[index] = newFrist;
                        count = c;
                        removeNode(e);
                    }
                }
                return oldValue;
            }finally{
                unlock();
            }
        }
        
        /**
         * 移除最旧元素
         */
        void removeEldestEntry() {
            if (count > this.maxCapacity) {
                HashEntry<K,V> eldestEntry = header.linkNext;
                remove(eldestEntry.key, eldestEntry.hash, null);
            }
        }

        void clear() {
            if (count != 0) {
                    lock();
                    try {
                            HashEntry<K, V>[] tab = table;
                            for (int i = 0; i < tab.length; i++)
                                    tab[i] = null;
                            ++modCount;
                            count = 0; // write-volatile
                    } finally {
                            unlock();
                    }
            }
    }
        
        private void rehash() {
            
        }
    }
    
    /**
     * 使用指定参数，创建一个ConcurrentLRUHashMap
     * 
     * @param segementCapacity
     *            Segement最大容量
     * @param loadFactor
     *            加载因子
     * @param concurrencyLevel
     *            并发级别
     */
    public ConcurrentLRUHashMap(int segementCapacity, float loadFactor,
            int concurrencyLevel) {
        if (!(loadFactor > 0) || segementCapacity < 0 || concurrencyLevel <= 0)
            throw new IllegalArgumentException();
        if (concurrencyLevel > MAX_SEGMENTS)
            concurrencyLevel = MAX_SEGMENTS;
        int sshift = 0;
        int ssize = 1;
        while (ssize < concurrencyLevel) {
                ++sshift;
                ssize <<= 1;
        }
        segmentShift = 32 - sshift;
        segmentMark = ssize - 1;
        this.segments = Segment.newArray(ssize);

        for (int i = 0; i < this.segments.length; ++i)
                this.segments[i] = new Segment<K, V>(segementCapacity, loadFactor, this);
    }
    
    /**
     * 使用指定参数，创建一个ConcurrentLRUHashMap
     * 
     * @param segementCapacity
     *            Segement最大容量
     * @param loadFactor
     *            加载因子
     */
    public ConcurrentLRUHashMap(int segementCapacity, float loadFactor) {
            this(segementCapacity, loadFactor, DEFAULT_CURRENCY_LEVEL);
    }

    /**
     * 使用指定参数，创建一个ConcurrentLRUHashMap
     * 
     * @param segementCapacity
     *            Segement最大容量
     */
    public ConcurrentLRUHashMap(int segementCapacity) {
            this(segementCapacity, DEFAULT_LOAD_FACTOR, DEFAULT_CURRENCY_LEVEL);
    }

    /**
     * 使用默认参数，创建一个ConcurrentLRUHashMap，存放元素最大数默认为1000， 加载因子为0.75，并发级别16
     */
    public ConcurrentLRUHashMap() {
            this(DEFAULT_SEGEMENT_MAX_CAPACITY, DEFAULT_LOAD_FACTOR,
                    DEFAULT_CURRENCY_LEVEL);
    }
    
    public boolean isEmpty() {
        final Segment<K, V>[] segments = this.segments;
        /*
         * We keep track of per-segment modCounts to avoid ABA problems in which
         * an element in one segment was added and in another removed during
         * traversal, in which case the table was never actually empty at any
         * point. Note the similar use of modCounts in the size() and
         * containsValue() methods, which are the only other methods also
         * susceptible to ABA problems.
         */
        int[] mc = new int[segments.length];
        int mcsum = 0;
        for (int i = 0; i < segments.length; ++i) {
                if (segments[i].count != 0)
                        return false;
                else
                        mcsum += mc[i] = segments[i].modCount;
        }
        // If mcsum happens to be zero, then we know we got a snapshot
        // before any modifications at all were made. This is
        // probably common enough to bother tracking.
        if (mcsum != 0) {
                for (int i = 0; i < segments.length; ++i) {
                        if (segments[i].count != 0 || mc[i] != segments[i].modCount)
                                return false;
                }
        }
        return true;
    }
    
    public int size() {
        final Segment<K, V>[] segments = this.segments;
        long sum = 0;
        long check = 0;
        int[] mc = new int[segments.length];
        // Try a few times to get accurate count. On failure due to
        // continuous async changes in table, resort to locking.
        for (int k = 0; k < RETRIES_BEFORE_LOCK; ++k) {
                check = 0;
                sum = 0;
                int mcsum = 0;
                for (int i = 0; i < segments.length; ++i) {
                        sum += segments[i].count;
                        mcsum += mc[i] = segments[i].modCount;
                }
                if (mcsum != 0) {
                        for (int i = 0; i < segments.length; ++i) {
                                check += segments[i].count;
                                if (mc[i] != segments[i].modCount) {
                                        check = -1; // force retry
                                        break;
                                }
                        }
                }
                if (check == sum)
                        break;
        }
        if (check != sum) { // Resort to locking all segments
                sum = 0;
                for (int i = 0; i < segments.length; ++i)
                        segments[i].lock();
                for (int i = 0; i < segments.length; ++i)
                        sum += segments[i].count;
                for (int i = 0; i < segments.length; ++i)
                        segments[i].unlock();
        }
        if (sum > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
        else
                return (int) sum;

    }
    
}
