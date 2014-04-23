package com.lorin.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class HelloWorldJob implements Job{

    @Override
    public void execute(JobExecutionContext content) throws JobExecutionException {
        // TODO Auto-generated method stub
        //实现你的业务逻辑  
        System.out.println("Hello!" + content.getJobDetail().getJobDataMap().get("message"));  
    }

}
