package com.lorin.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PatriciaTrie {

    private class PatriciaTrieNode {

        private String key;

        private int data;

        private List<PatriciaTrieNode> children = new ArrayList<PatriciaTrieNode>();

        //use "#" for terminal char 
        private boolean terminal;

        public PatriciaTrieNode() {
            this.key = "";
        }

        public PatriciaTrieNode(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key + (this.terminal ? "#" : "") + "(" + children.size() + ")";
        }
    }

    private PatriciaTrieNode root;

    //return the value of the external node if found;    
    //otherwise, return null 
    public Integer find(String key) {
        if (key == null || key.length() == 0) {
            return null;
        }
        if (root == null) {
            return null;
        } else {
            return find(root, key);
        }
    }

    private Integer find(PatriciaTrieNode currentNode, String key) {
        for (int i = 0; i < currentNode.children.size(); i++) {
            PatriciaTrieNode child = currentNode.children.get(i);
            int len = child.key.length() < key.length() ? child.key.length() : key.length();
            int j = 0;
            for (; j < len; j++) {
                if (key.charAt(j) != child.key.charAt(j)) {
                    break;
                }
            }
            if (j == 0) {//this child doesn't match any character with the new key      
                //order keys by lexi-order   
                if (key.charAt(0) < child.key.charAt(0)) {
                    //e.g. child="e", key="c" (currNode="abc")   
                    //     abc                        
                    //    /  /        
                    //   e    h      
                    return null;
                } else {
                    //e.g. child="e", key="h" (currNode="abc")   
                    continue;
                }
            } else {//current child's key partially matches with the new key; 0<j<=len                 
                if (j == len) {
                    if (key.length() == child.key.length()) {
                        if (child.terminal) {
                            //e.g. child="ab", key="ab"   
                            //     ab#                       
                            //       /       
                            //        f#       
                            return child.data;
                        } else {
                            //e.g. child="ab", key="ab"   
                            //     ab                       
                            //    /  /       
                            //   e    f       
                            return null;
                        }
                    } else if (key.length() > child.key.length()) {
                        //e.g. child="ab#", key="abc"   
                        //     ab#                        
                        //    /  /                             
                        //   a    c#               
                        String subkey = key.substring(j); //c   
                        //recursion   
                        return find(child, subkey);
                    } else {//key.length()<child.key.length()   
                        //e.g. child="abc", key="ab"   
                        //     abc                         
                        //    /   /          
                        //   e     f        
                        return null;
                    }
                } else {//0<j<len   
                    //e.g. child="abc", key="abd"   
                    //     abc                        
                    //    /  /         
                    //   e    f       
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 统计带某个前缀的单词数量 
     * @param key
     * @return
     */
    public int query(String key) {
        return query(root, key);
    }

    private int query(PatriciaTrieNode currNode, String key) {
        for (int i = 0; i < currNode.children.size(); ++i) {
            PatriciaTrieNode child = currNode.children.get(i);
            int len = child.key.length() < currNode.key.length() ? child.key.length()
                    : currNode.key.length();
            int j = 0;
            for (; j < len; ++j) {
                if (child.key.charAt(j) != currNode.key.charAt(j)) {
                    break;
                }
            }
            if (j == 0) {
                if (child.key.charAt(0) > currNode.key.charAt(0)) {
                    return 0;
                } else {
                    continue;
                }
            } else {
                if (j == len) {
                    if (key.length() == child.key.length()) {
                        if (child.terminal) {
                            //e.g. child="ab", key="ab"
                            // ab# 
                            //  /                                 
                            // f# 
                            return getLeafCount(child);
                        } else {

                        }
                    } else if (key.length() > child.key.length()) {
                        //e.g. child="ab#", key="abc"                          
                        //     ab#                                                
                        //    /  /                                                     
                        //   a    c#                                       
                        String subkey = key.substring(j); //c                           
                        //recursion                           
                        return query(child,subkey);  
                    }else{ //key.length()<child.key.length()                          
                        //e.g. child="abc", key="ab"                          
                        //     abc                                                 
                        //    /   /                                  
                        //   e     f                                
                        return getLeafCount(child);                                           
                    }
                }else{//0<j<len                      
                    //e.g. child="abc", key="abd"                       
                    //     abc                                            
                    //    /  /                             
                    //   e    f                           
                    return 0;                                     }  
            }
        }
        return 0;
    }

    private int getLeafCount(PatriciaTrieNode currNode) {
        int count = 0;
        if (currNode.terminal) {
            count++;
            for (int i = 0; i < currNode.children.size(); i++) {
                PatriciaTrieNode child = currNode.children.get(i);
                count += getLeafCount(child);
            }
            return count;
        }

        return 0;
    }

    public void delete(String key) throws Exception {
        if (key == null || key.length() == 0) return;

        if (root == null) {
            return;
        }
        delete(root, key);
    }

    private void delete(PatriciaTrieNode currNode, String key) throws Exception {
        // TODO Auto-generated method stub
        boolean done = false;
        for (int i = 0; i < currNode.children.size(); i++) {
            PatriciaTrieNode child = currNode.children.get(i);

            //use min(child.key.length, key.length)   
            int len = child.key.length() < key.length() ? child.key.length() : key.length();
            int j = 0;
            for (; j < len; j++) {
                if (key.charAt(j) != child.key.charAt(j)) {
                    break;
                }
            }

            if (j == 0) {//this child doesn't match any character with the new key
                //order keys by lexi-order   
                if (key.charAt(0) < child.key.charAt(0)) {
                    //e.g. child="e", key="c" (currNode="abc")   
                    //     abc                        
                    //    /  /        
                    //   e    h      
                    done = true;
                    System.out.println("No such key is found for removal!");
                } else {
                    //e.g. child="e", key="h" (currNode="abc")                     
                    continue;
                }
            } else {//current child's key partially matches with the new key; 0<j<=len                 
                if (j == len) {
                    if (key.length() == child.key.length()) {
                        if (child.terminal) {
                            //found key, delete it   
                            if (child.children.size() == 0) {
                                //e.g. child="ab#", key="ab", currNode="a"   
                                //      a                       
                                //     / /       
                                //    d  ab#       
                                currNode.children.remove(i);
                                //merge node for currNode   
                                if (!currNode.terminal && currNode.children.size() == 1) {
                                    PatriciaTrieNode singleChild = currNode.children.get(0); //d   
                                    currNode.key += singleChild.key;
                                    currNode.data = singleChild.data;
                                    currNode.terminal = singleChild.terminal;
                                    currNode.children = singleChild.children;
                                }
                            } else { //child.children.size()>=1
                                //e.g. child="ab#", key="ab", currNode="a"   
                                //    a#   
                                //     /   
                                //     ab#                       
                                //       /       
                                //        f#     
                                child.terminal = false;
                                //merge node for child   
                                if (child.children.size() == 1) {
                                    PatriciaTrieNode singleChild = child.children.get(0); //f#   
                                    child.key += singleChild.key;
                                    child.data = singleChild.data;
                                    child.terminal = singleChild.terminal; //Note: singleChild may not be external node   
                                    child.children = singleChild.children;
                                }
                            }
                        } else {
                            //e.g. child="ab", key="ab"   
                            //     ab                       
                            //    /  /       
                            //   e    f       
                            System.out.println("No such key is found for removal!");
                        }
                    } else if (key.length() > child.key.length()) {
                        //e.g. child="ab#", key="abc"   
                        //     ab#                        
                        //    /  /                             
                        //   a    c#               
                        String subkey = key.substring(j); //c   
                        //recursion   
                        delete(child, subkey);
                    } else { //key.length()<child.key.length()   
                        //e.g. child="abc", key="ab"   
                        //     abc                         
                        //    /   /          
                        //   e     f        
                        System.out.println("No such key is found for removal!");
                    }
                } else {//0<j<len   
                    //e.g. child="abc", key="abd"   
                    //     abc                        
                    //    /  /         
                    //   e    f       
                    System.out.println("No such key is found for removal!");
                }
                done = true;
                break;
            }
        }
        if (!done) {
            throw new Exception("No such key is found for removal!");
        }
    }

    public void insert(String key, Integer value) throws Exception {
        if (key == null || key.length() == 0) return;

        if (root == null) {
            root = new PatriciaTrieNode();
        }
        insert(root, key, value);
    }

    //value is only located at the external node   
    private void insert(PatriciaTrieNode currNode, String key, Integer value) throws Exception {
        boolean done = false;
        for (int i = 0; i < currNode.children.size(); i++) {
            PatriciaTrieNode child = currNode.children.get(i);

            //use min(child.key.length, key.length)   
            int len = child.key.length() < key.length() ? child.key.length() : key.length();
            int j = 0;
            for (; j < len; j++) {
                if (key.charAt(j) != child.key.charAt(j)) {
                    break;
                }
            }
            if (j == 0) {//this child doesn't match any character with the new key        
                //order keys by lexi-order   
                if (key.charAt(0) < child.key.charAt(0)) {
                    //e.g. child="e" (currNode="abc")   
                    //     abc                     abc   
                    //    /  /    =========>      / | /   
                    //   e    f   insert "c"     c# e  f   

                    PatriciaTrieNode node = new PatriciaTrieNode(key);
                    currNode.children.add(i, node);
                    node.terminal = true;
                    node.data = value;
                    done = true;
                    break;
                } else { //key.charAt(0)>child.key.charAt(0)   
                    //don't forget to add the largest new key after iterating all children   
                    continue;
                }
            } else {//current child's key partially matches with the new key; 0<j<=len                 
                if (j == len) {
                    if (key.length() == child.key.length()) {
                        if (child.terminal) {
                            throw new Exception("Duplicate Key is found when insertion!");
                        } else {
                            //e.g. child="ab"   
                            //     ab                    ab#   
                            //    /  /    =========>    /   /   
                            //   e    f   insert "ab"  e     f   
                            child.terminal = true;
                            child.data = value;
                        }
                    } else if (key.length() > child.key.length()) {
                        //e.g. child="ab#"   
                        //     ab#                    ab#   
                        //    /  /    ==========>    / | /                              
                        //   e    f   insert "abc"  c# e  f
                        String subkey = key.substring(j);
                        insert(child, subkey, value);
                    } else { //key.length()<child.key.length()   
                        //e.g. child="abc#"   
                        //     abc#                      ab#   
                        //    /   /      =========>      /      
                        //   e     f     insert "ab"    c#       
                        //                             /  /   
                        //                            e    f  
                        String childSubkey = child.key.substring(j); //c   
                        PatriciaTrieNode subChildNode = new PatriciaTrieNode(childSubkey);
                        subChildNode.terminal = child.terminal;
                        subChildNode.data = child.data;
                        subChildNode.children = child.children;

                        child.key = key; //ab   
                        child.terminal = true; //ab#      
                        child.data = value;

                        child.children = new LinkedList<PatriciaTrieNode>();
                        child.children.add(subChildNode);
                    }
                } else {//0<j<len   
                    //e.g. child="abc#"   
                    //     abc#                     ab   
                    //    /  /     ==========>     / /   
                    //   e    f   insert "abd"    c#  d#    
                    //                           /  /   
                    //                          e    f                     
                    //split at j  
                    String childSubkey = child.key.substring(j); //c   
                    String subkey = key.substring(j); //d 

                    PatriciaTrieNode subChildNode = new PatriciaTrieNode(childSubkey);
                    subChildNode.terminal = child.terminal;
                    subChildNode.data = child.data;
                    subChildNode.children = child.children; //inherited from parent   

                    //update child's key   
                    child.key = child.key.substring(0, j);
                    //child is not terminal now due to split, it is inherited by subChildNode   
                    child.terminal = false;

                    //Note: no need to merge subChildNode                      
                    PatriciaTrieNode node = new PatriciaTrieNode(subkey);
                    node.terminal = true;
                    node.data = value;
                    child.children = new LinkedList<PatriciaTrieNode>();
                    if (subkey.charAt(0) < childSubkey.charAt(0)) {
                        child.children.add(node);
                        child.children.add(subChildNode);
                    } else {
                        child.children.add(subChildNode);
                        child.children.add(node);
                    }
                }
                done = true;
                break;
            }
        }

        if (!done) {
            PatriciaTrieNode node = new PatriciaTrieNode(key);
            node.terminal = true;
            node.data = value;
            currNode.children.add(node);
        }
    }

    //for test purpose only   
    public void printTree() {
        this.print(0, this.root);
    }

    private void print(int level, PatriciaTrieNode node) {
        for (int i = 0; i < level; i++) {
            System.out.format(" ");
        }
        System.out.format("|");
        for (int i = 0; i < level; i++) {
            System.out.format("-");
        }
        if (node.terminal) System.out.format("%s[%s]#%n", node.key, node.data);
        else System.out.format("%s%n", node.key);
        for (PatriciaTrieNode child : node.children) {
            print(level + 1, child);
        }
    }

    public void testFind(String key) {
        Integer val = this.find(key);
        if (val != null) System.out.format("Found key %s at: %s%n", key, val);
        else System.out.format("Found no such key: %s%n", key);
    }
    
    

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //test insertion   
        PatriciaTrie ptrie = new PatriciaTrie();
        ptrie.insert("ab", 1);
        ptrie.insert("abc", 2);
        ptrie.insert("abde", 3);
        ptrie.insert("abd", 4);

        //ptrie.insert("dc");   
        ptrie.insert("dce", 5);
        ptrie.insert("dceh", 6);
        ptrie.insert("dceg", 7);
        ptrie.insert("dca", 8);
        ptrie.insert("dcf", 9);

        ptrie.insert("ghk", 10);
        ptrie.insert("gh", 11);

        ptrie.insert("mns", 12);
        ptrie.insert("mnt", 13);
        ptrie.insert("mn", 14);
        ptrie.insert("mg", 15);
        ptrie.printTree();

        String key = "dc";
        ptrie.testFind(key);
        key = "d";
        ptrie.testFind(key);
        key = "ab";
        ptrie.testFind(key);
        key = "ef";
        ptrie.testFind(key);
        key = "zz";
        ptrie.testFind(key);
        key = "dk";
        ptrie.testFind(key);
        key = "dcf";
        ptrie.testFind(key);
        key = "dck";
        ptrie.testFind(key);

        key = "abd";

        ptrie.delete(key);
        System.out.format("After delete key: %s%n", key);
        ptrie.printTree();

        System.out.println("****************************");
        ptrie = new PatriciaTrie();
        ptrie.insert("bear", 1);
        ptrie.insert("bell", 2);
        ptrie.insert("bid", 3);
        ptrie.insert("bull", 4);
        ptrie.insert("buy", 5);
        ptrie.insert("sell", 6);
        ptrie.insert("stock", 7);
        ptrie.insert("stop", 8);
        ptrie.printTree();

        System.out.println("****************************");
        ptrie = new PatriciaTrie();
        ptrie.insert("allot", 1);
        ptrie.insert("alloy", 2);
        ptrie.insert("all", 3);
        ptrie.insert("aloe", 4);
        ptrie.insert("ant", 5);
        ptrie.insert("an", 6);
        ptrie.insert("are", 7);
        ptrie.insert("ate", 8);
        ptrie.insert("be", 9);
        ptrie.printTree();

        System.out.println("****************************");
        ptrie = new PatriciaTrie();
        ptrie.insert("minimize", 0);
        ptrie.insert("mize", 4);
        ptrie.insert("ze", 6);
        ptrie.insert("nimize", 2);
        ptrie.insert("ize", 5);
        ptrie.insert("inimize", 1);
        ptrie.insert("imize", 3);
        ptrie.insert("e", 7);
        ptrie.printTree();

        key = "ize";
        ptrie.testFind(key);
        key = "zee";
        ptrie.testFind(key);
        key = "mize";
        ptrie.testFind(key);

        key = "mize";
        ptrie.delete(key);
        System.out.format("After delete key: %s%n", key);
        ptrie.printTree();

    }

}
