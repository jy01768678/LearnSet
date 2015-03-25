package com.lorin.poj;

import java.util.Stack;

public class Google_stackMin {

	private Stack<Integer> stackData = new Stack<Integer>();
	private Stack<Integer> stackMin = new Stack<Integer>();
	
	public Stack<Integer> getStackData() {
		return stackData;
	}

	public void setStackData(Stack<Integer> stackData) {
		this.stackData = stackData;
	}

	public Stack<Integer> getStackMin() {
		return stackMin;
	}

	public void setStackMin(Stack<Integer> stackMin) {
		this.stackMin = stackMin;
	}

	public void push(int value){
		stackData.push(value);
		//辅助栈为空或者压入的元素小于辅助站的栈顶元素，则压入当前元素 ，否则压入栈顶元素
		if(stackMin.isEmpty() || stackMin.pop() > value){
			stackMin.push(value);
		}else {
			stackMin.push(stackMin.pop());
		}
	}
	
	public int pop(){
		int result = 0;
		if(stackData.size() > 0 && stackMin.size() > 0){
			stackMin.pop();
			result = stackData.pop();
		}
		return result;
	}
	
	public int min(){
		return stackMin.pop();
	}
}
