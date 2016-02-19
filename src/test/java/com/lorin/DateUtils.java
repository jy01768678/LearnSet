package com.lorin;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mysql.jdbc.TimeUtil;
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
	
	public static String dateChnFormat(String format){
		 return new SimpleDateFormat(format).format(new Date());
	}
	public static long dateStringFormat(String dateString,String format){
		try {			if(!StringUtils.isEmpty(dateString)){
			  SimpleDateFormat sf = new SimpleDateFormat(format);
		       Date d = sf.parse(dateString);
		        long curTime = d.getTime();
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.HOUR, -8);
		        cal.setTimeInMillis(curTime);
				return cal.getTime().getTime()/1000;
			}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0l;
	}
	public static long getCurrDateStartLottery(Date date){
		String start1=dateChnFormat("yyyy-MM-dd")+" 00:00:00";
		return dateStringFormat(start1,"yyyy-MM-dd hh:mm:ss");
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
//	    public static long getDayBeginTime(int count) {
//	        Calendar calendar = GregorianCalendar.getInstance();
//	        calendar.add(Calendar.DAY_OF_MONTH, count);
//	        long curTime =  new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
//	        String str = String.valueOf(curTime).substring(0, 10);
//	        return Long.parseLong(str);
//	    }
	    
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
	public static long getDayEndTime(long time) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(time);
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

	 public static long getParamDayEndTime(int time){
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTimeInMillis(time * 1000L);
		 calendar.add(Calendar.DAY_OF_MONTH, 1);
		 long curTime = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
		 String str = String.valueOf(curTime).substring(0, 10);
		 return Long.parseLong(str);
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
			System.out.println("moneySave2:" + dateChnFormatNo1000(endTime, "yyyy-MM-dd HH:mm:ss.SSS"));
			
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
	 public static long getNowTimestamp10LongMonth(int month){
	    	long curTime = System.currentTimeMillis();
			Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(curTime);
	        cal.add(Calendar.MONTH, -month);
			String str = String.valueOf(cal.getTime().getTime()).substring(0, 10);
			return new Long(0).parseLong(str);
	    }
	 public static int getDayDifferenceByTS(long firstTime,long secondTime){
			long day = (Math.abs(firstTime-secondTime))/(24*60*60);
			BigDecimal bd = new BigDecimal(day);
			int newDay = Integer.parseInt(bd.setScale(BigDecimal.ROUND_UP).toPlainString());
	        return newDay;
		}
	 public static int getNowTimestamp10Int(){
			long curTime = System.currentTimeMillis();
			Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(curTime);
	        cal.add(Calendar.HOUR, -8);
			String str = String.valueOf(cal.getTime().getTime()).substring(0, 10);
			return new Integer(0).parseInt(str);
		}
	 public static long getDayBeginTime(int count) {
			Calendar calendar = GregorianCalendar.getInstance();
			if (count != 0) {
				calendar.add(Calendar.DAY_OF_MONTH, count);
			}
			long curTime = new GregorianCalendar(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar
							.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
			String str = String.valueOf(curTime).substring(0, 10);
			return Long.parseLong(str);
		}

		public static long getDayEndTime(int count) {
			Calendar calendar = GregorianCalendar.getInstance();
			if (count != 0) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			long curTime = new GregorianCalendar(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar
							.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
			String str = String.valueOf(curTime).substring(0, 10);
			return Long.parseLong(str);
		}
		
		public static String getFormatTimeFormat(String time,String orignalFormat,String targetFormat){
			SimpleDateFormat sf = new SimpleDateFormat(orignalFormat);
			try {
				Date dateTime = sf.parse(time);
				sf = new SimpleDateFormat(targetFormat);
				return sf.format(dateTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return "";
		}
		
		public static String subDayFromCurrDate(int subDay){
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			long curTime = d.getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(curTime);
			cal.add(Calendar.DAY_OF_MONTH, -subDay);
			return sf.format(cal.getTime());
		}

	public static int getNowTimestampSecond(){
		long curTime = System.currentTimeMillis()/1000;
		return (int)curTime;
	}

	public static long getRealOutTime(int claimTime){
		long a = ( getParamDayEndTime(claimTime) + 13 * 24 * 3600 );
		int b = getNowTimestampSecond();
		long c = a - b;
		long d = c / (24*3600);
		return  d;
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
		System.out.println("没有+8:"+dateChnFormat(1451383448, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("没有+8:"+dateChnFormat(1449072000, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("没有-8:"+GMTSFormatToCT(1381001805, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("-8 int:"+getNowTimestamp10Int());
		System.out.println("ddd:"+getCurrDateStart(new Date()) / 1000);
		System.out.println(formatDate(0) + "\t" + formatDate(1388289755));
		System.out.println("当天开始结束时间："+getDayBeginTime()+"\t"+getDayEndTime());
		System.out.println("字符串时间表示 ： "+dateStringFormatNoSub8("2015-12-21 14:00:00")+"--");
		//1413415931--1366940967
		System.out.println(getDayBeginTime(0)+"---"+getDayEndTime(1));
		System.out.println(getFormatTimeFormat("2014-08-05 14:00:00", "yyyy-MM-dd HH:mm:ss", "MM-dd HH:mm:ss"));
		System.out.println("-------------------------");
		System.out.println(formatDate(1449035961));
		System.out.println("get day start = "+getDayBeginTime());
		System.out.println("get day end = "+getDayEndTime());
		System.out.println("get param day end time = "+getParamDayEndTime(1449036114));
		//1449036114-1449075600
		System.out.println("get real out time = "+getRealOutTime(1451383448));
//		moneySaveTime();
	}
}
