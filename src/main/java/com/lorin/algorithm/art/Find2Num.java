package com.lorin.algorithm.art;

import java.util.ArrayList;
import java.util.List;

public class Find2Num {
	
	/**
	 * 寻找水王
	 */
	public static int findWaterNum(int[] data){
		if(data.length == 0){
			return 0;
		}
		int result=data[0];
		int times = 1;
		for(int i=1;i<data.length;i++){
			if(data[i] == result){
				times++;
			}else {
				times--;
			}
			if(times == 0){
				result=data[i];
				times=1;
			}
		}
		times=0;
		for(int i=0;i<data.length;i++){
			if(data[i] == result){
				times++;
			}
		}
		if(times * 2 <= data.length){
			//正解了
		}
		return result;
	}
	
	/**
	 * 寻找数组中子数组的最大值
	 * @param data
	 * @return
	 */
	public static int findMaxSum(int[] data){
		int max = data[0];
		int sum = 0;
		for(int i=0;i<data.length;i++){
			if(data[i] > 0){
				sum+=data[i];
			}else {
				sum = data[i];
			}
			if(sum > max){
				max = sum;
			}
		}
		return sum;
	}

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
