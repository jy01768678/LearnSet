package com.lorin.tree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LCA {

    static void lca(Node p, ArrayList<Query> q) {
        MAKE_SET(p);
        //FIND(p).ancester=p;
        for (Node i : p.childs) {
            lca(i, q);
            UNION(p, i);
            FIND(p).ancester = p;
        }
        p.checked = true;
        for (Query query : q) {
            if (query.p1 == p) {
                if (query.p2.checked) {
                    query.result = FIND(query.p2);
                }
            } else if (query.p2 == p) {
                if (query.p1.checked) {
                    query.result = FIND(query.p1);
                }
            } else {
                continue;
            }
        }
    }

    static void MAKE_SET(Node p) {
        p.ancester = p;
    }

    static Node FIND(Node p) {
        Node r = p;
        for (; r.ancester != r; r = r.ancester);
        return r;
    }

    static void UNION(Node p, Node q) {
        q.ancester = p;
    }

    public static void main(String args[]) {
        // create tree
        Node p[] = new Node[24];
        p[0] = new Node(0, null); // root
        p[1] = new Node(1, p[0]);
        p[2] = new Node(2, p[0]);
        p[3] = new Node(3, p[0]);
        p[4] = new Node(4, p[1]);
        p[5] = new Node(5, p[1]);
        p[6] = new Node(6, p[1]);
        p[7] = new Node(7, p[2]);
        p[8] = new Node(8, p[2]);
        p[9] = new Node(9, p[3]);
        p[10] = new Node(10, p[3]);
        p[11] = new Node(11, p[3]);
        p[12] = new Node(12, p[4]);
        p[13] = new Node(13, p[4]);
        p[14] = new Node(14, p[6]);
        p[15] = new Node(15, p[8]);
        p[16] = new Node(16, p[10]);
        p[17] = new Node(17, p[10]);
        p[18] = new Node(18, p[14]);
        p[19] = new Node(19, p[14]);
        p[20] = new Node(20, p[17]);
        p[21] = new Node(21, p[17]);
        p[22] = new Node(22, p[17]);
        p[23] = new Node(23, p[11]);

        // make lca query
        ArrayList<Query> q = new ArrayList<Query>();
        q.add(new Query(p[15], p[19]));
        q.add(new Query(p[21], p[16]));
        q.add(new Query(p[14], p[14]));
        q.add(new Query(p[4], p[23]));
        q.add(new Query(p[23], p[16]));

        // lca
        lca(p[0], q);

        // dump results
        for (Query item : q) {
            System.out.println(item.p1 + ":" + item.p2 + ": result is:" + item.result);
        }
    }
}

class Node {

    public Node(int id, Node parent) {
        this.id = id;
        if (parent != null) {
            parent.childs.add(this);
        } else {
            assert this.id == 0;
        }
        this.checked = false;
        this.ancester = null;
        this.childs = new ArrayList<Node>();
    }

    int id;

    ArrayList<Node> childs;

    public String toString() {
        return "Node:<" + id + ">";
    }

    Node ancester; // used for lca search

    boolean checked; // used for lca search
}

class Query {

    public Query(Node p1, Node p2) {
        assert p1 != null && p2 != null;
        this.p1 = p1;
        this.p2 = p2;
        this.result = null;
    }

    Node p1;

    Node p2;

    Node result;
}
