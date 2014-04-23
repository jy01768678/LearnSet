package com.lorin.tree;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/**
 * 动物王国中有三类动物A,B,C，这三类动物的食物链构成了有趣的环形。A吃B， B吃C，C吃A。 
 * 现有N个动物，以1－N编号。每个动物都是A,B,C中的一种，但是我们并不知道它到底是哪一种。 
 * 有人用两种说法对这N个动物所构成的食物链关系进行描述： 
 * 第一种说法是"1 X Y"，表示X和Y是同类。 
 * 第二种说法是"2 X Y"，表示X吃Y。 
 * 此人对N个动物，用上述两种说法，一句接一句地说出K句话，这K句话有的是真的，有的是假的。当一句话满足下列三条之一时，这句话就是假话，否则就是真话。 
 *1） 当前的话与前面的某些真的话冲突，就是假话； 
 * 2） 当前的话中X或Y比N大，就是假话； 
 *3） 当前的话表示X吃X，就是假话。 
 *你的任务是根据给定的N（1 <= N <= 50,000）和K句话（0 <= K <= 100,000），输出假话的总数
 *http://hi.baidu.com/lewutian/blog/item/3d55abf3aa03525d352acc8c.html
 *http://apps.hi.baidu.com/share/detail/16546869
 *http://www.cnblogs.com/wuyiqi/archive/2011/08/24/come__in.html    
 */
public class foodChain {

    private int p[];

    private int r[];

    public foodChain(int n) {
        p = new int[n + 1];
        r = new int[n + 1];
        
        for(int i=0;i<=n;i++){
            p[i] = i;
            r[i] = 0;
        }
    }
    
   // ffx   
   // |   \
   // fx   \
   // |    /
   // |   /
   // |  /
   // x /   
    public int find(int x){
        if(p[x] == x){
            return x;
        }
        int temp_px = find(p[x]);
        r[x] = ( r[temp_px] + r[x] ) % 3;
        p[x] = temp_px;
        return p[x];
    }
    
    public void union_set(int rx , int ry , int x , int y , int d){
        p[ry] = rx;
        r[ry] = ( r[x] - r[y] + 3 + d ) % 3;    //同上。这两个关系的推得实际上是这道题的关键所在。
    }

    /**
     * 不妨继续假设，x的当前集合根节点tx，y的当前集合根节点ty，x->y的偏移值为d-1（题中给出的询问已知条件）
        (1)如果tx和ty不相同，那么我们把ty合并到tx上，并且更新deltx[ty]值（注意：deltx[i]表示i的当前集合根节点到i的偏移量！！！！）
                    此时 tx->ty = tx->x + x->y + y->ty，可能这一步就是所谓向量思维模式吧
                    上式进一步转化为：tx->ty = (deltx[x]+d-1+3-deltx[y])%3 = deltx[ty]，（模3是保证偏移量取值始终在[0,2]间）
        (2)如果tx和ty相同，那么我们就验证x->y之间的偏移量是否与题中给出的d-1一致
                    此时 x->y = x->tx + tx->y = x->tx + ty->y，
                    上式进一步转化为：x->y = (3-deltx[x]+deltx[y])%3，
                    若一致则为真，否则为假。
     * @param x
     * @param y
     * @param d
     * @return
     */
    public int check(int x,int y,int d){
        int fs = 0;
        int rx = 0;
        int ry = 0;
        rx = find(x);
        ry = find(y);
        if ( rx == ry ){//可以确定X与Y的关系，也就可以判断此话的真伪。
            if((r[y]-r[x]+3)%3 != d){
                System.out.println("|d:"+d+"|x:"+x+"|y:"+y);
                return 1;
            } else {
                return 0;
            }
        }else
            union_set(rx , ry , x , y , d );
        return fs;
    }
    public static void main(String[] args) throws IOException {
        int k = 7;
        int fs = 0;
        
        String[] line = null;
        int n = 100;
        foodChain fc = new foodChain(n);
        Scanner scan = new Scanner(new BufferedInputStream(System.in));
        line = scan.nextLine().split("\\s");
        n = Integer.parseInt(line[0]);
        k = Integer.parseInt(line[1]);
        while (k > 0) {
            k--;
            String[] rl = scan.nextLine().split("\\s");
            int d = Integer.parseInt(rl[0]);
            int x = Integer.parseInt(rl[1]);
            int y = Integer.parseInt(rl[2]);
            if ( x > n || y > n || ( d == 2 && x == y )){
                System.out.println("|d:"+d+"|x:"+x+"|y:"+y);
                  fs++;
                  continue;
            }
            fs = fs + fc.check(x, y, d-1);
        }
        System.out.println(fs);
    }
}
