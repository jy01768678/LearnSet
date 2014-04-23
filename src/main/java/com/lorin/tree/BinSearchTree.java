package com.lorin.tree;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;


public class BinSearchTree<E extends Comparable<E>> extends  AbstractSet<E> implements TreeOrder<E>{

    private static class Entry<E>{
        E elem;
        Entry<E> parent;
        Entry<E> left;
        Entry<E> right;
        
      //构造函数只有两个参数，左右节点是调用add方法时设置  
        public Entry(E elem, Entry<E> parent) {  
            this.elem = elem;  
            this.parent = parent;  
        }  
    }
    
    private Entry<E> root;
    
    private int size;
    
    public BinSearchTree() {  
        root = null;  
    }
    
    @Override
    public Iterator<E> iterator() {
        return new TreeItrator(); 
    }

    @Override
    public int size() {
        return size;
    }

    //中序遍历 
    @Override
    public void inOrder(Visitor<E> v) {
        // TODO Auto-generated method stub
        inOrder(root,v);
    }

    private void inOrder(Entry<E> p, Visitor<E> v) {
        // TODO Auto-generated method stub
        if(p == null){
            return ;
        }
        inOrder(p.left,v);
        v.vistor(p.elem);
        inOrder(p.right,v);
    }

    @Override
    public void levelOrder(Visitor<E> v) {
        // TODO Auto-generated method stub
        if(root == null){
            return;
        }
        LinkedList<Entry<E>> queue = new LinkedList<Entry<E>>();
        queue.addLast(root);
        while(!queue.isEmpty()){
            Entry<E> p = queue.removeFirst();
            v.vistor(p.elem);
            if(p.left != null){
                queue.add(p.left);
            }
            if(p.right != null){
                queue.add(p.right);
            }
        }
    }

    @Override
    public void postOrder(Visitor<E> v) {
        // TODO Auto-generated method stub
        postOrder(root,v);
    }

     private void postOrder(Entry<E> p, Visitor<E> v) {
        // TODO Auto-generated method stub
        if(p != null){
            postOrder(p.left,v);
            postOrder(p.right,v);
            v.vistor(p.elem);
        }
    }

    //前序遍历
    @Override
    public void preOrder(Visitor<E> v) {
        // TODO Auto-generated method stub
        preOrder(root,v);
    }

    private void preOrder(Entry<E> p, Visitor<E> v) {
        // TODO Auto-generated method stub
        if(p != null){
            v.vistor(p.elem);
            preOrder(p.left, v);
            preOrder(p.right,v);
        }
    }
    
    /** 
     * 是否含有某元素 
     * @param e 
     * @return boolean 
     */  
    public boolean contanins(E e) { 
        Entry<E> temp = root;
        int comp;
        while(temp != null){
            comp = e.compareTo(temp.elem);
          //如果与tmp元素相等，则返回
            if(comp == 0){
                return true;
            }
          //如果比tmp小，则在tmp的左子树中找
            if(comp < 0){
                temp = temp.left;
            }
          //如果比tmp大，则在tmp的右子树中找  
            if(comp > 0){
                temp = temp.right;
            }
        }
        return false;
    }
    
    /** 
     * 向二叉搜索树中添加节点 
     * 被插入的元素总是变成树中的叶结点，所以在插入元素后，没有必要重新组织树，这与AVL树或 
     * RED-BLACK树不太一样 
     * @param e 
     * @return boolean 
     */  
    public boolean add(E e) {  
      //1、如果树为空，则直接加入  
        if (root == null) {  
            root = new Entry<E>(e,null);
            size ++ ;
            return true;
        } else {//如果树不为空 
            Entry<E> temp = root;
            int comp;
            while(true){//死循环，直到节点插入到正确位置或元素已存在 
                comp = e.compareTo(temp.elem);
              //2、如果添加的元素e与tmp相等，则表示元素存在，直接返回失败
                if(comp == 0){
                    return false;
                } else if(comp < 0){//3、如果添加的元素e小于tmp节点，则要添加到tmp的左子树中的某个位置上
                    if(temp.left != null){
                        temp = temp.left;
                    }else {//如果tmp没有左节点，则或把新增元素设置成tmp的左子节点
                        temp.left = new Entry<E>(e,temp);
                        size++;
                        return true;
                    }
                } else {//4、否则在tmp的右子树中找添加位置  
                    //如果tmp的右子树为不为空，则还要继续找添加点  
                    if (temp.right != null) {  
                        temp = temp.right;  
                    }//如果tmp没有右子节点，则或把新增元素设置成tmp的右子节点  
                    else {  
                        temp.right = new Entry<E>(e, temp);  
                        size++;  
                        return true;  
                    }  
                }
            }
        }
    }
    
