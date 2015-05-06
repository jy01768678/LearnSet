package com.lorin.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class BoundedBlockingPool<T> extends AbstractPool<T> implements
		BlockingPool<T> {

	private int size;
	private BlockingQueue<T> objects;
	private Validator validator;
	private ObjectFactory<T> objectFactory;
	private ExecutorService executor = Executors.newCachedThreadPool();
	private volatile boolean shutdownCalled;

	public BoundedBlockingPool(int size, Validator validator,
			ObjectFactory objectFactory) {
		super();
		this.objectFactory = objectFactory;
		this.size = size;
		this.validator = validator;
		objects = new LinkedBlockingQueue(size);
		initializeObjects();
		shutdownCalled = false;
	}

	private void initializeObjects() {
		for (int i = 0; i < size; i++) {
			objects.add(objectFactory.createNew());
		}
	}

	@Override
	public void shutdown() {
		shutdownCalled = true;
		executor.shutdown();
		clearResources();
	}

	private void clearResources() {
		for (T t : objects) {
			validator.invalidate(t);
		}
	}

	@Override
	public T get() {
		if (!shutdownCalled) {
			T t = null;
			try {
				t = objects.take();
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			return t;
		}
		throw new IllegalStateException("Object pool is already shutdown!");
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException {
		if (!shutdownCalled) {
			T t = null;
			try {
				t = objects.poll(timeout, unit);
				return t;
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			return t;
		}
		throw new IllegalStateException("Object pool is already shutdown!");
	}

	@Override
	protected void handleInvalidReturn(Object t) {

	}

	@Override
	protected void returnToPool(Object t) {
		if (validator.isValid(t)) {
			executor.submit(new ObjectReturner(objects, t));
		}
	}

	@Override
	protected boolean isValid(Object t) {
		return validator.isValid(t);
	}

	private class ObjectReturner<T> implements Callable {
		private BlockingQueue queue;
		private T e;

		public ObjectReturner(BlockingQueue queue, T e) {
			this.queue = queue;
			this.e = e;
		}

		public Void call() {
			while (true) {
				try {
					queue.put(e);
					break;
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
			return null;
		}

	}

}
