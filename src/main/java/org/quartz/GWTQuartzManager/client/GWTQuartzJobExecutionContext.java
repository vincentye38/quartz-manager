package org.quartz.GWTQuartzManager.client;

import java.io.Serializable;
import java.util.Date;

import org.quartz.GWTQuartzManager.share.GWTKey;




public class GWTQuartzJobExecutionContext implements Serializable {
	GWTKey jobKey;
	GWTKey triggerKey;
	long jobRunTime;
	Date fireTime;
	Date nextFireTime;
	Date scheduledFireTime;
	Date previousFireTime;
	String fireInstanceId;
	public GWTKey getJobKey() {
		return jobKey;
	}
	public void setJobKey(GWTKey jobKey) {
		this.jobKey = jobKey;
	}
	public GWTKey getTriggerKey() {
		return triggerKey;
	}
	public void setTriggerKey(GWTKey triggerKey) {
		this.triggerKey = triggerKey;
	}
	public long getJobRunTime() {
		return jobRunTime;
	}
	public void setJobRunTime(long jobRunTime) {
		this.jobRunTime = jobRunTime;
	}
	public Date getFireTime() {
		return fireTime;
	}
	public void setFireTime(Date fireTime) {
		this.fireTime = fireTime;
	}
	public Date getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public Date getScheduledFireTime() {
		return scheduledFireTime;
	}
	public void setScheduledFireTime(Date scheduledFireTime) {
		this.scheduledFireTime = scheduledFireTime;
	}
	public Date getPreviousFireTime() {
		return previousFireTime;
	}
	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}
	public String getFireInstanceId() {
		return fireInstanceId;
	}
	public void setFireInstanceId(String fireInstanceId) {
		this.fireInstanceId = fireInstanceId;
	}
}
