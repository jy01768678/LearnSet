package com.lorin.algorithm;

public class KMP {

	private String text;
	private String pattern;
	public KMP(){}
	public KMP(String text,String pattern){
		this.text = text;
		this.pattern = pattern;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	private int[] next(){
		int len = pattern.length();
		int[] next = new int[len];
		next[0] = 0;
		int k = 0;
		for(int i=1;i<len;i++){
			while(k > 0 && pattern.charAt(k) != pattern.charAt(i)){
				k = next[k - 1];
			}
			if(pattern.charAt(k) == pattern.charAt(i)) k++;
			next[i] = k;
		}
		for(int i=0;i<next.length;i++){
	    	System.out.print(next[i] +"\t");
	    }
		return next;
	}
	
	 private int[] computeSuffix() { 
		int[] suffix = new int[pattern.length()];
	    suffix[pattern.length()-1] = pattern.length();            
	    int j = suffix.length - 1;
	    //suffix[i] = m - the length of the longest prefix of p[i..m-1]
	    for (int i = suffix.length - 2; i >= 0; i--) { 
	      while (j < suffix.length - 1 && pattern.charAt(j) != pattern.charAt(i)) {
	        j = suffix[j + 1] - 1; 
	      }
	      if (pattern.charAt(j) == pattern.charAt(i)) { j--; }
	      suffix[i] = j + 1;
	    }
	    for(int i=0;i<suffix.length;i++){
	    	System.out.print(suffix[i] +"\t");
	    }
	    return suffix;
	  }
	
	private void KMPMatch(){
		int n = text.length();
		int m = pattern.length();
		
		int next[] = next();
		int p = 0;
		
		int count = 0;
		for(int i=0;i<n;i++){
			while(p > 0 && pattern.charAt(p)!= text.charAt(i)){
				p = next[p - 1];
			}
			if(pattern.charAt(p)== text.charAt(i)) p++;
			if(p == m){
				System.out.println("Pattern occurs with shift  " +count + "times");
				p = next[p - 1];
			}
		}
	}
	
	public static void main(String[] args) {  
        
        KMP kmp;  
          
        if(args.length == 2) {  
            kmp = new KMP(args[0] , args[1]);  
        }  
        else {  
            kmp = new KMP();  
            kmp.setText("ababacabacbababababacabcbabababaca");  
            kmp.setPattern("ababaca");  
            System.out.println(kmp.next());
            System.out.println(kmp.computeSuffix());
        }  
        kmp.KMPMatch();  
    }  
}
