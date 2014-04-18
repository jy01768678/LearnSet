package com.lorin.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class MathUtil {

    public static void main(String[] args) {
        double lat = 3.9962382E7;
        double lon = 1.1643905E8;
        double dis = 12612.2459;
//        DecimalFormat df = new DecimalFormat("#.000000");
//        System.out.println(new Double(lat/1000000) +" " + new Double(lon/1000000));
        System.out.println(distanceFormat(dis));
    }
    public static String distanceFormat(double distance){
        String disType = "2";
        double m = distance;
        //距离类型为公里 就 * 1000
        if("1".equals(disType)){
            m = distance * 1000;
        } 
        BigDecimal b = new BigDecimal(m);
        if(m >= 9999){
            b = new BigDecimal(m/1000);
            return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + "公里";
        } else if (m < 9999 && m > 1000){
            return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + "公里";
        } else if (m < 1000){
            return b.setScale(0, BigDecimal.ROUND_HALF_UP) + "米";
        }
        return m + "公里";
    }
}
