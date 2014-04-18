package com.lorin.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class NumberPrinter {

    private Lock lock = new ReentrantLock();
    
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    
    private Map<Integer, Condition> condtionContext =   
        new HashMap<Integer, Condition>();  
 
    public NumberPrinter() {  
        condtionContext.put(Integer.valueOf(0), c1);  
        condtionContext.put(Integer.valueOf(1), c2);  
        condtionContext.put(Integer.valueOf(2), c3);  
    }
    
    private int count = 0;
    
    public static void main(String[] args) {
        ExecutorService excutor = Executors.newFixedThreadPool(3);
        final CountDownLatch latch = new CountDownLatch(1);
        final NumberPrinter printer = new NumberPrinter();
        for(int i=0;i<3;i++){
            final int id = i;
            excutor.submit(new Runnable(){
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    printer.print(id);
                }
            });
            System.out.println("3");
            latch.countDown();
            excutor.shutdown();
        }
    }

    public void print(int id) {
        lock.lock();
        try {  
            while(count*5 < 75) { 
                int curId = count % 3;
                if(id == curId){
                    for (int i = 1; i<=5; i++) {  
                        System.out.print(count*5 +i+ ",");  
                    }  
                    System.out.println();  
                    count++;  
                    int nextID = count % 3;  
                    Condition nextCondition = condtionContext.get(  
                            Integer.valueOf(nextID));  
                    //通知下一线程  
                    nextCondition.signal();  
                } else {  
                    Condition condition = condtionContext.get(  
                            Integer.valueOf(id));  
                    condition.await();  
                }  
            }
            //通知线程结束  
            for(Condition c : condtionContext.values()) {  
                c.signal();  
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {  
            lock.unlock();  
        }  
    }
}
