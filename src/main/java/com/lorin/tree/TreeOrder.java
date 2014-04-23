package com.lorin.tree;


public interface TreeOrder<E extends Comparable<E>> {
    
    static interface Visitor<E extends Comparable<E>> {
        void vistor(E ele);
    }
    
    //前序遍历  
    void preOrder(Visitor<E> v);  
  
    //中序遍历  
    void inOrder(Visitor<E> v);  
  
    //后序遍历  
    void postOrder(Visitor<E> v);  
  
    //层次遍历  
    void levelOrder(Visitor<E> v);  
}
