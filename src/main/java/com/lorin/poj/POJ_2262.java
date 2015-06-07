package com.lorin.poj;

public class POJ_2262 {

	public static boolean isprime(int s){
		boolean result = true;
		double t = Math.sqrt(s + 0.5);
		for(int i=2;i<=t;i++){
			if(s % i == 0){
				result = false;
				break;
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		int target = 8;
		
		int t = target / 2;
		
		for(int i = 3;i<=t;i+=2){
			if(isprime(i) && isprime(target - i)){
				System.out.println(i+","+(target-i));
			}
		}
	}
}
