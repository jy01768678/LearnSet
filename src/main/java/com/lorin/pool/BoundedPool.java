package com.lorin.pool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BoundedPool<T> extends AbstractPool<T> {

	private int size;
	private Queue<T> objects;
	private Validator<T> validator;
	private ObjectFactory<T> objectFactory;
	private Semaphore permits;
	private volatile boolean shutdownCalled;

	public BoundedPool(int size, Validator<T> validator,
			ObjectFactory<T> objectFactory) {
		super();
		this.objectFactory = objectFactory;
		this.size = size;
		this.validator = validator;
		objects = new LinkedList<T>();
		initializeObjects();
		shutdownCalled = false;
	}

	private void initializeObjects() {
		for (int i = 0; i < size; i++) {
			objects.add(objectFactory.createNew());
		}
	}

	@Override
	public T get() {
		T t = null;
		if(!shutdownCalled){
			if(permits.tryAcquire()){
				t = objects.poll();
			}
		}else {
			throw new IllegalStateException("Object pool already shutdown");
		}
		return t;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		shutdownCalled = true;
		clearResources();
	}

	private void clearResources() {
		// TODO Auto-generated method stub
		for(T t : objects){
			validator.invalidate(t);
		}
	}

	@Override
	protected void handleInvalidReturn(T t) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void returnToPool(T t) {
		// TODO Auto-generated method stub
		boolean add = objects.add(t);
		if(add){
			permits.release();
		}
	}

	@Override
	protected boolean isValid(T t) {
		return validator.isValid(t);
	}

}
