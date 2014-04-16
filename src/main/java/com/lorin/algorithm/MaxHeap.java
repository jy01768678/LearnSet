package com.lorin.algorithm;


public class MaxHeap<T extends Comparable<? super T>> extends Heap<T>{

	@Override
	protected void filterDown(int startPos) {
		int i = startPos;
		int j = (startPos << 1) + 1;
		while(j < size){
			if(j < size-1 && elements[j].compareTo(elements[j+1]) < 0){//叶子节点进行比较找出最大的一个
				j++;
			}
			//最大的一个再跟父节点比较 大就跟父节点交换，然后继续轮询下个子树
			if(elements[i].compareTo(elements[j]) < 0){
				swap(i, j);  
                i = j;  
                j = (i << 1) + 1; 
			}
		}
	}

	@Override
	protected void filterUp(int startPos) {
		int i = startPos;//leaf 新加入的叶子节点 也许是根节点的index
		int j = (startPos - 1 ) >> 1;//root 求出次叶子节点的根节点
		while(i > 0){
			if(elements[i].compareTo(elements[j]) > 0){
				swap(i,j);
				i = j;  
                j = (i - 1) >> 1;  
			}
		}
	}

}