    /** 
     * 删除指定的数据域的元素 
     * @param p 
     * @return boolean 
     */  
    public boolean remove(E p) {  
      //根据数据域查找待删除的元素  
        Entry<E> tmp = getEntry(p);
        if (tmp == null) {//如果元素没有找到，则删除失败  
            return false;  
        } else {  
            //删除元素  
            deleteEntry(tmp);  
            return true;  
        }  
    }

    /** 
     * 删除指定的节点实现 
     *  
     * 算法思想： 
     *  
     * 1、若待删除的节点p是叶子节点，则直接删除该节点； 
     *  
     * 2、若待删除的节点p只有一个子节点，则将p的子节点与p的父节点直接连接，然后删除节点p； 
     * 为什么只有一个子节点时可以直接接到删除节点的父节点下面呢?因为只有一个子节点，直接接上 
     * 去不会影响排序子节点本身的排序，当然更不会影响另外一个子树（因为另一子树跟本不存在!）； 
     *  
     * 3、若待删除节点p有两个子节点时，应该使用中序遍历方式得到的直接前置节点S或直接后继节点s 
     * 的值来代替点s的值，然后删除节点s，（注：节点s肯定属于上述1、2情况之一）而不是直接删除 
     * p，这样可以将该删除问题转换成上面1、2问题； 
     *  
     * @param p 指向被删除的节点p 
     */  
    private void deleteEntry(Entry<E> p) {
        // TODO Auto-generated method stub
        //如果删除的节点p有左右子树时，将问题转换成删除叶子节点或只有一个子节点的节点问题  
        if (p.left != null && p.right != null) {  
            /* 
             * 删除有两个子节点的节点示例图(转换成删除只有一个子节点的节点或叶子节点问题)： 
             *  
             * p → 80         将直接后继元素s的elem替换p元素的          90          
             *     /\        elem，然后将p指向s，这样将问题转换        /\             
             *   15 110       成删除只有一个子节点的节点p问题了        15 110          
             *       /                                             /    
             *  s → 90                   →                 s、p → 90      
             *       \                                             \ 
             *      105                                           105 
             *  
             */  
             /* 
              * 查找待删除节点p的中序遍历节点的直接后继节点。注，该后继节点只可能是叶子节点 
              * （该叶子节点只可能是以下两种：一种是就是右子节点本身，因为可能待删除节点p的 
              * 右子节点没有左右子树了；第一种就是待删除节点p右子节点的左子树上最左边的叶子 
              * 节点），或只有一个右子节点的节点（该节点只可能是在上述第二种叶子节点上上多了 
              * 一个右子树罢了） 
              */  
             Entry<E> s = successor(p);  
             /* 
              * 当待删除的节点p的左右子树都存在时，我们不正真真删除p这个节点，而是用后继节点s 
              * 来替换p节点，具体作法就是什么后继节点的数据域elem替换待删除节点p的数据域elem， 
              * 替换之后，我们让真真待删除的节点p变成后继节点s（即让p指向s），因为这样将问题转 
              * 换成删除叶子节点或只有一个子节点（这个子节点有个特点就是左子树一定为空）的节点的 
              * 问题了，这样也就能共用下面真正的删除叶子节点与删除只有一个子节点的节点边辑了 
              */  
             p.elem = s.elem;//使用后继节点s的数据替换p的数据域  
   
             p = s;//让p指向直接后继节点s，这样删除p时实质上是删除的直接后节点
        }
        /* 
         * !! 注，程序运行到这里时，如果待删除的节点p左右子树都存在时，已被上面程序逻辑转换成了 
         * 删除叶子节点或只有一个子节点（一定是右子节点）的节点问题了，当然下面的删除逻辑还不只适 
         * 用于删除只有一个右子节点的节点，还适用于删除只有一个左子节点的节点，总之能适用于删除只 
         * 一个子节点的节点，而不管这个子节点是左还是有。  
         *  
         * 下面程序开始删除叶子节点或只有一个子节点的节点： 
         */  
  
        //若待删除的节点p是叶子节点，则直接删除该节点，无需用后继节点填补  
        if (p.left == null && p.right == null) {  
            /* 
            * 删除叶子节点示例图： 
            *  
            *         80               80 
            *         /\               /\ 
            *       20 110     →     20 110 
            *        \   /               / 
            *    p → 50 90              90 
            *            \               \ 
            *           105             105 
            *  
            * p指向要删除的节点50，要做的就是将50的父节点Entry对象（元素为20的Entry对象）的 
            * 右子节点修改为null 
            */  
            //若待删除的节点p是根元素，且又没有子节点时，直接删除释放节点  
            if (p.parent == null) {  
                root = null;  
            }//如果被删除的节点p为左叶子节点，则把父节点的左指针置为null  
            else if (p == p.parent.left) {  
                p.parent.left = null;  
            }//否则被删除的节点p为右叶子节点，则把父节点的右指针置为null  
            else {  
                p.parent.right = null;  
            }  
  
        }//否则删除的是只有一个子节点（不管左还是右都可）的节点时，则需使用后继节点填补  
        else {  
  
            /* 
            * !! 注，到此，p只有一个子节点，不可能即有左子节点同时又有右子节点，因为如果有， 
            * 前面逻辑也会把p转换成了只具有一个右子节点的节点 
            */  
  
            /* 
             * 删除只有一个子节点的元素示例图： 
             *  
             *          80               80 
             *          /\               /\ 
             *     p → 20 110     →    50 110 
             *          \  /               / 
             *    rep → 50 90             90 
             *              \              \ 
             *              105           105 
             *  
             * p指向要删除的节点20。不能在二叉搜索树中留下空洞，所以必须要用某个元素来取代20， 
             * 那么选择哪个元素好?逻辑上选择被删除的子节点15。因此需把15连到20的父节点上。 
             */  
            Entry<E> rep;// 指向用来替换被删除节点p的，只可能是左子节点或是右子节点  
  
            if (p.left != null) {  
                //如果只有左子节点时，则用左子节点替换要删除的节点p  
                rep = p.left;  
            } else {//否则只有右子树，则用右子节点替换要删除的节点p  
                rep = p.right;  
            }  
            //--修改替换节点的父指针指向  
            /* 
             * 使替换节点的父指针指向删除节点p的父节点，注，如果删除的是根节点，则左右子节点 
             * 的父指针都会指向null，则此时需将root指向左或右子节点，即左或右子节点将成为根 
             * 节点 
             */  
            rep.parent = p.parent;//设置替换元素的父  
  
            //--修改被删除元素p的父节点的左或右指针指向  
            //如果删除的是根元素，则重置root  
            if (p.parent == null) {  
                root = rep;  
            }//如果删除的是某节点的左子节点  
            else if (p == p.parent.left) {  
                p.parent.left = rep;  
            }//否则删除的是某节点的右子节点  
            else {  
                p.parent.right = rep;  
            }  
  
        }  
        //让删除节点成为孤立点  
        p.parent = null;  
        p.left = null;  
        p.right = null;  
  
        size--;//删除节点后树节点个数减一  
    }

