package com.lorin.concurrent;

import java.util.concurrent.LinkedBlockingQueue;


public class PCTester {

    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);
        // 生产者一号
        Producer producer = new Producer(queue);
        // 生产者二号
        Producer producer2 = new Producer(queue);
        // 消费者
        Consumer consumer = new Consumer(queue);

        for (int i = 0; i < 15; i++) {
            new Thread(producer).start();
        }

        for (int i = 0; i < 15; i++) {
            new Thread(producer2).start();
        }

        for (int i = 0; i < 30; i++) {
            new Thread(consumer).start();
        }
    }
}
