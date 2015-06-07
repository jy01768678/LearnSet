package com.lorin.poj;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class POJ_2786 {

	/**
	 * 返回需要减少的订单
	 * @param tasks
	 * @return
	 */
	public int greedy(List<Task> tasks){
		Queue<Integer> qp = new PriorityQueue<Integer>();
		int cur = 0, result = 0;
		for(int i=0;i<tasks.size();i++){
			qp.add(tasks.get(i).getNum());
			cur += tasks.get(i).getNum();
			if(cur > tasks.get(i).getTime()){
				result ++;
				cur-=qp.peek();
				qp.poll();
			}
		}
		
		return result;
	}
}

class Task{
	private int num;
	private int time;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}