    /** 
     * 根据指定的数据域查找元素 
     * @param e 
     * @return Entry<E> 
     */  
    private Entry<E> getEntry(E e) {  
        Entry<E> tmp = root;  
        int c;  
        while (tmp != null) {//如果树不为空  
            c = e.compareTo(tmp.elem);  
            //如果与tmp元素相等，则返回  
            if (c == 0) {  
                return tmp;  
            }//如果比tmp小，则在tmp的左子树中找  
            else if (c < 0) {  
                tmp = tmp.left;  
            }//如果比tmp大，则在tmp的右子树中找  
            else {  
                tmp = tmp.right;  
            }  
        }  
        //树本身就为空或树不为空时没有找到时  
        return null; 
    }
    
    /** 
     * 查找指定节点的中序遍历序列的直接后继节点 
     *  
     * 注，无需在左子树上找，因为中序遍历时，左子树上的节点都会在该节点的前面遍历。 
     *  
     * 1、如果待查找的节点有右子树，则后继节点一定在右子树上，此时右子树上的某个节点可能成为后 
     * 继节点：一是如果待查节点的右子树没有左子树（有没有右子树无所谓）时，直接就返回该待查节点 
     * 的右子节点；二是如果待点节点的右子节点有左子树，则查找右子节点的最左边的左子树节点（注， 
     * 该节点一点是左叶子节点或只有一个右子节点的左节点，查找过程要一直向左，即遍历时只向左拐， 
     * 不可向右） 
     *  
     * 2、如果待查找的节点没有右子树，则需要从该节点向根的方向遍历（不可向左或右拐），后继节点只 
     * 可能在祖宗节点中产生（包括父节点与根节点在内），此情况分两种：一种就是待查节点为某节点的左 
     * 子树，则此时的后继为父节点；第二种就是当待查节点为某个节点的右子树时，则需沿根的方向向上找， 
     * 一直找到第一个有左子树的祖宗节点即为后继节点，或到根为止还没有找到（则该节点只可能为中序遍 
     * 历的最后节点）。 
     *  
     * @param e 需要查找哪个节点的直接后继节点 
     * @return Entry<E> 直接后继节点 
     */  
    private Entry<E> successor(Entry<E> e) { 
        if (e == null) {  
            return null;  
        }else if(e.right != null){//如果待找的节点有右子树，则在右子树上查找 
            /* 
             * 查找50节点的直接后继，查找结果为55 
             *            50 
             *             \ 
             *             75 
             *             / 
             *            61 
             *            /\ 
             *           55 68 
             *            \ 
             *            59               
             */  
             //默认后继节点为右子节点（如果右子节点没有左子树时即为该节点）  
             Entry<E> p = e.right;  
             while (p.left != null) {  
                 //注，如果右子节点的左子树不为空，则在左子树中查找，且后面找时要一直向左拐  
                 p = p.left;  
             }  
             return p;  
        } else {//如果待查节点没有右子树，则要在祖宗节点中查找后继节点 
            /* 
             * 没有右子树的节点且为父节点的右子节点36的直接后继为37，同样节点68的直接后继为75 
             * 没有右子树的节点且为父节点的左子节点37的直接后继为50，同样节点28的直接后继为30 
             * 75为最后节点，所以直接后继为null 
             *  
             *                 50 
             *                 /\ 
             *                37 75 
             *                /   / 
             *               25   61  
             *               /\   /\ 
             *             15 30 55 68  
             *                /\  \ 
             *              28 32 59 
             *                  \ 
             *                  36 
             *                   / 
             *                  35 
             */  
             //默认后继节点为父节点（如果待查节点为父节点的左子树，则后继为父节点）
            Entry<E> p = e.parent;
            Entry<E> c = e;//当前节点，如果其父不为后继，则下次指向父节点  
            while (p != null && c == p.right) {  
                c = p;  
                p = p.parent;  
            }  
            return p; 
        }
    }
    
