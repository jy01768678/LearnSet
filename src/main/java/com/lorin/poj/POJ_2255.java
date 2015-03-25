package com.lorin.poj;

public class POJ_2255 {

	public static void main(String[] args) {
		String preStr = "DBACEGF";
		String midStr = "ABCDEFG";
		POJ_2255 poj2255 = new POJ_2255();
		
		TreeNode root = poj2255.buildTree(preStr,midStr);
	}
	
	private TreeNode buildTree(String preStr, String midStr) {
		TreeNode root = null;
		if(preStr.length() > 0){
			root = new TreeNode();
			root.setValue(preStr.charAt(0));
			int index = midStr.indexOf(preStr.charAt(0));
			root.setLeft(buildTree(preStr.substring(1, index), midStr.substring(0, index)));
			root.setRight(buildTree(preStr.substring(index+1), midStr.substring(index+1)));
		}
		return root;
	}
	
	private void postTraverse(TreeNode root){
		if(root != null){
			postTraverse(root.getLeft());
			postTraverse(root.getRight());
			System.out.println(root.getValue());
		}
	}

	class TreeNode{
		private char value;
		private TreeNode left;
		private TreeNode right;
		
		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}

		public TreeNode getLeft() {
			return left;
		}
		public void setLeft(TreeNode left) {
			this.left = left;
		}
		public TreeNode getRight() {
			return right;
		}
		public void setRight(TreeNode right) {
			this.right = right;
		}
	}
}
