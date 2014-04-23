package com.lorin.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Tree {

    private int data;// 数据节点    

    private Tree left;// 左子树    

    private Tree right;// 右子树
    
    private int maxLeft;
    
    private int maxRight;
    
    private int maxLen;

    public Tree(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    
    /**
     * 创建二叉树，返回根结点
     * 
     * @param input
     * @return
     */
    public static Tree createTree(int[] input) {
        Tree root = null;
        Tree temp = null;
        for (int i = 0; i < input.length; i++) {
            // 创建根节点    
            if (root == null) {
                root = temp = new Tree(input[i]);
            } else {
                // 回到根结点    
                temp = root;
                // 添加节点    
                while (temp.data != input[i]) {
                    if (input[i] <= temp.data) {
                        if (temp.left != null) {
                            temp = temp.left;
                        } else {
                            temp.left = new Tree(input[i]);
                        }
                    } else {
                        if (temp.right != null) {
                            temp = temp.right;
                        } else {
                            temp.right = new Tree(input[i]);
                        }
                    }
                }
            }
        }
        return root;
    }
    
    public static Tree createTree1(int[] input){
        Tree root = null;
        for(int i=0;i<input.length;i++){
            if(root == null){
                root = new Tree(input[i]);
            } else {
                insert(root, input[i]);
            }
        }
        return root;
    }
    //-------------------------------------------
    public static Tree createTree2(int[] input){
        Tree root = null;
        for(int i=0;i<input.length;i++){
            if(root == null){
                root = new Tree(input[i]);
            } else {
                insert(root,input[i]);
            }
        }
        return root;
    }
    
    public static void insert(Tree node,int element){
        if(node == null || node.data == element){
            return;
        }
        if(node.data > element){
            if(node.left == null){
                Tree temp = new Tree(element);
                node.left = temp;
            } else {
                insert(node.left, element);
            }
        } else if(node.data < element){
            if(node.right == null){
                Tree temp = new Tree(element);
                node.right = temp;
            } else {
                insert(node.right, element);
            }
        }
    }
    public static void insert1(Tree root,int element){
        if(root == null || root.data == element){
            return ;
        }
        if(root.data > element){
            if(root.left != null){
                Tree temp = new Tree(element);
                root.left = temp;
            } else {
                insert1(root.left, element);
            }
        } else {
            if(root.right == null){
                Tree temp = new Tree(element);
                root.right = temp;
            } else {
                insert1(root.right, element);
            }
        }
    }
    
    /**
     * 前序遍历
     * 
     * @param tree
     */
    public static void preOrder(Tree tree) {
        if (tree != null) {
            System.out.print(tree.data + " ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }
    public static void preOrder1(Tree tree){
        if(tree != null){
            System.out.println(tree.data + " ");
            preOrder1(tree.left);
            preOrder1(tree.right);
        }
    }
    
    
    public static void stackPreOrder(Tree tree){
        Stack stack = new Stack();
        if(tree == null)return;
        stack.push(tree);
        System.out.println(tree.data + "\t");
        Tree node = tree.left;
        while(node != null){
            stack.push(node);
            System.out.println(node.data + "\t");
            node = node.left;
        }
        node = (Tree) stack.pop();
        while(node != null){
            node = node.right;
            while(node != null){
                stack.push(node);
                System.out.println(node.data + "\t");
                node = node.left;
            }
            if (stack.empty())
                break;
            node = (Tree) stack.pop();
        }
    }
    
    public static void stackPreOrder1(Tree tree){
        Stack stack = new Stack();
        if(tree == null)return;
        stack.push(tree);
        System.out.println(tree.data);
        Tree node = tree.left;
        while(node != null){
            stack.push(node);
            System.out.println(node.data + "\t");
            node = node.left;
        }
        node = (Tree) stack.pop();
        while(node != null){
            node = node.right;
            while(node != null){
                stack.push(node);
                System.out.println(node.data);
                node = node.left;
            }
            if (stack.empty())
                break;
            node = (Tree) stack.pop();
        }
        
        
    }
    
    /**
     * 中序遍历
     * 
     * @param tree
     */
    public static void midOrder(Tree tree) {
        if (tree != null) {
            midOrder(tree.left);
            System.out.print(tree.data + " ");
            midOrder(tree.right);
        }
    }
    public static void minOrder1(Tree tree){
        if(tree != null){
            midOrder(tree.left);
            System.out.println(tree.data + " ");
            midOrder(tree.right);
        }
    }
    
    public static void stackInOrder(Tree root) {
        Stack stack = new Stack();
        if (root == null)
            return;
        else
            stack.push(root);
        Tree temp = root.left;
        while (temp != null) {
            stack.push(temp);
            temp = temp.left;
        }
        temp = (Tree) stack.pop();
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.right;
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;
            }
            if (stack.empty())
                break;
            temp = (Tree) stack.pop();
        }
    }

    public static void midOrder1(Tree tree){
        if(tree != null){
            midOrder1(tree.left);
            System.out.println(tree.data);
            midOrder1(tree.right);
        }
    }
    /**
     * 后序遍历
     * 
     * @param tree
     */
    public static void posOrder(Tree tree) {
        if (tree != null) {
            posOrder(tree.left);
            posOrder(tree.right);
            System.out.print(tree.data + " ");
        }
    }
    
    //找出最小节点
    public static int findMin(Tree root){
        if(root.left == null){
            return root.data;
        } else {
            return findMin(root.left);
        }
    }
    
    //找出指定节点下的节点
    public static int findElementMin(int element,Tree root){
        if(element < root.data){
            if(root.left == null)return root.data;
            return findElementMin(element, root.left);
        } else if(element > root.data){
            if(root.right == null)return root.data;
            return findElementMin(element, root.right);
        } else {
            return root.data;
        }
    }
    
    public static Tree add(int element,Tree root){
        if(element < root.data){
            if(root.left == null){
                return root.left = new Tree(element);
            } else {
                add(element, root.left);
            }
        } else if(element > root.data){
            if(root.right == null){
                return root.right = new Tree(element);
            } else {
                add(element, root.right);
            }
        }
        return root;
    }
    
    //删除指定节点
    public static Tree delete(int element,Tree root){
        if(element < root.data){
            root.left = delete(element, root.left);
        } else if(element > root.data){
            root.right = delete(element, root.right);
        } else {
            if(root.left != null && root.right != null){
                int temp = findMin(root.right);
                root.data = temp;
                root.right = delete(root.data, root.right);
            } else {
                if(root.left == null){
                    root = root.right;
                } else if(root.right == null){
                    root = root.left;
                }
            }
        }
        return root;
    }

    //二元查找树转变成排序的双向链表
    public static Tree convertTree(Tree node,boolean asRight){
        Tree pLeft = null;
        Tree pRight = null;
        
        if(node.left != null){
            pLeft = convertTree(node.left,false);
        }
        
        if(pLeft != null){
            pLeft.right = node;
            node.left = pLeft;
        }
        
        if(node.right != null){
            pRight = convertTree(node.right, true);
        }
        
        if(pRight != null){
            pRight.left = node;
            node.right = pRight;
        }
        
        Tree pTemp = node;
        if(asRight){
              while(pTemp.left != null)
                    pTemp = pTemp.left;
        } else {
              while(pTemp.right != null)
                    pTemp = pTemp.right;
        }
        return pTemp;
    }
    
    public static void FindPath(Tree node,int element,List<Integer> pathList,int currentSum){
        currentSum += node.data;
        pathList.add(node.data);
        
        boolean isLeaf = (node.left == null && node.right == null);
        if(currentSum == element && isLeaf){
            System.out.print("[");
            for(int a : pathList){
                System.out.print(a + "\t");
            }
            System.out.print("]");
        }
        if(node.left != null){
            FindPath(node.left, element,pathList,currentSum);
        }
        if(node.right != null){
            FindPath(node.right, element,pathList,currentSum);
        }
        
        currentSum -= node.data;
        pathList.remove(pathList.size()-1);
    }
    
    /**
     * 以下是非递归的广度遍历
     * 递归的广度遍历：需要变一下levelOrder(Queue queue){for();levelOrder();
     * @param root
     */
    public static void levelOrder(Tree root){
        Tree p = root;
        if(p != null){
            Queue<Tree> queue = new LinkedList<Tree>();
            queue.offer(p);
            while(!queue.isEmpty()){
                p = queue.poll();
                System.out.print(p.data +"\t");
                if(p.left != null){
                    queue.offer(p.left);
                }
                if(p.right != null){
                    queue.offer(p.right);
                }
            }
        }
    }
    
    //树的深度
    public static int getTreeHeight(Tree node){
        if(node == null){
            return 0;
        }
        if(node.left == null && node.right == null){
            return 1;
        }
        return 1 + Math.max(getTreeHeight(node.left), getTreeHeight(node.right));
    }
    
    //判断给定序列是否为二叉排序树的 后续排列
    public static boolean verifySquenceOfBST(int[] squence,int length){
        
        if(squence.length == 0 || length <=0) return false;
        int root = squence[length - 1];
        int i = 0;
        for(; i < length - 1; ++ i){
              if(squence[i] > root)
                    break;
        }
        int j = i;
        for(; j < length - 1; ++ j){
              if(squence[j] < root)
                    return false;
        }
        boolean left = true;
        if(i > 0)
              left = verifySquenceOfBST(squence, i);

        // verify whether the right sub-tree is a BST
        boolean right = true;
        if(i < length - 1)//squence  +i
              right = verifySquenceOfBST(squence, length - i - 1);

        return (left && right);
    }
    
    //二元查找树的镜像
    public static void MirrorRecursively(Tree node){
        if(node.left == null && node.right == null) return;
        node.left.data^=node.right.data^=node.left.data^=node.right.data;
        
        if(node.left != null){
            MirrorRecursively(node.left);
        }
        if(node.right != null){
            MirrorRecursively(node.right);
        }
    }
    
    /**
     *最低公共父节点 
     */
    public static Tree lca = null;
    public static int lowestCommonAncestor(Tree root,Tree node1,Tree node2){
        if(root == null)return 0;
        if(root.data == node1.data || root.data == node2.data)return 1;
        int num = lowestCommonAncestor(root.left,node1, node2) + lowestCommonAncestor(root.right, node1, node2);
        if(num == 2 && lca == null){
            lca = root;
        } else if(num == 1 && lca == null){
            lca = root;
        }
        return num;
    }
    
    /**
     * 树的子结构
     */
    public static boolean HasSubTree(Tree tree1,Tree tree2){
        
        if(tree1 == null && tree2 != null || tree1 != null && tree2 == null)return false;
        if(tree1 == null && tree2 == null)return true;
        return HasTreeCore(tree1,tree2); 
    }
    
    private static boolean HasTreeCore(Tree tree1, Tree tree2) {
        boolean result = false;
        if(tree1.data == tree2.data){
            result = DoesTree1HaveAllNodesOfTree(tree1,tree2);
        }
        if(!result && tree1.left != null){
            result = HasTreeCore(tree1.left, tree2);
        }
        if(!result && tree1.right != null){
            result = HasTreeCore(tree1.right, tree2);
        }
        return result;
    }
    private static boolean DoesTree1HaveAllNodesOfTree(Tree tree1, Tree tree2) {
        if(tree2 == null)return true;
        if(tree1 == null)return false;
        if(tree1.data != tree2.data)return false;
    
        return DoesTree1HaveAllNodesOfTree(tree1.left, tree2.left) &&
                DoesTree1HaveAllNodesOfTree(tree1.right, tree2.right);
    }
    
    /**
     * 判断二叉树是不是平衡 用c更简单明了 后序遍历  记录深度
     */
    public static int IsBalancedTree(Tree root,int dept){
        if(root == null){
           return dept = 0;
        }
        int left = 0,right = 0;
        int diff = IsBalancedTree(root.left, left)-IsBalancedTree(root.right, right);
        if(diff <=1 && diff >= -1){
            dept = 1 + (left > right?left:right);
            return dept;
        } 
        return dept;
    }
    //方法二
    public boolean isBSTHelper(Tree node,int low,int high){
        if (node == null) return true;
        if(node.data < high && node.data > low){
            return isBSTHelper(node.left, low, node.data) && isBSTHelper(node.right, node.data, high);
        }
        return false;
    }
    public boolean isBST(Tree root) {
        return isBSTHelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    /**
     * 把一个排好序的数组数组转化成一颗平衡二叉查询树 
     */
    public static Tree sortedArrayToBST(int array[],int start,int end){
        int mid = start + (end - start) / 2;
        Tree root = new Tree(array[mid]);
        root.left = sortedArrayToBST(array, start, mid - 1);
        root.right = sortedArrayToBST(array, mid  +1, end);
        return root;
    }
    
    /**
     * 二叉树中任意两个节点间的最大距离
     */
    public static void FindMaxLen(Tree root){
        if(root == null){
            return ;
        }
        
        if(root.left == null){
            root.maxLeft = 0;
        } else {
            FindMaxLen(root.left);
            int tempMax = 0;
            if(root.left.maxLeft > root.left.maxRight){
                tempMax = root.left.maxLeft;
            } else {
                tempMax = root.left.maxRight;
            }
            root.maxLeft = tempMax + 1;
        }
        
        if(root.right ==null){
            root.maxRight = 0;
        } else {
            FindMaxLen(root.right);
            int tempMax = 0;
            if(root.right.maxLeft > root.right.maxRight){
                tempMax = root.right.maxLeft;
            } else {
                tempMax = root.right.maxRight;
            }
            root.maxRight = tempMax + 1;
        }
        
        if(root.maxLen + root.maxRight > root.maxLen){
            root.maxLen = root.maxLen + root.maxRight;
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] input = { 4, 2, 6, 1, 3, 5, 7,31,100,66,19,20,51,245,105,44,58,101,71 };
        int[] input2 = {10,12,5,4,7};
        int[] input3 = {8,4,12,2,6,10,14,1,3,5,7,9,11,13,15};
        Tree tree = createTree1(input3);
        //System.out.println(findMin(tree));
        //System.out.println(findElementMin(31, tree));
        //add(111, tree);
        //System.out.println("root : "+tree.data);
        //FindPath(tree, 22, new ArrayList<Integer>(), 0);
        //System.out.println("-----广度遍历----");
        //levelOrder(tree);
        //System.out.print("前序遍历：");
        //preOrder(tree);
        //System.out.print("\n中序遍历：");
        //midOrder(tree);
        //System.out.print("\n后序遍历：");
        //posOrder(tree);
    }

}