    /** 
     * 查找指定节点的中序遍历序列的直接前驱节点 
     *  
     * 查找逻辑与找直接后继节点刚好相反或对称 
     * @param e 
     * @return 
     */  
    private Entry<E> precursor(Entry<E> e) {
        if (e == null) {  
            return null;  
        }//如果待找的节点有左子树，则在左子树上查找  
        else if (e.left != null) {  
            //默认直接前驱节点为左子节点（如果左子节点没有右子树时即为该节点）  
            Entry<E> p = e.left;  
            while (p.right != null) {  
                //注，如果左子节点的右子树不为空，则在右子树中查找，且后面找时要一直向右拐  
                p = p.right;  
            }  
            return p;  
        }//如果待查节点没有左子树，则要在祖宗节点中查找前驱节点   
        else {  
            //默认前驱节点为父节点（如果待查节点为父节点的右子树，则前驱为父节点）  
            Entry<E> p = e.parent;  
            Entry<E> current = e;//当前节点，如果其父不为前驱，则下次指向父节点  
            //如果待查节点为父节点的左节点时，继续向上找，一直要找到current为p的右子节点，则s才是前驱  
            while (p != null && current == p.left) {  
                current = p;  
                p = p.parent;  
            }  
            return p;  
        }  
    }
    
    
    /** 
     * 树的迭代器 
     * @author jzj 
     * @date 2009-12-19 
     */  
  
