package com.lorin;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class decodeTest {

	@Test
	public void decodeStr(){
		try {
			JeeheDES des = new JeeheDES();
			String md5EncodeStr = "wkwkwkkk";
			//afb9fa67f64411eb -- 852327
			//f7acf609c79bf624 -- 8523271
			//beb3e7efa3723e50 -- 502271
			//5179cce9044a7be1 -- 502266
			//8304906a3d973b9af42664ba748dd429
			//69a415532b6cbe6c -- 607335
			System.out.println(des.encrypt(""+0) + " -- " +des.encrypt("55tuansmskeyDetail20150410"));
			System.out.println(des.encrypt("199169594958") + " -- " +des.encrypt("875869"));
			System.out.println(des.decrypt("")+"\t");
			System.out.println(des.decrypt("e0b8e5edb62eb832b10a36e3851711a0")+"\t");
			System.out.println(des.decrypt("037e9abcb3eb81c1")+"\t");
			System.out.println(new JeeheDES().decrypt(""));
			String title = "八分田咖啡&#8226;精致果木牛扒";
//			System.out.println(Integer.parseInt("0.0"));
//			String userid = new BASE64Encoder().encode("607335".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testUrl(String code) throws IOException{
			String str[] = code.split("-");
			String str1[] = str[2].split("invite");
			String userid = str1[0];
			System.out.println("userid="+new String(new BASE64Decoder().decodeBuffer(userid)));
	}
	
	public static double length(String value) {  
        double valueLength = 0;  
        String chinese = "[\u4e00-\u9fa5]";  
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1  
        for (int i = 0; i < value.length(); i++) {  
            // 获取一个字符  
            String temp = value.substring(i, i + 1);  
            // 判断是否为中文字符  
            if (temp.matches(chinese)) {  
                // 中文字符长度为1  
                valueLength += 2;  
            } else {  
                // 其他字符长度为0.5  
                valueLength += 1;  
            }  
        }  
        //进位取整  
        return  Math.ceil(valueLength);  
    }  
}
