package com.lorin.algorithm.art;

/**
 * 计算亲和数
 * @author cc
 *
 */
public class AmicablePair {

	public final static int n = 5000000;
	
	public static void main(String[] args) {
		int i,j=0;
		int[] nums = new int[n+1];
		for(i=1;i<=n;i++){
			nums[i] = 1;
		}
		for(i=2;i+i<=n;i++){
			j = i + i;
			while(j<=n){
				nums[j] += i;
				j+=i;
			}
		}
		
		for(i=2;i<=n;i++){
			if(nums[i] > i && nums[i] <= n && nums[nums[i]]==i){
				System.out.println(i+"\t" + nums[i]);
			}
		}
	}
}
