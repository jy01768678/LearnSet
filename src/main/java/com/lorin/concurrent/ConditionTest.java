package com.lorin.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConditionTest {
    private static int i;  
    private static int bufSize = 26, size;  
    private static char cStart = 97;  
    private static char[] cbuf = new char[bufSize];  
  
    private static Lock lk = new ReentrantLock();  
    private static Condition cdEmpty = lk.newCondition();  
    private static Condition cdFull = lk.newCondition();  
    private static final int testInterval = 500;
    
    public static void fill() throws InterruptedException {  
        lk.lock();  
        try {  
            while (size == bufSize) { // buffer is full, wait for condition empty  
                cdEmpty.await();  
            }  
  
            System.out.printf("\n\n------\nstart fill\n\n");  
            while (size < bufSize) { // buffer is not full now, fill it  
                char c = (char) (cStart + size);  
                cbuf[size++] = c;  
                printTime("put: " + new Character(c));  
                Thread.sleep(testInterval);  
            }  
            cdFull.signal(); // buffer is full filled now, need to be read  
            System.out.printf("\nbuffer full\n------\n\n");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            lk.unlock();  
        }  
    }  
    
    public static void take() throws InterruptedException {  
        lk.lock();  
        try {  
            while (size != bufSize) { // not full, wait for condition full  
                cdFull.await();  
            }  
            System.out.printf("\n\n------\nstart take\n\n");  
            for (int i = 0; i < bufSize; i++) { // now is full, take  
                printTime("take: " + new Character(cbuf[i]));  
                size--;  
                Thread.sleep(testInterval);  
            }  
            cdEmpty.signal(); // buffer is empty now, need to be fill  
            System.out.printf("\nbuffer empty\n------\n\n");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            lk.unlock();  
        }  
    }  
  
    public static void test() {  
        // thread to fill buffer  
        Thread tFill = new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    while (true)  
                        fill();  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }  
        }, "t-fill");  
  
        // thread to take from buffer  
        Thread tTake = new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    while (true)  
                        take();  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }  
        }, "t-take");  
        tTake.start();  
        tFill.start();  
    }  
  
    public static void printTime() {  
        printTime("");  
    }  
  
    /** 
     * print thread name & time 
     *  
     * @param info 
     *            additional info to print 
     */  
    public synchronized static void printTime(String info) {  
        System.out.printf("%s:\t%d,\t,%d,\t%s\n", Thread.currentThread().getName(), ++i, System.currentTimeMillis() / 1000, info);  
    }  
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        test();
    }

}
