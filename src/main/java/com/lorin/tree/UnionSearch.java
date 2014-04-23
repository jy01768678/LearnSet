package com.lorin.tree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 并查集应用实例
 * @author Administrator
 *
 */
public class UnionSearch {

    int numberOfelements = 0;
    
    int MAX = 200;

    int tree[] = new int[MAX];

    int number[] = new int[MAX];

    public UnionSearch(int numberOfelements) {
        this.numberOfelements = numberOfelements;

        for (int i = 0; i <= numberOfelements; i++) {
            // 初始化父节点为自己
            tree[i] = i;
            // 初始化为1
            number[i] = 0;
        }
    }
    
    /**
     * 查找x元素所在的集合,回溯时压缩路径
     * @param x
     * @return
     */
    public int father(int x) {
        if(tree[x] == x) {
            return x;
        }
        number[x] += number[tree[x]];
        tree[x]=father(tree[x]);
        return tree[x];
    }
    

    /**
     * 根据权重值进行比较 都合并到大的number上
     * @param a
     * @param b
     */
    public void union(int a, int b) {
        tree[a]=b;
        number[a]=(Math.abs(a-b)%1000);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= numberOfelements; i++) {
            sb.append(tree[i]).append("(").append(number[i]).append(") ");
        }
        return sb.toString();
    }
    /**1(1) 2(1) 3(1) 4(1) 5(1) 6(1) 7(1) 8(1) 9(1) 10(1) 
     * 10 7
        2 4
        5 7
        1 3
        8 9
        1 2
        5 6
        2 3
     * @param args
     */
    public static void main(String[] args) {
            UnionSearch us = new UnionSearch(10);
        
        try {           
            BufferedReader reader = new BufferedReader(new FileReader("d://input.txt"));
            String line = null;
            while((line=reader.readLine())!=null){
                String[] ns = line.split("\\s");
                int e1 = Integer.parseInt(ns[0]);
                int e2 = Integer.parseInt(ns[1]);
                
                e1 = us.father(e1);
                e2 = us.father(e2);
                if(e1!=e2){
                    us.union(e1, e2);
                }
                System.out.println(us.toString());
            }
            
            int e3 = us.father(3);
            int e4 = us.father(4);
            System.out.println(e3==e4?"Found":"Not found");
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
