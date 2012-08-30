package com.nextag.quartz.share;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.view.client.ProvidesKey;

public class GWTTrigger implements Serializable {
	GWTKey key = new GWTKey();
	GWTKey jobKey = new GWTKey();
	String description;
	int priority;
	
	public enum TriggerState { NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED };
	
	public final static ProvidesKey<GWTTrigger> KEY_PROVIDER = new ProvidesKey<GWTTrigger>() {

		@Override
		public Object getKey(GWTTrigger item) {
			return item.getKey();
		}
	};
	
	public GWTKey getKey(){
		return key;
	}

    public GWTKey getJobKey(){
    	return jobKey;
    }
    
    /**
     * Return the description given to the <code>Trigger</code> instance by
     * its creator (if any).
     * 
     * @return null if no description was set.
     */
    public String getDescription(){
    	return description;
    }

    
    

    /**
     * The priority of a <code>Trigger</code> acts as a tiebreaker such that if 
     * two <code>Trigger</code>s have the same scheduled fire time, then the
     * one with the higher priority will get first access to a worker
     * thread.
     * 
     * <p>
     * If not explicitly set, the default value is <code>5</code>.
     * </p>
     * 
     * @see #DEFAULT_PRIORITY
     */
    public int getPriority(){
    	return priority;
    }

    /**
     * Used by the <code>{@link Scheduler}</code> to determine whether or not
     * it is possible for this <code>Trigger</code> to fire again.
     * 
     * <p>
     * If the returned value is <code>false</code> then the <code>Scheduler</code>
     * may remove the <code>Trigger</code> from the <code>{@link org.quartz.spi.JobStore}</code>.
     * </p>
     */
    boolean mayFireAgain;
    public boolean mayFireAgain(){
    	return mayFireAgain;
    }

    /**
     * Get the time at which the <code>Trigger</code> should occur.
     */
    Date startTime;
    public Date getStartTime(){
    	return startTime;
    }

    /**
     * Get the time at which the <code>Trigger</code> should quit repeating -
     * regardless of any remaining repeats (based on the trigger's particular 
     * repeat settings). 
     * 
     * @see #getFinalFireTime()
     */
    Date endTime;
    public Date getEndTime(){
    	return endTime;
    }

    /**
     * Returns the next time at which the <code>Trigger</code> is scheduled to fire. If
     * the trigger will not fire again, <code>null</code> will be returned.  Note that
     * the time returned can possibly be in the past, if the time that was computed
     * for the trigger to next fire has already arrived, but the scheduler has not yet
     * been able to fire the trigger (which would likely be due to lack of resources
     * e.g. threads).
     *
     * <p>The value returned is not guaranteed to be valid until after the <code>Trigger</code>
     * has been added to the scheduler.
     * </p>
     *
     * @see TriggerUtils#computeFireTimesBetween(Trigger, Calendar, Date, Date)
     */
    Date nextFireTime;
    public Date getNextFireTime(){
    	return nextFireTime;
    }

    /**
     * Returns the previous time at which the <code>Trigger</code> fired.
     * If the trigger has not yet fired, <code>null</code> will be returned.
     */
    Date previousFireTime;
    public Date getPreviousFireTime()
    {
    	return previousFireTime;
    }

   
    /**
     * Returns the last time at which the <code>Trigger</code> will fire, if
     * the Trigger will repeat indefinitely, null will be returned.
     * 
     * <p>
     * Note that the return time *may* be in the past.
     * </p>
     */
    Date finalFireTime;
    public Date getFinalFireTime(){
    	return finalFireTime;
    }

    /**
     * Get the instruction the <code>Scheduler</code> should be given for
     * handling misfire situations for this <code>Trigger</code>- the
     * concrete <code>Trigger</code> type that you are using will have
     * defined a set of additional <code>MISFIRE_INSTRUCTION_XXX</code>
     * constants that may be set as this property's value.
     * 
     * <p>
     * If not explicitly set, the default value is <code>MISFIRE_INSTRUCTION_SMART_POLICY</code>.
     * </p>
     * 
     * @see #MISFIRE_INSTRUCTION_SMART_POLICY
     * @see #updateAfterMisfire(Calendar)
     * @see SimpleTrigger
     * @see CronTrigger
     */
    int misfireInstruction;
    public int getMisfireInstruction(){
    	return misfireInstruction;
    }

	public void setKey(GWTKey key) {
		this.key = key;
	}

	public void setJobKey(GWTKey jobKey) {
		this.jobKey = jobKey;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setMayFireAgain(boolean mayFireAgain) {
		this.mayFireAgain = mayFireAgain;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}

	public void setFinalFireTime(Date finalFireTime) {
		this.finalFireTime = finalFireTime;
	}

	public void setMisfireInstruction(int misfireInstruction) {
		this.misfireInstruction = misfireInstruction;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	ArrayList<Pair<String, String>> jobDataMapEntryList = new ArrayList<Pair<String, String>>();

	public ArrayList<Pair<String, String>> getJobDataMapEntryList() {
		return jobDataMapEntryList;
	}

	public void setJobDataMapEntryList(ArrayList<Pair<String, String>> jobDataMapEntryList) {
		this.jobDataMapEntryList = jobDataMapEntryList;
	}
	
}
