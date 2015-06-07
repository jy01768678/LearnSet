package com.lorin.poj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class POJ_1700 {

	public static void main(String[] args) {
		List<Integer> num = new ArrayList<Integer>();
		num.add(5);
		num.add(1);
		num.add(4);
		num.add(10);
		Collections.sort(num);
		int sum = 0;
		for(int i = num.size()-1;i>2;i-=2){
			if(num.get(0) + 2 * num.get(1) + num.get(i) > 
			   2 * num.get(0) + num.get(i-1) + num.get(i)){
				sum += 2 * num.get(0) + num.get(i-1) + num.get(i);
			}else {
				sum += num.get(0) + 2 * num.get(1) + num.get(i) ;
			}
			if(i == 2){
				sum += num.get(0) + num.get(1)+num.get(2);
			}else if(i == 1){
				sum += num.get(1);
			}else {
				sum+= num.get(0);
			}
		}
		System.out.println(sum);
	}
}
