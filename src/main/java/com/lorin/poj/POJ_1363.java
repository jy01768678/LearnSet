package com.lorin.poj;

import java.util.Scanner;

public class POJ_1363 {

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		int[] in = new int[1001];
		int[] out = new int[1001];
		int[] stack = new int[1001];
		int i,j,n;
		for(i=0;i<1001;i++){
			in[i] = i+1;
		}
		while(scan.hasNext()){
			n = scan.nextInt();
			if(n == 0) break;
			while(true){
				out[0] = scan.nextInt();
				if(out[0] == 0)break;
				for(i = 1;i<n;i++){
					out[i] = scan.nextInt();
				}
				i = j = 0;
				int top = -1;
				while(i < n){
					top++;
					stack[top] = in[i];
					i++;
					while(stack[top] == out[j]){//判断开始进栈的 元素 是否等于 开始出栈的元素 如果是逐一判断
						top--;
						j++;
						if(top == -1)break;
					}
				}
				if(top == -1){
					System.out.println("Yes \n");
				}else {
					System.out.println("No \n");
				}
			}
		}
	}
}