    public class TreeItrator implements Iterator<E> { 
        private Entry<E> lastRet;//最近一次next操作返回的节点  
        private Entry<E> next;//下一个节点  
        private Entry<E> endNode;//树最后一个节点  
        
        public TreeItrator() {  
            //初始化时，让next指根节点，如果根没有左子树时，则就为根  
            next = root;  
            if (next != null) {  
                //如果next还有左子树时，则为左子节点，直到最左边节点为止  
                while (next.left != null) {  
                    next = next.left;//从根节点开始，一直向左拐  
                }  
            }  
        }  
        
        @Override
        public boolean hasNext() {
            return next != null;  
        }
        @Override
        public E next() {
            if (next == null) {  
                throw new NoSuchElementException();  
            }  
            lastRet = next;  
            next = successor(next);//下一个为直接后继节点  
  
            //如果后继节点为null，表示该next指向的节点为树中的最后节点  
            if (next == null) {  
                /* 
                 * 使用endNode记录下最末节点，以便 previous 使用，因为next最终会指向null， 
                 * 即好比指向了最末节点的后面，此时previous是要返回最末节点的，所以需要标记 
                 * 与存储起来 
                 */  
                endNode = lastRet;  
            }  
            return lastRet.elem;  
        }
        
      //是否有前驱节点  
        public boolean hasPrevious() {  
            return (next != null && precursor(next) != null) || endNode != null;  
        } 
        
      //返回前驱节点  
        public E previous() {  
            if ((next != null && precursor(next) == null)) {  
                throw new NoSuchElementException();  
            }  
  
            //如果已迭代到了最末节点  
            if (endNode != null) {  
                //使用lastReturned与next都指向最末节点  
                lastRet = next = endNode;  
                endNode = null;  
            } else {//如果lastReturned指向的不是最末节点时  
                lastRet = next = precursor(next);  
            }  
  
            return lastRet.elem;  
        }  
        
