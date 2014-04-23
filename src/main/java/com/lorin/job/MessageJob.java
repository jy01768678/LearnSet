package com.lorin.job;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class MessageJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        Map properties = context.getJobDetail().getJobDataMap();  
        
        System.out.println("Previous Fire Time: " + context.getPreviousFireTime());  
        System.out.println("Current Fire Time: " + context.getFireTime());  
        System.out.println("Next Fire Time: " + context.getNextFireTime());  
        System.out.println(properties.get("message"));  
    }

}
