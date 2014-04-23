package com.lorin.tree;

import java.util.ArrayList;


public class Tarjan {

    public static void MAKE_SET(Noder p){
        p.ancester = p;
    }
    public static void UNION(Noder p,Noder q){
        q.ancester = p;   
    }
    public static Noder FIND(Noder p){
        Noder r = p;
        for(;r.ancester!=r;r = r.ancester);
        return r;
    }
    public static void LAC(Noder p, ArrayList<Queryer> q){
        MAKE_SET(p);
        for(Noder i : p.childs){
            LAC(i, q);
            UNION(p, i);
            FIND(p).ancester = p;
        }
        p.checked = true;
        for(Queryer query : q){
            if(query.a == p){
                if(query.b.checked){
                    query.result = FIND(query.b);
                }
            } else if(query.b == p){
                if(query.a.checked){
                    query.result = FIND(query.a);
                }
            } else {
                continue;
            }
        }
    }
}

class Noder{
    int id;
    Noder ancester;
    boolean checked;
    ArrayList<Noder> childs;
    public Noder(int id,Noder parent){
        this.id = id;
        if(parent != null){
            parent.childs.add(this);
        }
        this.checked = false;
        this.ancester = null;
        this.childs = new ArrayList<Noder>();
    }
}

class Queryer{
    public Queryer(Noder a, Noder b){
        this.a = a;
        this.b = b;
        this.result = null;
    }
    Noder a;
    Noder b;
    Noder result;
}