package com.lorin.tree;


public class TreapTree<E extends Comparable<E>> {

    private TreapNode<E> root = null;
    
    private static class TreapNode<E>{
        E elem;
        public int priority;
        
    }
}
