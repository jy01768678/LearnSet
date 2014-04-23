package com.lorin.job;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;


public class HelloScheduling {

    /**
     * @param args
     * @throws SchedulerException 
     */
    public static void main(String[] args) throws SchedulerException {
        // TODO Auto-generated method stub
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        
    }

}
