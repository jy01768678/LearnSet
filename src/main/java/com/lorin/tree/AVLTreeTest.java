package com.lorin.tree;

import java.util.Iterator;
import java.util.Random;

public class AVLTreeTest {
	public static void main(String[] args) {
		AVLTree myTree = new AVLTree();
		Random random = new Random();
		System.out.print("随机产生的节点为：");
		int num = 0;
		//直到树的节点数为n止
		while (myTree.size() < 10) {
			num = new Integer(random.nextInt(100));
			myTree.add(num);
			System.out.print(num + " ");
		}
		System.out.println("");
		if (myTree.isAVL()) {
			System.out.println("这棵平衡二叉树的总节点数：" + myTree.size());
			System.out.println("这棵平衡二叉树的高度是：" + myTree.height());
			System.out.println("在树中查找 " + num + " 节点:"
					+ myTree.contains(new Integer(num)));
			System.out.println("在树中查找 100 节点:" + myTree.contains(new Integer(100)));
			System.out.print("中序遍历：");
			Iterator itr = myTree.iterator();
			while (itr.hasNext()) {
				System.out.print(itr.next() + " ");
			}
			System.out.println("");

			myTree.remove(num);
			System.out.print("删除节点 " + num + " 后遍历：");
			itr = myTree.iterator();
			while (itr.hasNext()) {
				System.out.print(itr.next() + " ");
			}
			System.out.println("");

			System.out.println("使用迭代器删除所有节点");
			itr = myTree.iterator();
			while (itr.hasNext()) {
				itr.next();
				itr.remove();
			}
			System.out.println("删除后树的总节点数：" + myTree.size());
		} else {
			System.out.println("failure");
		}
	}
}
