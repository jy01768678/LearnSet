package com.lorin.job;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

public class CronWithCalendarScheduling {

    public static void main(String[] args) throws SchedulerException, ParseException {
        Calendar cal = Calendar.getInstance();  
        cal.set(2010, Calendar.OCTOBER, 31);  
          
        HolidayCalendar calendar  = new HolidayCalendar();  
        calendar.addExcludedDate(cal.getTime());  
          
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();  
        scheduler.start();  
          
        scheduler.addCalendar("calendar", calendar, true, false);  
          
        JobDetail jobDetail =  JobBuilder.newJob(MessageJob.class).withIdentity("messageJob",  
                Scheduler.DEFAULT_GROUP).build();
          
        Map map = jobDetail.getJobDataMap();  
        map.put("message", "This is a message from Quartz");  
  
        String cronExpression = "3/5 * 17,18,19,20 * * ?";  
          
        Trigger trigger = TriggerBuilder
        .newTrigger()
        .withIdentity("trigger_1","group_1")
        .startNow()
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10) //时间间隔
                .withRepeatCount(5)        //重复次数(将执行6次)
                )
        .build();
  
          
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
