package com.lorin.tree;


public class ListNode {
    public int data;
    public ListNode next;
    public ListNode sibling;
    
    public ListNode(int element){
        this.data = element;
        this.next = null;
        this.sibling = null;
    }
    
    //....node -> nextNode
    public static ListNode ReverseIteratively(ListNode node){
        if(node == null || node.next == null)return node;
        ListNode nextNode = ReverseIteratively(node.next);
        node.next.next = node;
        node.next = null;
        return nextNode;
    }
    
    /**
     *复杂链表的复制 
     *1.复制链表
     *2.设置clone链表的sibling
     */
    public static void  CloneNodes(ListNode head){
        ListNode node = head;
        while(node != null){
            ListNode cloneNode = new ListNode(node.data);
            cloneNode.next = node.next;
            node.next = cloneNode;
            node = cloneNode.next;
        }
    }
    public static void ConnectSiblingNodes(ListNode head){
        ListNode node = head;
        while(node != null){
            ListNode cloneNode = node.next;
            if(node != null){
                cloneNode.sibling = node.sibling.next;
            }
            node = cloneNode.next;
        }
    }
    public static ListNode ReconnectNodes(ListNode head){
        ListNode node = head;
        ListNode cloneHead = null;
        ListNode cloneNode = null;
        
        if(node != null){
            cloneHead = cloneNode = node.next;
            node.next = cloneNode.next;
            node = node.next;
        }
        
        while(node != null){
            cloneNode.next = node.next;
            cloneNode = cloneNode.next;
            
            node.next = cloneNode.next;
            node = node.next;
        }
        return cloneHead;
    }
    public static ListNode Clone(ListNode head){
        CloneNodes(head);
        ConnectSiblingNodes(head);
        return ReconnectNodes(head);
    }
    
    /**
     * 时间复杂度O(n)删除节点 
     */
    public static ListNode deleteNode(ListNode head,ListNode delNode){
        if(head == null || delNode == null){
            return null;
        }
        if(delNode.next != null){
            ListNode next = delNode.next;
            delNode.data = next.data;
            delNode.next = next.next;
            next = null;
            return head;
        } else {
            ListNode node = head;
            while(node.next != delNode){
                node = node.next;
            }
            node.next = null;
            return head;
        }
    }
    
    /**
     * 单链表是否有环
     */
    public static boolean hasLoop(ListNode head){
        if(head == null)return false;
        ListNode sNode = head.next;
        if(sNode == null)return false;
        boolean flag = false;
        ListNode fNode = sNode.next;
        while(sNode != null && fNode != null){
            if(sNode.data == fNode.data)flag = true;
            sNode = sNode.next;
            fNode = fNode.next;
            if(fNode != null){
                fNode = fNode.next;
            }
        }
        if(flag){
            sNode = head;
            while(sNode.data != fNode.data){
                sNode = sNode.next;
                fNode = fNode.next;
            }
        }
        System.out.println("环的起始节点："+sNode.data);
        return flag;
    }
    
    /**
     * 两两交换 链表
     * @param head
     * @return
     */
    public static ListNode reverse(ListNode head) {  
        
        if (head == null) return null;  
        if (head.next == null)  return head;  
        //the linked list has at least two nodes   
        ListNode root = head.next;  
        ListNode first = head;  
        ListNode second = head.next;  
        //"previous" is used to point to the last node in the pair, like "A", "C", "E", etc.   
        ListNode previous = null;  
        while (second != null) {  
            // temp point to the next node of the pair   
            ListNode temp = second.next;  
            //update the previous node's "next"   
            if (previous != null) {  
                previous.next = second;  
            }  
            second.next = first;  
            first.next = temp;  
            previous = first;  
            first = temp;  
            second = first.next;  
        }  
        return root;  
    }  

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        ListNode temp = ReverseIteratively(l1);
        while(temp!= null){
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

}
