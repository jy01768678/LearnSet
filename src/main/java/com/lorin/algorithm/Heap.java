package com.lorin.algorithm;

import java.lang.reflect.Array;

public abstract class Heap<T extends Comparable<? super T>> {

	public static final int DEFAULT_CAPACITY = 16;
	
	protected T[] elements = null;
	
	protected int capacity = 0;
	
	protected int size = 0;
	
	public Heap(){
		this(DEFAULT_CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public Heap(int capacity){
		this.capacity = capacity;
		this.elements = (T[])Array.newInstance(Comparable.class, this.capacity);
	}
	
	public Heap(T[] elements){
		this(elements.length);
		System.arraycopy(elements, 0, this.elements, 0, elements.length);
		this.size = elements.length;
		int startPos = (elements.length / 2 ) -1; 
		while(startPos >= 0){
			filterDown(startPos);
			startPos--;
		}
	}

	protected abstract void filterDown(int startPos);
	
	protected abstract void filterUp(int startPos);
	
	public boolean insert(T data){
		ensureCapacity();
		elements[size++] = data;
		filterUp(size-1);
		return true;
	}
	
	public int Size() {  
        return size;  
    } 
	
	public T peek(){
		if(size == 0){
			throw new RuntimeException("Heap is empty");
		}
		return elements[0];
	}
	protected void swap(int i, int j) {  
        T temp = elements[i];  
        elements[i] = elements[j];  
        elements[j] = temp;  
    }  
	@SuppressWarnings("unchecked")
	private void ensureCapacity() {
		if(size == capacity){
			capacity *= 2;
			T[] newElements = (T[])Array.newInstance(Comparable.class, capacity);
			System.arraycopy(elements, 0, newElements, 0, size);
			elements = newElements;
		}
	}
	public String toString() {  
        StringBuilder builder = new StringBuilder();  
        for (int i = 0; i < size; i++) {  
            builder.append(elements[i]);  
            if (i != size - 1) {  
                builder.append(",");  
            }  
        }  
        return builder.toString();  
    }  
}
