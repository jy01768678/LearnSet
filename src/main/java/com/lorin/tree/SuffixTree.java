package com.lorin.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class SuffixTree {

    /**
     * 用链表来模拟后缀树
     * @author Administrator
     *
     */
    private class SuffixNode{
        private String key;
        private List<SuffixNode> children = new ArrayList<SuffixNode>();
        //use "#" for terminal char
        private boolean terminal; 
         
        private int minStartIndex;
         
        public SuffixNode(){            
            this.key = "";
            minStartIndex = -1;
        }
        public SuffixNode(String key){
            this.key = key;         
        }       
        public String toString(){           
            return this.key + "[" + this.minStartIndex + "]" + (this.terminal?"#":"") + "(" + children.size() +")";
        }
    }
    
    private SuffixNode root;
    
    private String text;
    
    public SuffixTree(String text){
        this.text = text;
    }
    
    //terminators should be ordered according to input strings
    private char[] terminators;
     
    public SuffixTree(String text,char[] terminators){
        this.text = text;
        this.terminators = terminators;
    }
    
  //return the start index of the matched substring;
    //return -1 if no match is found
    public int find(String pattern){
        if(pattern == null || pattern.length() == 0){
            return -1;
        }
        if(root == null){
            return -1;
        } else {
            return find(root,pattern);
        }
    }
    private int find(SuffixNode currentNode, String pattern) {
        for(int i=0;i<currentNode.children.size();i++){
            SuffixNode child = currentNode.children.get(i);
            
            int len = child.key.length() < pattern.length() ? child.key.length() : pattern.length();
            
            int j=0;
            for(;j<len;j++){
                if(child.key.charAt(j) != pattern.charAt(j)){
                    break;
                }
            }
            if(j == 0){//this child doesn't match any character with the new pattern
              //order suffix-key by lexi-order
                if(pattern.charAt(0)<child.key.charAt(0)){
                    //e.g. child="e", pattern="c" (currNode="abc")
                    //     abc                     
                    //    /  \     
                    //   e    h   
                    return -1;
                }else{
                    //e.g. child="e", pattern="h" (currNode="abc")
                    continue;
                }
            } else {//current child's key partially matches with the new pattern; 0<j<=len 
                if(j == len){
                    if(pattern.length()==child.key.length()){
                        if(child.terminal){
                            //e.g. child="ab", pattern="ab"
                            //     ab#                    
                            //       \    
                            //        f#    
                            return child.minStartIndex; 
                        } else {
                            //e.g. child="ab", pattern="ab"
                            //     ab                    
                            //    /  \    
                            //   e    f    
                            return child.minStartIndex;
                        }
                    } else if(pattern.length()>child.key.length()){    
                        //e.g. child="ab#", pattern="abc"
                        //     ab#                     
                        //    /  \                          
                        //   a    c#            
                        String subpattern = pattern.substring(j); //c
                        //recursion
                        int index = find(child,subpattern);
                        if(index==-1){
                            return -1;
                        }else{
                            return index-child.key.length();
                        }
                    }else{ //pattern.length()<child.key.length()
                        //e.g. child="abc", pattern="ab"
                        //     abc                      
                        //    /   \       
                        //   e     f     
                        return child.minStartIndex;                     
                    }   
                }else{//0<j<len
                    //e.g. child="abc", pattern="abd"
                    //     abc                     
                    //    /  \      
                    //   e    f    
                    return -1;                  
                }   
            }
        }
        return -1;
    }

    public void insert(String suffix,int startIndex) throws Exception{
        if(suffix == null || suffix.length() == 0) return;
         
        if(root==null){
            root = new SuffixNode();                
        }
        insert(root,suffix,startIndex);     
    }
    public void insert(SuffixNode currNode,String key,int startIndex) throws Exception{ 
        boolean done = false;
        for(int i=0;i<currNode.children.size();++i){
            SuffixNode child = currNode.children.get(i);
            int len = child.key.length() < key.length() ? child.key.length() : key.length();
            int j = 0;
            for(;j<len;++j){
                if(child.key.charAt(j) != key.charAt(j)){
                    break;
                }
            }
            
            if(j == 0){//this child doesn't match any character with the new pattern
                //order keys by lexi-order
                if(key.charAt(0)<child.key.charAt(0)){
                    //e.g. child="e" (currNode="abc")
                    //     abc                     abc
                    //    /  \    =========>      / | \
                    //   e    f   insert "c"     c# e  f
                 
                    SuffixNode node = new SuffixNode(key);
                    currNode.children.add(i,node);
                    node.terminal = true;   
                    node.minStartIndex = startIndex;
                    done = true;
                    break;                  
                }else{ //key.charAt(0)>child.key.charAt(0)
                    //don't forget to add the largest new key after iterating all children
                    continue;
                }
            } else if(j == len){
                if(child.key.length() == key.length()){
                    if(child.terminal){
                        throw new Exception("Duplicate Key is found when insertion!");  
                    } else {
                      //e.g. child="ab"
                        //     ab                    ab#
                        //    /  \    =========>    /   \
                        //   e    f   insert "ab"  e     f
                        child.terminal = true;
                        if(child.minStartIndex>startIndex)
                            child.minStartIndex = startIndex;
                    }
                } else if(key.length()>child.key.length()){
                    //e.g. child="ab#"
                    //     ab#                    ab#
                    //    /  \    ==========>    / | \                           
                    //   e    f   insert "abc"  c# e  f     
                    if(child.minStartIndex>startIndex)
                        child.minStartIndex = startIndex;
                    String subkey = key.substring(j);
                    //recursion
                    insert(child,subkey,startIndex+j);
                } else {//key.length()<child.key.length()
                    //e.g. child="abc#"
                    //     abc#                      ab#
                    //    /   \      =========>      /   
                    //   e     f     insert "ab"    c#    
                    //                             /  \
                    //                            e    f 
                    String childSubkey = child.key.substring(j); //c
                    SuffixNode subChildNode = new SuffixNode(childSubkey);
                    subChildNode.terminal = child.terminal;
                    subChildNode.children = child.children; //inherited from parent
                    subChildNode.minStartIndex = child.minStartIndex+j;
                     
                    child.key = key;  //ab
                    child.terminal = true;  //ab#   
                    if(child.minStartIndex>startIndex)
                        child.minStartIndex = startIndex;
                     
                    child.children = new LinkedList<SuffixNode>();
                    child.children.add(subChildNode);
                }
            }else{//0<j<len
                //e.g. child="abc#"
                //     abc#                     ab
                //    /  \     ==========>     / \
                //   e    f   insert "abd"    c#  d# 
                //                           /  \
                //                          e    f                  
                //split at j
                String childSubkey = child.key.substring(j);  //c
                String subkey = key.substring(j); //d
                 
                SuffixNode subChildNode = new SuffixNode(childSubkey);
                subChildNode.terminal = child.terminal;
                subChildNode.children = child.children; //inherited from parent
                subChildNode.minStartIndex = child.minStartIndex+j;
                 
                //update child's key
                child.key = child.key.substring(0,j);
                if(child.minStartIndex>startIndex)
                    child.minStartIndex = startIndex;
                //child is not terminal now due to split, it is inherited by subChildNode
                child.terminal = false;
                 
                //Note: no need to merge subChildNode                   
                 
                SuffixNode node = new SuffixNode(subkey);
                node.terminal = true;
                node.minStartIndex = startIndex+j;
                child.children = new LinkedList<SuffixNode>();
                if(subkey.charAt(0)<childSubkey.charAt(0)){
                    child.children.add(node);
                    child.children.add(subChildNode);
                }else{
                    child.children.add(subChildNode);
                    child.children.add(node);
                }
            }
            done = true;
            break;
        }
        if(!done){
            SuffixNode node = new SuffixNode(key);      
            node.terminal = true;
            node.minStartIndex = startIndex;
            currNode.children.add(node);
        }
    }
    
    //build a suffix-tree for a string of text
    public void buildSuffixTree() throws Exception{
        for(int i=0;i<text.length();i++){
            this.insert(text.substring(i), i);
            //this.printTree();
        }       
    }
  //for test purpose only
    public void printTree(){
        this.print(0, this.root);
    }
    private void print(int level, SuffixNode node){
        for (int i = 0; i < level; i++) {
            System.out.format(" ");
        }
        System.out.format("|");
        for (int i = 0; i < level; i++) {
            System.out.format("-");
        }
        if (node.terminal)
            System.out.format("%s[%s]#%n", node.key,node.minStartIndex);
        else
            System.out.format("%s[%s]%n", node.key,node.minStartIndex);
        for (SuffixNode child : node.children) {
            print(level + 1, child);
        }       
    }
    
    public void testFind(String pattern){
        int index = this.find(pattern);
        if(index != -1)
            System.out.format("Found pattern \"%s\" at: %s%n",pattern,index);
        else
            System.out.format("Found no such pattern: \"%s\"%n",pattern);
    }
    
    public String findLCS(){
        return findLCS(root);
    }
    //return the longest substring starting with current node (but not including currNode.key)
    public String findLCS(SuffixNode currNode){
        int maxDepth = currNode.key.length();
        int currDepth = currNode.key.length();
        
        String longestSubstrSuffix = "";
        
        for(int i=0;i<currNode.children.size();i++){
            SuffixNode child = currNode.children.get(i);
            if(!child.terminal){     
                int depth = currDepth + child.key.length();
                
                //terminators are unique, so terminal child is excluded
                boolean containsTerminators = containTerminators(child);
                if(containsTerminators){                        
                    if(depth > maxDepth){
                        maxDepth = depth;
                        longestSubstrSuffix =  child.key;
                    }
                    String longestChildSubstrSuffix = findLCS(child);
                    //System.out.println(child.key+"这里："+longestChildSubstrSuffix);
                    if(longestChildSubstrSuffix.length()>0){ //not a part of LCS if longestChildSubstrSuffix's lenght is 0
                        int maxChildDepth = longestChildSubstrSuffix.length() + depth;
                        if(maxChildDepth > maxDepth){
                            maxDepth = maxChildDepth;
                            //the substring is relative to currNode
                            longestSubstrSuffix = child.key + longestChildSubstrSuffix;
                        }
                    }
                }    
            }
        }
        return longestSubstrSuffix;
    }
    
    private boolean containTerminators(SuffixNode currNode) {
        boolean[] done = new boolean[terminators.length];
        return containTerminators(currNode,done);
    }

    private boolean containTerminators(SuffixNode currNode, boolean[] done) {
        boolean allDone = false;
        
        for(int i=0;i<currNode.children.size();i++){
            SuffixNode child = currNode.children.get(i);
            if(child.terminal){
                //Note: here the order of terminator is important 
                for(int j=0;j<terminators.length;j++){                   
                    int pos = child.key.indexOf(terminators[j]);
                    if(pos>=0){
                        done[j]=true;
                        break;
                    }
                }
            }else{
                containTerminators(child,done);
            }
             
            allDone = true;
            for(int j=0;j<done.length;j++){
                if(!done[j]){
                    allDone = false;
                    break;
                }                   
            }
            if(allDone)
                break;
        }
         
        return allDone;
    }
    
    public void testFindLCS() throws Exception{
        System.out.println("****************************"); 
        System.out.format("LCS for 3 strings:{abc,bcabca,aabcf}%n");
        String text = "abc$bcabca@aabcf%";
        SuffixTree strie = new SuffixTree(text,new char[]{'$','@','%'});
        strie.buildSuffixTree();
        //strie.printTree();
         
        String longestSubstr = strie.findLCS();
        System.out.format("%s%n", longestSubstr);
         
         
        System.out.println("****************************");
        System.out.format("LCS for 3 strings:{abcd,bcca,aabc}%n");
        text = "abcd$bcca@aabc%";
        strie = new SuffixTree(text,new char[]{'$','@','%'});
        strie.buildSuffixTree();
        //strie.printTree();
         
        longestSubstr = strie.findLCS();
        System.out.format("%s%n", longestSubstr);
         
         
         
        System.out.println("****************************");     
        System.out.format("LCS for 2 strings:{asdfldkjxlfjax123456789abckljddfdfe123456789ees, xsdldkjalfla123456789abcfleur123456789ljafa}%n");
        text = "asdfldkjxlfjax123456789abckljddfdfe123456789ees$xsdldkjalfla123456789abcfleur123456789ljafa@";
        strie = new SuffixTree(text,new char[]{'$','@'});
        strie.buildSuffixTree();
        //strie.printTree();
         
        longestSubstr = strie.findLCS();
        System.out.format("%s%n", longestSubstr);
         
        System.out.println("****************************");     
        System.out.format("LCS for 4 strings:{abcd,abce,abd,bdess}%n");
        text = "abcd$abce@abd%bdess&";
        strie = new SuffixTree(text,new char[]{'$','@','%','&'});
        strie.buildSuffixTree();
        //strie.printTree();
         
        longestSubstr = strie.findLCS();
        System.out.format("%s%n", longestSubstr);
    }
    
    public static void huiwenTest()throws Exception{
        String text = "XMADAMYX";//XYMADAMX
        for(int i=0;i<text.length();i++){
            String txt = text.substring(i)+"$"+StringUtils.reverse(text).substring(text.length()-i-1);
            //System.out.println(txt + "--" +i);
            SuffixTree strie = new SuffixTree(txt,new char[]{'$'});
            strie.buildSuffixTree();
            String longestSubstr = strie.findLCS();
            System.out.println(longestSubstr);
            System.out.println("****************************"); 
        }
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
      //test suffix-tree
        System.out.println("****************************");     
        String text = "";
        SuffixTree strie = null;
        huiwenTest();
//        String text = "abc$bcabca@aabcf%";
//        SuffixTree strie = new SuffixTree(text);
//        SuffixTree strie = new SuffixTree(text,new char[]{'$','@','%'});
//        strie.buildSuffixTree();
//        System.out.println(strie.findLCS());
//        strie.printTree();
         
//        System.out.println("****************************");     
//        text = "mississippi";
//        strie = new SuffixTree(text);
//        strie.buildSuffixTree();
//        strie.printTree();
//         
//        String pattern = "iss";
//        strie.testFind(pattern);
//        pattern = "ip";
//        strie.testFind(pattern);
//        pattern = "pi";
//        strie.testFind(pattern);
//        pattern = "miss";
//        strie.testFind(pattern);
//        pattern = "tt";
//        strie.testFind(pattern);
//        pattern = "si";
//        strie.testFind(pattern);
//        pattern = "ssi";
//        strie.testFind(pattern);
//        pattern = "sissippi";
//        strie.testFind(pattern);
//        pattern = "ssippi";
//        strie.testFind(pattern);
//         
//        System.out.println("****************************");     
//        text = "After a long text, here's a needle ZZZZZ";  
//        pattern = "ZZZZZ";    
//        strie = new SuffixTree(text);
//        strie.buildSuffixTree();
//        //strie.printTree();
//        strie.testFind(pattern);
//         
//         
//        System.out.println("****************************");     
//        text = "The quick brown fox jumps over the lazy dog.";  
//        pattern = "lazy";  
//        strie = new SuffixTree(text);
//        strie.buildSuffixTree();
//        //strie.printTree();
//        strie.testFind(pattern);
//         
//         
//        System.out.println("****************************");     
//        text = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna...";  
//        pattern = "tempor"; 
//        strie = new SuffixTree(text);
//        strie.buildSuffixTree();
//        //strie.printTree();
//        strie.testFind(pattern);
//         
//        System.out.println("****************************");     
//        text = "GGGGGGGGGGGGCGCAAAAGCGAGCAGAGAGAAAAAAAAAAAAAAAAAAAAAA";  
//        pattern = "GCAGAGAG";      
//        strie = new SuffixTree(text);
//        strie.buildSuffixTree();
//        //strie.printTree();
//        strie.testFind(pattern);
    }

}
