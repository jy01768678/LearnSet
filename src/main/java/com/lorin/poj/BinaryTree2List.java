package com.lorin.poj;

public class BinaryTree2List {

    public void convert(BinaryTree root,BinaryTree pre){
    	
    	if(root == null){
    		return;
    	}
    	
    	BinaryTree current = root;
    	if(current.getLeft() != null){
    		convert(root.getLeft(),pre);
    	}
    	current.left = pre;
    	if(pre != null){
    		pre.left = current;
    	}else {
    		pre = current;
    	}
    	if(current.getRight() != null){
    		convert(root.getRight(),pre);
    	}
    }
	
	class BinaryTree{
		private int data;
		private BinaryTree left;
		private BinaryTree right;
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		public BinaryTree getLeft() {
			return left;
		}
		public void setLeft(BinaryTree left) {
			this.left = left;
		}
		public BinaryTree getRight() {
			return right;
		}
		public void setRight(BinaryTree right) {
			this.right = right;
		}
		
	}
}
