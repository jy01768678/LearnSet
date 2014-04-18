package com.lorin.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class TestSemaphore {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore sm = new Semaphore(5);
        for(int i=0;i<20;i++){
            final int NO = i;  
            Runnable task = new Runnable(){
                @Override
                public void run() {
                    try {
                        sm.acquire();
                        System.out.println("Accessing: " + NO);     
                        Thread.sleep((long) (Math.random() * 10000));     
                        sm.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(task);
        }
        // 退出线程池     
        service.shutdown();
    }
    
    public static void test(){
        ExecutorService server = Executors.newCachedThreadPool();
        final Semaphore sm = new Semaphore(5);
        for(int i=0;i<20;i++){
            final int NO = i;
            Runnable taak = new Runnable(){
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        sm.acquire();
                        
                        sm.release();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
        }
    }
    
}
