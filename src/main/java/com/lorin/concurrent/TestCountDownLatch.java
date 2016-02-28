package com.lorin.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch start = new CountDownLatch(1);
        
        final CountDownLatch end = new CountDownLatch(10);
        
        final ExecutorService service = Executors.newFixedThreadPool(10);
        
        for (int index = 0; index < 10; index++) {
            final int NO = index + 1;
            Runnable run = new Runnable() {
             public void run() {
              try {
                  start.await();//一直阻塞
               Thread.sleep((long) (Math.random() * 10000));
               System.out.println("No." + NO + " arrived");
              } catch (InterruptedException e) {
              } finally {
               end.countDown();
              }
             }
            };
            service.submit(run);
        }
           System.out.println("Game Start");
           start.countDown();
           end.await();
           System.out.println("Game Over");
           service.shutdown();
    }
   
    public static void test() throws InterruptedException{
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(10);
        final ExecutorService service = Executors.newFixedThreadPool(10);
        
        for(int i = 0;i<10;i++){
            final int NO = i + 1;
            Runnable run = new Runnable(){
                @Override
                public void run() {
                    try {
                        start.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally{
                        end.countDown();
                    }
                }
            };
            service.submit(run);
        }
        start.countDown();
        end.await();
        service.shutdown();
    }
}
