package com.lorin.algorithm;

public class ActivitationCode {

	private final static int N = 10;
	
	private static int result = 0;
	
	public static void main(String[] args) {
		int SUM = 20;
		int[] data = new int[]{1,2,3,4,5,6,7,8,9,10};
		int[] flag = new int[N];
		int result = codeResult(SUM,N-1,data,flag);
		System.out.println(result);
	}
	private static int codeResult(int sum, int n, int[] data,int[] flag) {
		if(sum == 0){
			result++;
			for(int i=0;i<flag.length;i++){
				if(flag[i] == 1){
					System.out.print(data[i] + "\t");
				}
			}
			System.out.println();
		}else if(sum > 0 && n>=0){
			flag[n] = 1;
			codeResult(sum-data[n], n-1, data, flag);
			flag[n] = 0;
			codeResult(sum, n-1, data, flag);
		}
		return result;
	}
}
