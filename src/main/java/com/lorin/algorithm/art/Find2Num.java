package com.lorin.algorithm.art;

import java.util.List;

public class Find2Num {
	
	public static int[] hollandFlag(int[] data){
		
		return null;
	}
	
	/**
	 * 最大乘积连续子串
	 * @param data
	 * @return
	 */
	public static double[] maxLargeSubMultiStr(double[] data){
		double[] maxC = new double[data.length];
		double[] minC = new double[data.length];
		double maxValue = maxC[0] = minC[0] = data[0];
		for(int i=1;i<data.length;i++){
			maxC[i] = Math.max(Math.max(data[i], maxC[i-1]*data[i]), maxC[i]*data[i]);
			minC[i] = Math.min(Math.min(data[i], minC[i-1]*data[i]), minC[i]*data[i]);
			maxValue = Math.max(maxValue, maxC[i]);
		}
		System.out.println("最大乘积为："+maxValue);
		return maxC;
	}
	
	/**
	 * 不改变正负数之间相对顺序重新排列数组.时间O(N)，空间O(1)
	 * @param data
	 * @return
	 */
	public static int[] mergeSort(int[] data){
		
		int startIndex = 0;
		int endIndex = 0;
		int temp = 0;
		boolean exchange = false;
		for(int i=data.length-1;i>=0;i--){
			if(data[i] < 0 && endIndex == 0){
				endIndex=i;
			}
			if(endIndex != 0 && data[i]>0){
				startIndex = i;
				exchange = true;
			}
			if(exchange && (endIndex - startIndex == 1)){
				temp = data[endIndex];
				data[endIndex] = data[startIndex];
				data[startIndex] = temp;
				endIndex = startIndex;
				startIndex = 0;
				exchange = false;
			}else if(exchange && (endIndex - startIndex > 1)){
				temp = data[startIndex];
				for(int j=startIndex;j<=endIndex;j++){
					data[j] = data[j+1];
				}
				data[endIndex] = temp;
				endIndex = endIndex-1;
				startIndex = 0;
				exchange = false;
			}
		}
		
		return data;
	}
	
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
//		List<Integer> result = new ArrayList<Integer>();
//		findFactor(n, m, 1,result);
//		int[] data = new int[]{1, 7, -5, 9, -12, 15 };
//		int[] data = new int[]{1, 7, -5, -6, 9, -12, 15,-16};
//		int[] result = mergeSort(data);
		double[] data = new double[]{ -2.5,4,0,3,0.5,8,-1};
		double[] result = maxLargeSubMultiStr(data);
		for(int i=0;i<result.length;i++){
			System.out.print(result[i] + "\t");
		}
	}
}
