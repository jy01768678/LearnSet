package com.lorin.concurrent;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
//AbstractQueuedSynchronizer是个很强大的工具，同步包里的大多数同步工具都是用它来处理同步问题的。
public class ProcessControll {

	final static int MAX_QPS = 10;

	final static Semaphore semaphore = new Semaphore(MAX_QPS);

	public static void main(String[] args) throws Exception {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//int available = semaphore.availablePermits();
				//只释放用掉的许可证数量
				//semaphore.release(MAX_QPS-available);
				semaphore.release(MAX_QPS / 2);
			}
		}, 1000, 500, TimeUnit.MILLISECONDS);

		ExecutorService pool = Executors.newFixedThreadPool(50);
		for (int i = 50; i > 0; i--) {
			final int x = i;
			pool.submit(new Runnable() {
				@Override
				public void run() {
					for (int j = 500; j > 0; j--) {
						if(semaphore.tryAcquire()){
							System.out.println("可以获得信号量！");
						}else{
							System.out.println("不可以获得信号量！");
						}
						semaphore.acquireUninterruptibly(1);
						remoteCall(x, j);
					}
				}

			});
		}
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.HOURS);
		System.out.println("DONE");
	}

	private static void remoteCall(int i, int j) {
		System.out.println(String.format("%s - %s: %d %d", new Date(),
				Thread.currentThread(), i, j));
	}
}
