package com.lorin.docker;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class JSONUtil {
	/** 
	    * 将一个实体类对象转化成JSON数据格式
	    * @param obj 
	    *      实体类对象 
	    * @return 
	    *       JSON数据格式字符串 
	    */  
	   public static String beanToJson(Object obj){ 
		   if(obj == null){
			   return StringUtils.EMPTY;
		   }
	       return JSONObject.fromObject(obj).toString();  
	   }  
	     
	   /** 
	    * 将Json格式的字符串转换成指定的对象返回 
	    * 
	    * @param jsonStr 
	    *         要转化的Json格式的字符串 
	    * @param classType 
	    *        指定转化对象类型 
	    * @return 
	    *       转化后的对象 
	    */  
	    public static <T> T jsonToBean(String jsonStr, Class<T> classType) {
	 	   if(StringUtils.isEmpty(jsonStr)){
			   return null;
		   }
	       JSONObject jsonObj = JSONObject.fromObject(jsonStr);  
	       Object obj = JSONObject.toBean(jsonObj, classType);  
	       return (T) obj;
	    }
	   
	    /**
	     * JSON格式字符串转Java对象，支持复杂Java对象
	     * @param jsonStr	JSON类型字符串
	     * @param classType	要转换的对象类型
	     * @param classMap	对象内部引用其他类型所组成的map
	     * @return
	     * @author mahaiyuan@55tuan.com
	     * @since 2015年6月3日下午3:29:13
	     */
	    public static <T> T jsonToBean(String jsonStr, Class<T> classType, Map classMap){
	    	if(StringUtils.isEmpty(jsonStr)){
				   return null;
			   }
		       JSONObject jsonObj = JSONObject.fromObject(jsonStr);  
		       Object obj = JSONObject.toBean(jsonObj, classType, classMap);  
		       return (T) obj;
	    }
	    
		/**
	     *  将map的数据转换成json格式的字符串
	     * @param jsonStr 
	     *     要转化的Json格式的字符串 的map
		 */
	   public static String mapToJson(Map<String, String> map) {
			if (null == map)
				return "";
			JSONObject jsonObj = JSONObject.fromObject(map);
			String str = jsonObj.toString();
			return str;
		}
}
