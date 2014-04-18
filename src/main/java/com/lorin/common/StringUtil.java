package com.lorin.common;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;


public class StringUtil {
    private static final String pattern_ua_android = ".*Android[\\s\\/_]*([2-9][\\._][1-9][0-9_\\.]*).+Safari.*";

    private static final String pattern_ua_android_no_version = ".*Android[\\s\\/_;\\-]+[^0-9].+Safari.*";

    private static final String patter_ua_iphone = ".*OS [3-9]_.*";
    
    public static final Pattern paramPattern = Pattern.compile("[^|\\?|&]?version=([^&]*)");
    
    /**
     * @param args
     */
    public static void main(String[] args) {
//      System.out.println(StringEscapeUtils.escapeHtml("><img src=\"www.safs.jpg\" />"));
//      System.out.println(StringEscapeUtils.unescapeHtml("&lt;script&gt;alert(111)&lt;/script&gt;"));
//      System.out.println(StringUtils.isBlank("") + "\t" + StringUtils.isBlank(null));
//      System.out.println(StringUtils.isEmpty("") + "\t" + StringUtils.isEmpty(null));
//      System.out.println(StringUtils.isNotBlank("") + "\t" + StringUtils.isNotBlank(null));
//      System.out.println(StringUtils.isNotEmpty("") + "\t" + StringUtils.isNotEmpty(null));
//      System.out.println(StringUtils.isNumeric("asdf") + "\t" + StringUtils.isNumeric("123"));
//        System.out.println(FastDateFormat.getInstance().format(new Date()));
//      String user1 = "LG_P503/1.0 Android/4.0.1 Release/5.20.2010 Browser/KHTML (Mozilla/5.0 AppleWebKit/533.1 Version/4.0 Mobile Safari/533.1)";  
      //        String user2 = "Mozilla/5.0 (iPad;CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A344 Safari/7534.48.3";
//              System.out.println(isOriginalBrowser(user1));
//      System.out.println("abaabc".charAt(-1));
//      Map<String,Integer> map = new HashMap<String,Integer>();
//      map.put("WapRemoteLogger2:tcp -h 10.3.16.182 -p 9999 -t 300", 1);
//      map.put("WapRemoteLogger2:tcp -h 10.3.16.182 -p 9999", 2);
      System.out.println("5".compareToIgnoreCase("9") >= 0);
      
    }
    
    private static String getWapRedirect(String srcUrl){
        if (!StringUtils.isEmpty(srcUrl)) {
            if (srcUrl != null && srcUrl.indexOf("redirect=") > 0) {
                srcUrl = srcUrl.substring(srcUrl.indexOf("redirect=") + "redirect=".length() );
                return srcUrl;
            }
        }
        return srcUrl;
    }
    
    /**
     * 判断是否原生浏览器
     * 
     * @param userAgent
     * @return
     */
    public static boolean isOriginalBrowser(String userAgent) {
        if (userAgent != null
                && (userAgent.matches(patter_ua_iphone) || userAgent.matches(pattern_ua_android) || userAgent
                        .matches(pattern_ua_android_no_version))) {
            return true;
        }
        return false;
    }
    /**
     * 计算token时用的一个随机字符串，目的是不让用户猜到算法
     */
    public static final String TOKEN_PREFIX = "843a3582";
    /**
     * 根据指定ticket计算requestToken
     * @param ticket
     * @return
     */
    public static String digestToken(String ticket) {
        return Integer.toHexString((TOKEN_PREFIX + ticket).hashCode());
    }
    
    public static boolean getUserSmsTime(Date lastLoginTime){
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(lastLoginTime);
        int aM = c.get(Calendar.MINUTE);
        int aH = c.get(Calendar.HOUR_OF_DAY);
        c.setTime(now);
        int bM = c.get(Calendar.MINUTE);
        int bH = c.get(Calendar.HOUR_OF_DAY);
        return bM-aM >= 10 || bH>aH;
    }

    public static String statusCut(String str, int length) {

        if (StringUtils.isBlank(str) || length <= 0) return "";

        if (str.length() <= length) return str;

        int tlength = 0;
        int maxLength = length * 2;
        int i;
        boolean bq = false;
        boolean left = false;
        boolean right = false;
        for (i = 0; i < str.length() && tlength <= maxLength; i++) {
            if (str.charAt(i) == '(') bq = true;
            if (str.charAt(i) == ')') bq = false;
            if (CharUtils.isAscii(str.charAt(i))) ++tlength;
            else tlength += 2;
        }
        if (i == str.length() && tlength <= maxLength) return str;
        maxLength -= 2;
        while ((tlength > maxLength || bq) && i!=0) {
            if (str.charAt(i - 1) == '(') {
                bq = false;
                left = true;
            }
            if (str.charAt(i - 1) == ')') {
                bq = true;
                right = true;
            }
            if(!left && right){
                bq = false;
            }
            if (CharUtils.isAscii(str.charAt(i - 1))) --tlength;
            else tlength -= 2;
            --i;
        }

        return str.substring(0, i) + "...";
    }
    public static String cut(String str, int length) {

        if (StringUtils.isBlank(str) || length <= 0) return "";

        if (str.length() <= length) return str;

        int tlength = 0;
        int maxLength = length * 2;
        int i;
        for (i = 0; i < str.length() && tlength <= maxLength; i++) {
            if (CharUtils.isAscii(str.charAt(i))) ++tlength;
            else tlength += 2;
        }
        if (i == str.length() && tlength <= maxLength) return str;
        maxLength -= 2;
        while (tlength > maxLength) {
            if (CharUtils.isAscii(str.charAt(i - 1))) --tlength;
            else tlength -= 2;
            --i;
        }
        return str.substring(0, i) + "...";
    }
}
