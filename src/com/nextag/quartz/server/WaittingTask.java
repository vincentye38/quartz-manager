package com.nextag.quartz.server;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.BeanNameAware;

import com.nextag.spring.scheduling.quartz.QuartzJobBean;

public class WaittingTask extends QuartzJobBean implements BeanNameAware{
	int waitTime = 2;
	int exceptionTime = 0;
	
	public WaittingTask(){}
	
	public WaittingTask(String waitTime){
		this.waitTime = Integer.parseInt(waitTime);
	}
	
	public int getExceptionTime() {
		return exceptionTime;
	}



	public void setExceptionTime(int exceptionTime) {
		this.exceptionTime = exceptionTime;
	}



	public int getWaitTime() {
		return waitTime;
	}



	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}


	String name;
	public void setBeanName(String name){
		this.name = name;
	}
	
	
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println(name + " job is launched at "+new Date());
		if (exceptionTime > 0 && exceptionTime <= waitTime){
			try {
				Thread.sleep(exceptionTime * 1000);
				System.out.println(name + " job throws exception at " + new Date());
			} catch (InterruptedException e) {
			}
			throw new JobExecutionException("error");
		} else {
			try {
				Thread.sleep(waitTime * 1000);
				System.out.println(name + " job is finished at " + new Date());
			} catch (InterruptedException e) {
			}
		}
	}
}
