package com.lorin.algorithm;


public class MinHeap<T extends Comparable<? super T>> extends Heap<T> {

	public MinHeap() {  
        super();  
    }  
    public MinHeap(int capacity) {  
        super(capacity);  
    }  
    public MinHeap(T[] elements) {  
        super(elements);  
    }  
    
	@Override
	protected void filterDown(int startPos) {
		int i = startPos;
		int j = (startPos << 1) + 1;
		while(j < size){
			if(j < size - 1 && elements[j].compareTo(elements[j+1]) > 0){
				j++;
			}
			if (elements[i].compareTo(elements[j]) <= 0) {  
                break;  
            } else {  
                swap(i, j);  
                i = j;  
                j = (j << 1) + 1;  
            } 
		}
	}

	@Override
	protected void filterUp(int startPos) {
		int i = startPos;//leaf
		int j = (startPos - 1) >> 1 ;//root
		while(i > 0){
			if (elements[j].compareTo(elements[i]) < 0) {  
                break;  
            } else {  
                swap(i, j);  
                i = j;  
                j = (i - 1) / 2;  
            }  
		}
	}

}
