package com.lorin.algorithm.art;

import java.util.ArrayList;
import java.util.List;

public class Find2Num {

	//固定的两个值
	public static boolean findSum(int[] data,int len,int sum,int firstNum,int secondNum){
		//假设数据是有序的
		if(len < 1)return false;
		int start = 0;
		int end = len - 1;
		while(end > start){
			int currentSum = data[start] + data[end];
			if(currentSum == sum){
				System.out.println("firstNum="+data[start]+"|secondNum="+data[end]);
				return true;
			}else if(currentSum > sum){
				end--;
			}else {
				start++;
			}
		}
		return false;
	}
	
	
	//所有序列1,2,3,4,5...n ;和为m
	public static void findFactor(int n,int sum,int index,List<Integer> ret){
		if(sum < 0) return;
		if(sum == 0){
			for(int node : ret){
				System.out.print(node+"\t");
			}
			System.out.println();
			return;
		}
		
		for(int i = index;i<=n;i++){
			ret.add(i);
			findFactor(n, sum-i, i+1, ret);
			ret.remove(ret.size()-1);
		}
		
	}
	
	public static void main(String[] args) {
		int n = 30;
		int m = 15;
		List<Integer> result = new ArrayList<Integer>();
		findFactor(n, m, 1,result);
	}
}
