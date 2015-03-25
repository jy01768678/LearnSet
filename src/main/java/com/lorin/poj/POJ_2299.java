package com.lorin.poj;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class POJ_2299 {

	static int num = 0;
	
	public static void main(String[] args){
		Scanner scan = new Scanner(new BufferedInputStream(System.in));
		while(scan.hasNext()){
			int n = scan.nextInt();
			if(n == 0){
				break;
			}
			
			num = 0;
			int[] data = new int[n];
			for(int i=0;i<n;i++){
				data[i] = scan.nextInt();
			}
			
			mergesort(data,0,n-1);
			System.out.println(num);
		}
		
	}

	private static void mergesort(int[] data, int left, int right) {
		if(left < right){
			int center = (left + right) / 2 ;
			mergesort(data, left, center);
			mergesort(data,center + 1,right);
			merge(data,left,center,right);
		}
		
	}

	private static void merge(int[] data, int left, int center, int right) {
		//[1,2,3,4] left = 1,center = 2 ,right = 4
		int[] temp = new int[right - left + 1];
		int i = left;
		int j = center + 1;
		int k = 0;
		
		while(i <= center && j <=right){
			if(data[i] > data[j]){
				temp[k++] = data[j++];
				num += center -i + 1;
			}else {
				temp[k++] = data[i++];
			}
		}
		while(i<=center){//处理左半边顺序正常情况
			temp[k++] = data[i++];
		}
		while(j <= right){//处理右半边顺序正常的情况
			temp[k++] = data[j++];
		}
		//把排好序的付给原数组
		for(i = left ,k=0;i <= right;i++,k++){
			data[i] = temp[k];
		}
	}
}
