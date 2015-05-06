package com.lorin.pool;

public interface Pool<T> {

	T get();
	
	void release(T t);
	
	void shutdown();
	

}
