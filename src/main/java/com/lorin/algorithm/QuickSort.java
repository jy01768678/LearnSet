package com.lorin.algorithm;

public class QuickSort {
	
	public static void quickSort(int[] a,int left, int right){
		if(left > right){
			return ;
		}
		int rp = sortPivot(a ,left ,right);
		
		quickSort(a, left, rp-1);
		quickSort(a, rp + 1, right);
	}

	private static int sortPivot(int[] a, int left, int right) {
		int pivot = a[left];
		int lp = left + 1;
		int rp = right;
		while(lp <= rp){
			for(;lp<=right;lp++){
				if(a[lp] > pivot)break;
			}
			for(;rp>=left+1;rp--){
				if(a[rp] < pivot)break;
			}
			if(lp < rp){
				int tmp = a[lp];  
		        a[lp]=a[rp];  
		        a[rp] = tmp;  
		        lp++;  
		        rp--; 
			}
		}
		a[left]=a[rp];  
		a[rp] = pivot;  
		return rp;  
	}
	

}
