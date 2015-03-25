package com.lorin.poj;

import java.util.Scanner;

public class POJ_3349 {
	
	public static int H = 1200007;
	
	public static int[] hashTable = new int[H];
	
	public static int N = 1200010;

	public static Node node[] = new Node[N];
	
	public static int cur = 0;
	
	
	public static void initHash(){
		for(int i=0;i< H ;++i){
			hashTable[i] = -1;
		}
	}
	
	public static void main(String[] args){
		int num[][] = new int[2][12];
		int n=2;
		boolean twin = false;
		initHash();
		while(n!=0){
			n--;
			Scanner scan = new Scanner(System.in);
			for(int i=0;i<6;i++){//顺时针的所有组合
				num[0][i] = scan.nextInt();
				num[0][i+6] = num[0][i];
			}
			if(twin)continue;
			for(int i=0;i<6;i++){//逆时针的所有组合
				num[1][6] = num[1][i] = num[0][5-i];
			}
			for(int i=0;i<6;i++){//i从0开始 每次取6个做判断
				if(searchHash(num[0],i)  || searchHash(num[1],i)){
					twin = true;
					break;
				}
			}
		}
	}
	
	private static int getHash(int[] num,int index){
		int hash = 0;
		for(int i=0;i<6;i++){
			hash += num[i];
		}
		return hash % H ;
	}
	
	private static boolean searchHash(int[] num,int index) {
		int h = getHash(num, index);
		int next = hashTable[h];
		while(next != -1){
			if(cmp(num,node[next].getNum())) return true;
			next = node[next].getNext();
		}
		insertHash(num,h);
		return false;
	}

	private static void insertHash(int[] num, int h) {
		for(int i=0;i<6;i++){
			node[cur].getNum()[i] = num[i];
		}
		node[cur].setNext(hashTable[h]);
		hashTable[h] = cur;
		++cur;
	}

	private static boolean cmp(int[] num1, int[] num2) {
		for(int i=0;i<6;i++){
			if(num1[i] != num2[i]) return false;
		}
		return true;
	}

	class Node{
		private int[] num;
		private int next;
		public Node(){
			num = new int[6];
			next = 0;
		}
		public int[] getNum() {
			return num;
		}
		public void setNum(int[] num) {
			this.num = num;
		}
		public int getNext() {
			return next;
		}
		public void setNext(int next) {
			this.next = next;
		}
		
		
	}
}
