package com.lorin.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

	protected static Integer getTimeWithDay(int days) {
		long now = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.HOUR, 0);
		System.out.println("set date:" + new Date(cal.getTimeInMillis()));
		if (cal.getTimeInMillis() > now) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.add(Calendar.DAY_OF_MONTH,-days);		
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String day = df.format(cal.getTime());
		return Integer.valueOf(day);
	}
	
	public static long getNowTimestamp10Long(){
		long curTime = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(curTime);
        cal.add(Calendar.HOUR, -8);
		String str = String.valueOf(cal.getTime().getTime()).substring(0, 10);
		return new Long(0).parseLong(str);
	}
	
	/**
	 * 格林尼治时间戳转化为东八区时间
	 * @param timeStamp  格林尼治时间戳
	 * @param format  日期格式 exp:yyyymmddhhnnss
	 * @return 东八区的时间 yyyy-MM-dd hh:mm:ss
	 */
	public static String GMTSFormatToCT(long timeStamp,String format){
		
		 DateFormat dateFormat = new SimpleDateFormat(format);
		 Calendar cal = Calendar.getInstance();
	     cal.setTimeInMillis(timeStamp*1000L);
	     cal.add(Calendar.HOUR,+8);    
		 return dateFormat.format((new Date(cal.getTimeInMillis())));
	}
	public static String dateChnFormat(long timeStamp,String format){
		 return new SimpleDateFormat(format).format((new Date((timeStamp*1000L))));
	}
	
	public static String dateChnFormatNo1000(long timeStamp,String format){
		 return new SimpleDateFormat(format).format((new Date((timeStamp))));
	}
	/**
	 * 
	 * @param date
	 * @param format
	 * @return 当天凌晨时间 不减8小时
	 * 2012-7-20 下午06:04:45 wh
	 */
	public static long getCurrDateStart(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		String str=sf.format(date);
		try {
			calendar.setTime(sf.parse(str));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.getTime().getTime();
	}
	 public static long getNowTimestamp10LongNoSub8(){
	        long curTime = System.currentTimeMillis();
	        String str = String.valueOf(curTime).substring(0, 10);
	        return Long.parseLong(str);
	    }
	 public static long geTimestamp10Long(String date){
         long curTime = 0;
        
            if(!StringUtils.isBlank(date)){
                try {
                curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
                String str = String.valueOf(curTime).substring(0, 10);
                curTime = Long.parseLong(str);
                } catch (ParseException e) {
                    try {
                        curTime = new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
                        String str = String.valueOf(curTime).substring(0, 10);
                        curTime = Long.parseLong(str);
                    } catch (ParseException e1) {
                    }
                   
                }
            }
       
        return curTime;
     }

	    /**
	     * �õ����쿪ʼʱ��
	     * @return
	     */
	    public static long getDayBeginTime() {
	        Calendar calendar = GregorianCalendar.getInstance();
	        long curTime =  new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
	        String str = String.valueOf(curTime).substring(0, 10);
	        return Long.parseLong(str);
	    }
	    
	    /**
	     * �õ��������ʱ��
	     * @return
	     */
	     public static long getDayEndTime() {
	        Calendar calendar = GregorianCalendar.getInstance();
	        calendar.add(Calendar.DAY_OF_MONTH, 1);
	        long curTime = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
	        String str = String.valueOf(curTime).substring(0, 10);
	        return Long.parseLong(str);
	    }
	 
	 public static String formatDate(int time) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time * 1000L);
			return dateFormat.format((new Date(cal.getTimeInMillis())));
		}
	 
	 public static void moneySaveTime(){
		 long time = System.currentTimeMillis();
		 long l = time-60000; 
			Timestamp timeStamp = new Timestamp(l); 
			String timeString = timeStamp.toString(); 
			timeString = timeString.substring(0, 17)+"00.000";  
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
			try {
				Date timeDate = sdf.parse(timeString);
				l=timeDate.getTime();
				System.out.println("moneySave1:"+dateChnFormatNo1000(l, "yyyy-MM-dd HH:mm:ss.SSS"));
			} catch (ParseException e) {
				
			}
			long endTime = l+60000;
			System.out.println("moneySave2:"+dateChnFormatNo1000(endTime, "yyyy-MM-dd HH:mm:ss.SSS"));
			
	 }
	 
	 /***
		 * 格式化时间并且-8小时
		 * @param dateString
		 * @return
		 */
	public static long dateStringFormatNoSub8(String dateString){
		try {			if(!StringUtils.isEmpty(dateString)){
			   String date = "yyyy-MM-dd HH:mm:ss" ;
			  SimpleDateFormat sf = new SimpleDateFormat(date);
		       Date d = sf.parse(dateString);
		        long curTime = d.getTime();
//				Calendar cal = Calendar.getInstance();
//		        cal.setTimeInMillis(curTime);
				return d.getTime()/1000;
			}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0l;
	}
	 public static int getpreviousYear(int times){
    	 Calendar calendar = GregorianCalendar.getInstance();
    	 calendar.add(Calendar.YEAR, times);
    	 return calendar.get(Calendar.YEAR);
     }
	 public static long getDayLastYearTime() {
	        Calendar calendar = GregorianCalendar.getInstance();
	        calendar.add(Calendar.YEAR, -1);
	        String str = String.valueOf(calendar.getTimeInMillis()).substring(0, 10);
	        return Long.parseLong(str);
	    }
	public static void main(String[] args) throws ParseException {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		System.out.println("年份："+cal.get(Calendar.YEAR) + "\t"+getDayLastYearTime());
//		System.out.println((cal.getTime().getTime()/1000));
		//1374086337       1181186915
//		System.out.println(1375257549 + 20*24*3600);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MONTH, -2);//截止前3个月
		rightNow.set(Calendar.DAY_OF_MONTH, 1);
		String beginTime = sdf.format(rightNow.getTime());
//		System.out.println(beginTime);
//		System.out.println(geTimestamp10Long(beginTime));//
		System.out.println("当前时间没有减去8："+getNowTimestamp10LongNoSub8());
		//2013-11-11 16:43:51
		System.out.println("没有+8:"+dateChnFormat(1396484820, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("没有+8:"+dateChnFormat(1396968224, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("没有-8:"+GMTSFormatToCT(1395182760, "yyyy-MM-dd "));
		System.out.println("ddd:"+getCurrDateStart(new Date()) / 1000);
		System.out.println(formatDate(0) + "\t" + formatDate(1388289755));
		System.out.println("当天开始结束时间："+getDayBeginTime()+"\t"+getDayEndTime());
		System.out.println(dateStringFormatNoSub8("2014-04-01 23:59:59"));
		System.out.println(getpreviousYear(1) );
//		System.out.println(cal.get(Calendar.DATE));
//		System.out.println(cal.getFirstDayOfWeek());
//		System.out.println(cal.get(Calendar.DAY_OF_MONTH));
//		System.out.println(cal.get(Calendar.YEAR));
//		System.out.println(cal.get(Calendar.MONTH) + 1);
//		System.out.println(getTimeWithDay(30));
//		System.out.println(1342943940 / (24 * 3600 * 365));
//		moneySaveTime();
	}
}
