package com.nextag.quartz.share;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.view.client.ProvidesKey;

public class GWTJobDetail implements Serializable{
	
	public final static ProvidesKey<GWTJobDetail> KEY_PROVIDER = new ProvidesKey<GWTJobDetail>() {

		@Override
		public Object getKey(GWTJobDetail item) {
			return item.getKey();
		}
	}; 
	
	public GWTJobDetail() {
	}
	
	public void setKey(GWTKey key) {
		this.key = key;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setJobClassName(String jobClass) {
		this.jobClass = jobClass;
	}
	public void setJobClass(Class<?> clazz){
		setJobClassName(clazz.getName());
	}
	
	public void setJobDataMapEntryList(ArrayList<Pair<String, String>> jobDataMapEntryList) {
		this.jobDataMapEntryList = jobDataMapEntryList;
	}
	public void setDurable(boolean durable) {
		this.durable = durable;
	}
	public void setPersistJobDataAfterExecution(boolean persistJobDataAfterExecution) {
		this.persistJobDataAfterExecution = persistJobDataAfterExecution;
	}
	public void setConcurrentExectionDisallowed(
			boolean concurrentExecutionDisallowed) {
		this.concurrentExecutionDisallowed = concurrentExecutionDisallowed;
	}
	public void setRequestsRecovery(boolean requestsRecovery) {
		this.requestsRecovery = requestsRecovery;
	}

	GWTKey key;
	public GWTKey getKey() {
		return key;
	}

	String description;
	public String getDescription() {
		return description;
	}

	String jobClass;
	public String getJobClassName() {
		return jobClass;
	}

	ArrayList<Pair<String, String>> jobDataMapEntryList = new ArrayList<Pair<String, String>>();
	public ArrayList<Pair<String, String>> getJobDataMapEntryList() {
		return jobDataMapEntryList;
	}

	boolean durable;
	public boolean isDurable() {
		return durable;
	}

	boolean persistJobDataAfterExecution;
	public boolean isPersistJobDataAfterExecution() {
		return persistJobDataAfterExecution;
	}

	boolean concurrentExecutionDisallowed;
	public boolean isConcurrentExectionDisallowed() {
		return concurrentExecutionDisallowed;
	}

	boolean requestsRecovery;
	public boolean isRequestsRecovery() {
		return requestsRecovery;
	}
	
}