        @Override
        public void remove() {
            if (lastRet == null) {  
                throw new IllegalStateException();  
            }  
  
            /* 
             * 注，如果删除的节点（lastRet指向的节点）具有左右子节点，则在调用 
             * deleteEntry方法删除后，它会使用这个后继节点的数据域替换待删除的节点的数据 
             * 域，next指向的节点会被移到lastRet位置，所以如果此时不使用next回退 
             * 到lastRet的位置，则 next指向的节点（Entry）对象将是一个不在这棵树中 
             * 的节点。如果删除的是一个叶子节点或只有一个节点的节点时不会有这种问题。 
             *  
             * 删除有两个子节点的节点40，删除后next指向的节点已被移到lastRet，所以 
             * next需后退 
             *  
             *                    先后退                 后删除50 
             * lastRet → 40    next、lastRet → 50             next → 50 
             *           /\         →         /\                    /\ 
             *          20 75                20 75         →       20 75 
             *             /\                   /\                     \ 
             *     next → 50 80               50 80                  50 80 
             *  
             * 删除只有一个子节点的节点20，删除后next指向不需要改变，因为next指向的元素没有 
             * 发生变化，删除前后还是指向原来的30 
             *                50                 50 
             *                /\                 /\ 
             *     lastRet → 20 75   →   next → 30 75 
             *                \                 / 
             *         next → 30               28 
             *                /       
             *               28                20 
             */  
            if (lastRet.left != null && lastRet.right != null) {  
                next = lastRet;  
            }  
  
            deleteEntry(lastRet);//删除最后一次next方法返回的元素  
  
            lastRet = null;//不能连续删除，只有在使用next后才能删除  
            
        }
    }
    private static void fixText() {  
        BinSearchTree<Integer> bst = new BinSearchTree<Integer>();  
        bst.add(50);  
        bst.add(37);  
        bst.add(75);  
        bst.add(25);  
        bst.add(61);  
        bst.add(15);  
        bst.add(30);  
        bst.add(55);  
        bst.add(68);  
        bst.add(28);  
        bst.add(32);  
        bst.add(59);  
        bst.add(36);  
        bst.add(36);//添加一个重复，但不能添加进去  
  
        //是否包含  
        System.out.println(bst.contanins(36));//true  
        System.out.println(bst.contanins(38));//false  
  
        //大小  
        System.out.println(bst.size());//13  
  
        //遍历  
        Iterator<Integer> itr = bst.iterator();  
        while (itr.hasNext()) {  
            //15 25 28 30 32 36 37 50 55 59 61 68 75  
            System.out.print(itr.next() + " ");  
        }  
        System.out.println();  
  
        //从后往前遍历  
        BinSearchTree<Integer>.TreeItrator titr = (BinSearchTree<Integer>.TreeItrator) itr;  
        while (titr.hasPrevious()) {  
            //75 68 61 59 55 50 37 36 32 30 28 25 15  
            System.out.print(titr.previous() + " ");  
        }  
        System.out.println();  
  
        //测试迭代器的 previous   
        titr = (BinSearchTree<Integer>.TreeItrator) bst.iterator();  
        System.out.println(titr.hasPrevious());//false  
        System.out.println(titr.next());//15  
        System.out.println(titr.previous());//15  
        System.out.println(titr.next());//15  
        System.out.println(titr.next());//25  
        System.out.println(titr.next());//28  
        System.out.println(titr.previous());//28  
  
        //删除根叶子节点36  
        bst.remove(36);  
        System.out.println(bst.size());//12  
        itr = bst.iterator();  
        while (itr.hasNext()) {  
            //15 25 28 30 32 37 50 55 59 61 68 75  
            System.out.print(itr.next() + " ");  
        }  
        System.out.println();  
  
        //删除只有一个左子节点的节点37  
        bst.remove(37);  
        System.out.println(bst.size());//11  
        itr = bst.iterator();  
        while (itr.hasNext()) {  
            //15 25 28 30 32 50 55 59 61 68 75   
            System.out.print(itr.next() + " ");  
        }  
        System.out.println();  
  
        //删除只有一个右子节点的节点55  
        bst.remove(55);  
        System.out.println(bst.size());//10  
        itr = bst.iterator();  
        while (itr.hasNext()) {  
            //15 25 28 30 32 50 59 61 68 75   
            System.out.print(itr.next() + " ");  
        }  
        System.out.println();  
  
        //删除有左右子节点的根节点50  
        bst.remove(50);  
        System.out.println(bst.size());//9  
        itr = bst.iterator();  
        while (itr.hasNext()) {  
            //15 25 28 30 32 59 61 68 75   
            System.out.print(itr.next() + " ");  
        }  
        System.out.println();  
  
        //下面通过迭代器删除节点根节点59  
        itr = bst.iterator();  
        while (itr.hasNext()) {  
            if (itr.next() == 59) {  
                itr.remove();//删除最近一次next返回的节点  
                break;  
            }  
        }  
  
        while (itr.hasNext()) {  
            //61 68 75  
            System.out.print(itr.next() + " ");  
            itr.remove();  
        }  
  
        System.out.println();  
        System.out.println(bst.size());//5  
    }  
  
