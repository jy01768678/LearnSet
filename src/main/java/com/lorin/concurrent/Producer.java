package com.lorin.concurrent;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;


public class Producer implements Runnable{

    private BlockingQueue<String> queue;
    
    public Producer(BlockingQueue<String> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        String uuid = UUID.randomUUID().toString();
        try {
            queue.put(uuid);
            System.out.println(Thread.currentThread() + " produce uuid:" + uuid);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
