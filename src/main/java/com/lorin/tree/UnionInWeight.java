package com.lorin.tree;

/**
 * 并查集练习
 * @author Administrator
 *
 */
public class UnionInWeight {

    int numberOfelements = 0;
    
    int[] tree;
    
    int[] number;
    
    public UnionInWeight(int numberOfelements){
        
        this.numberOfelements = numberOfelements;
        
        tree = new int[numberOfelements + 1];
        
        number = new int[numberOfelements + 1];
        
        for(int i=1;i<numberOfelements;i++){
            tree[i] = i;
            number[i] = 1;
        }
    }
    
    public int father(int x){
        if(tree[x] != x){
            tree[x] = father(tree[x]);
        }
        return tree[x];
    }
    
    public void union(int a, int b) {
        if(tree[a] > tree[b]){
            tree[b] = a;
            number[a] = number[a] + number[b];
        } else {
            tree[a] = b;
            number[b] = number[a] + number[b];
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        UnionInWeight test = new UnionInWeight(10);
        int e3 = test.father(3);
        int e4 = test.father(4);
        if(e3!=e4){
            test.union(e3, e4);
        }
        System.out.println(e3==e4?"Found":"Not found");
    }

}
