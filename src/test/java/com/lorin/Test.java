package com.lorin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.appadhoc.javasdk.AdhocSdk;
import com.appadhoc.javasdk.ExperimentFlags;
import com.appadhoc.javasdk.T;

public class Test {

	public static void main(String[] args) {
//		SafeSingleton singleton = SafeSingleton.INSTANCE;
//		singleton.println();
		System.out.printf("%x\n", 31558);
		AtomicReference<Thread> owner =new AtomicReference<Thread>();
		AtomicInteger serviceNum = new AtomicInteger();
		List<String> tst = new ArrayList<String>();
		tst.add("1");
		tst.add("2");
		System.out.println(tst.toString());
		System.out.println(1+'a');
		System.out.println(1+"a");
		String result = "bacis[0]";
		System.out.println(result.substring(0,result.indexOf("[")));
		String test = new String();
//		getRealIp("https://10.9.120.22:2376");
//		long mem = 134926032896l;
//		
//		JSONObject cpu = new JSONObject();
//		cpu.put("region","asdfa");
//		cpu.put("val", "111");
//		JSONObject cpu1 = new JSONObject();
//		cpu1.put("region","1231231");
//		cpu1.put("val", "111");
//		JSONArray array = new JSONArray();
//		array.add(cpu);
//		array.add(cpu1);
//		System.out.println(array.toString());
//		getUrl("/backend/cluster/service/1/docker.do");
//		testIntern();
		String evn = "${mysqlPasswordGener.findPassword(\"611111111117\")}";
		System.out.println(evn.substring(evn.indexOf("(\"")+2,evn.indexOf("\")")));
	}
	
	public static void testIntern(){
		String s = new String("1");
	    s.intern();
	    String s2 = "1";
	    System.out.println(s == s2);

	    String s3 = new String("1") + new String("1");
	    s3.intern();
	    String s4 = "11";
	    System.out.println(s3 == s4);
	}
	
	public static void getRealIp(String address){
		String regex = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}";
		Pattern p = Pattern.compile(regex);  
	    Matcher m = p.matcher(address);  
	    while (m.find()) {  
	        System.out.println(m.group());  
	    } 
	}
	
	public static void getUrl(String url){
		String regex = "/backend/template/[1-9]+/config/list.do";
		
		System.out.println("result:"+url.matches("/backend/cluster/service/[1-9]+/[a-zA-Z]+.do"));
		
		Pattern p = Pattern.compile(regex);  
	    Matcher m = p.matcher(url);  
	    while (m.find()) {  
	        System.out.println(m.group());  
	    } 
	}
}
