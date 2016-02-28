package com.lorin.algorithm;

public class Permutation {

	public static void permuat(char[] chars,boolean[] userd,int pos,char[] out){
		if(pos == chars.length){
			System.out.println(out);              
            return;  
		}
		for(int i=0;i<chars.length;i++){
			if(!userd[i]){
				out[pos] = chars[i];
				userd[i] = true;
				permuat(chars, userd, pos+1, out);
				userd[i] = false;
			}
		}
	}
	
	public static void combine(int n,char[] chars,boolean[] used,int pos,char[] out){  
        if(pos==chars.length){                
            return;  
        }  
        for(int i=0;i<chars.length;i++){       
            if(pos==0 || (!used[i] &&   out[pos-1] < chars[i])){  
                out[pos] = chars[i];      
                if( pos+1==n){
                	 printSub(out,0,pos);  
                }else if(pos+1<n){  
                    used[i] = true;  
                    combine(n,chars,used,pos+1,out);          
                    used[i] = false;   //用完需要标记该字符可用  
                }  
            }  
        }  
    }  
    public static void printSub(char[] out,int start,int end){  
        for(int i=start;i<=end;i++){  
            System.out.print(out[i]);             
        }  
        System.out.println();  
    }  
    
    public static void main(String[] args) {
    	 char[] chars = new char[]{'c','a','d','b','e'};  
         boolean[] used = new boolean[]{false,false,false,false,false};  
         char[] out = new char[5];  
//         combine(4,chars,used,0,out);
         permuat(chars,used,0,out);
	}
}
