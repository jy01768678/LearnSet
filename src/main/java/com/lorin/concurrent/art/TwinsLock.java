package com.lorin.concurrent.art;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock{

	private final Sync sync = new Sync(2);
	
	private static final class Sync extends AbstractQueuedSynchronizer{
		Sync(int count){
			if(count <=0){
				throw new IllegalArgumentException("");
			}
			setState(count);
		}
		public int tryAcquireShared(int reduceCount){
			for(;;){
				int current = getState();
				int newCount = current - reduceCount;
				if(newCount < 0 || compareAndSetState(current,newCount)){
					return newCount;
				}
			}
		}
		public boolean tryReleaseShared(int returnCode){
			for(;;){
				int current = getState();
				int newCount = current + current;
				if(compareAndSetState(current,newCount)){
					return true;
				}
			}
				
		}
	}
	
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
