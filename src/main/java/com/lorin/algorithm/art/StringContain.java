package com.lorin.algorithm.art;

import java.math.BigInteger;

public class StringContain {

	public static int[] primeNumber = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
			37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101 };
	
	
	public static boolean primeMulti(String longStr,String shortStr){
		BigInteger product = new BigInteger("1");
		int index = 0;
		//遍历长字符串
		for(int i=0;i<longStr.length();i++){
			index = longStr.charAt(i)-'A';
			product = product.multiply(new BigInteger(String.valueOf(primeNumber[index])));
		}
		//遍历短字符串
		int j = 0;
		for(;j<shortStr.length();j++){
			index = shortStr.charAt(j)-'A';
			if(product.mod(new BigInteger(String.valueOf(primeNumber[index]))).intValue() !=0 ){
				break;
			}
		}
		if(j == shortStr.length()){
			return true;
		}
		return false;
	}
	
	public static boolean hashCount(String longStr,String shortStr){
		
		
		return false;
	}
	
	public static void main(String[] args) {
		String strOne = "ABCDEFGHLMNOPQRS";  
	    String strTwo = "DCGSRQPOM";  
	    primeMulti(strOne, strTwo);
	}
}
