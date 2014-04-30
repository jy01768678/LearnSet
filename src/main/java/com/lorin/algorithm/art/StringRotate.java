package com.lorin.algorithm.art;

public class StringRotate {

	
	
	public static void main(String[] args) {
		int i = 4;
		String str = "acdefghjklmnopqrstuvw";
		int n = str.length();
		char[] charArray = str.toCharArray();
		char[] result = rotate(charArray,n,i % n,0,n-1,true);
		System.out.println(result);
	}

	/**
	 * 
	 * @param charArray
	 * @param len
	 * @param m
	 * @param head
	 * @param tail
	 * @param flag = true进行左旋，flag = false进行右旋    
	 */
	private static char[] rotate(char[] charArray, int n, int m, int head,
			int tail, boolean flag) {
		if(head == tail || m <= 0){
			return charArray;
		}
		char temp;
		if(flag){
			int indexP1 = head;
			int indexP2 = head + m;
			int k = (n - m) - n % m;
			for(int i=0;i<k;i++,indexP1++,indexP2++){
				temp = charArray[indexP2];
				charArray[indexP2] = charArray[indexP1];
				charArray[indexP1] = temp;
			}
			charArray = rotate(charArray, n - k, n % m, indexP1, tail, false);
		}else {
			int indexP1 = tail;
			int indexP2 = tail-m;
			int k = (n - m) - n % m;
			for(int i=0;i<k;i++,indexP1--,indexP2--){
				temp = charArray[indexP2];
				charArray[indexP2] = charArray[indexP1];
				charArray[indexP1] = temp;
			}
			charArray = rotate(charArray, n - k, n % m, head, indexP1, true);
		}
		return charArray;
	}
	
}