    private static void randomTest() {  
        BinSearchTree<Integer> myTree = new BinSearchTree<Integer>();  
        Random random = new Random();  
        System.out.print("随机产生的节点为：");  
        int num = 0;  
        //直到树的节点数为n止  
        while (myTree.size() < 21) {  
            num = random.nextInt(100);  
            myTree.add(num);  
            System.out.print(num + " ");  
        }  
        System.out.println("");  
  
        System.out.println("这棵平衡二叉树的总节点数：" + myTree.size());  
        System.out.println("在树中查找 " + num + " 节点:" + myTree.contains(new Integer(num)));  
        System.out.println("在树中查找 100 节点:" + myTree.contains(new Integer(100)));  
        System.out.print("中序遍历(从前往后)：");  
        BinSearchTree<Integer>.TreeItrator itr = (BinSearchTree<Integer>.TreeItrator) myTree  
                .iterator();  
        while (itr.hasNext()) {  
            System.out.print(itr.next() + " ");  
        }  
        System.out.println("");  
  
        System.out.print("中序遍历(从后往前)：");  
        while (itr.hasPrevious()) {  
            System.out.print(itr.previous() + " ");  
        }  
        System.out.println("");  
  
        myTree.remove(num);  
        System.out.print("删除节点 " + num + " 后遍历：");  
        itr = (BinSearchTree<Integer>.TreeItrator) myTree.iterator();  
        while (itr.hasNext()) {  
            System.out.print(itr.next() + " ");  
        }  
        System.out.println("");  
  
        System.out.println("使用迭代器删除所有节点");  
        itr = (BinSearchTree<Integer>.TreeItrator) myTree.iterator();  
        while (itr.hasNext()) {  
            itr.next();  
            itr.remove();  
        }  
        System.out.println("删除后树的总节点数：" + myTree.size());  
  
    }  
  
    private static void order() {  
        BinSearchTree<Integer> myTree = new BinSearchTree<Integer>();  
        Random random = new Random();  
        int num = 0;  
        while (myTree.size() < 10) {  
            num = random.nextInt(100);  
            myTree.add(num);  
        }  
  
        System.out.print("前序遍历 - ");  
        myTree.preOrder(new Visitor<Integer>() {  

            @Override
            public void vistor(Integer ele) {
                // TODO Auto-generated method stub
                System.out.print(ele + " ");  
            }
        });  
        System.out.println();  
  
        System.out.print("中序遍历 - ");  
        myTree.inOrder(new Visitor<Integer>() {  
            @Override
            public void vistor(Integer ele) {
                // TODO Auto-generated method stub
                System.out.print(ele + " ");  
            }  
        });  
        System.out.println();  
  
        System.out.print("后序遍历 - ");  
        myTree.postOrder(new Visitor<Integer>() {  
            @Override
            public void vistor(Integer ele) {
                // TODO Auto-generated method stub
                System.out.print(ele + " ");  
            } 
        });  
        System.out.println();  
  
        System.out.print("层次遍历 - ");  
        myTree.levelOrder(new Visitor<Integer>() {  
            @Override
            public void vistor(Integer ele) {
                // TODO Auto-generated method stub
                System.out.print(ele + " ");  
            } 
        });  
        System.out.println();  
    }  
  
    //测试  
    public static void main(String[] args) {  
        fixText();//固定测试  
        randomTest();//随机测试  
        order();//遍历  
    }  
}
