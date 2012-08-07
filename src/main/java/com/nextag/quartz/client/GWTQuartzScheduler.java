/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nextag.quartz.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nextag.quartz.share.GWTCronTrigger;
import com.nextag.quartz.share.GWTJobDetail;
import com.nextag.quartz.share.GWTKey;
import com.nextag.quartz.share.GWTSchedulerException;
import com.nextag.quartz.share.GWTTrigger;

@RemoteServiceRelativePath("springGwtServices/GWTQuartzScheduler")
public interface GWTQuartzScheduler extends RemoteService {
	
	
	/*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Interface.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * Returns the name of the <code>Scheduler</code>.
     */
    String getSchedulerName() throws GWTSchedulerException;

    /**
     * Returns the instance Id of the <code>Scheduler</code>.
     */
    String getSchedulerInstanceId() throws GWTSchedulerException;

    
    ///////////////////////////////////////////////////////////////////////////
    ///
    /// Scheduler State Management Methods
    ///
    ///////////////////////////////////////////////////////////////////////////
    
    /**
     * Starts the <code>Scheduler</code>'s threads that fire <code>{@link Trigger}s</code>.
     * When a scheduler is first created it is in "stand-by" mode, and will not
     * fire triggers.  The scheduler can also be put into stand-by mode by
     * calling the <code>standby()</code> method. 
     * 
     * <p>
     * The misfire/recovery process will be started, if it is the initial call
     * to this method on this scheduler instance.
     * </p>
     * 
     * @throws SchedulerException
     *           if <code>shutdown()</code> has been called, or there is an
     *           error within the <code>Scheduler</code>.
     *
     * @see #startDelayed(int)
     * @see #standby()
     * @see #shutdown()
     */
    void start() throws GWTSchedulerException;

    /**
     * Calls {#start()} after the indicated number of seconds.
     * (This call does not block). This can be useful within applications that
     * have initializers that create the scheduler immediately, before the
     * resources needed by the executing jobs have been fully initialized.
     *
     * @throws SchedulerException
     *           if <code>shutdown()</code> has been called, or there is an
     *           error within the <code>Scheduler</code>.
     *
     * @see #start() 
     * @see #standby()
     * @see #shutdown()
     */
    void startDelayed(int seconds) throws GWTSchedulerException;

    /**
     * Whether the scheduler has been started.  
     * 
     * <p>
     * Note: This only reflects whether <code>{@link #start()}</code> has ever
     * been called on this Scheduler, so it will return <code>true</code> even 
     * if the <code>Scheduler</code> is currently in standby mode or has been 
     * since shutdown.
     * </p>
     * 
     * @see #start()
     * @see #isShutdown()
     * @see #isInStandbyMode()
     */    
    boolean isStarted() throws GWTSchedulerException;
    
    /**
     * Temporarily halts the <code>Scheduler</code>'s firing of <code>{@link Trigger}s</code>.
     * 
     * <p>
     * When <code>start()</code> is called (to bring the scheduler out of 
     * stand-by mode), trigger misfire instructions will NOT be applied
     * during the execution of the <code>start()</code> method - any misfires 
     * will be detected immediately afterward (by the <code>JobStore</code>'s 
     * normal process).
     * </p>
     * 
     * <p>
     * The scheduler is not destroyed, and can be re-started at any time.
     * </p>
     * 
     * @see #start()
     * @see #pauseAll()
     */
    void standby() throws GWTSchedulerException;

    /**
     * Reports whether the <code>Scheduler</code> is in stand-by mode.
     * 
     * @see #standby()
     * @see #start()
     */
    boolean isInStandbyMode() throws GWTSchedulerException;

    /**
     * Halts the <code>Scheduler</code>'s firing of <code>{@link Trigger}s</code>,
     * and cleans up all resources associated with the Scheduler. Equivalent to
     * <code>shutdown(false)</code>.
     * 
     * <p>
     * The scheduler cannot be re-started.
     * </p>
     * 
     * @see #shutdown(boolean)
     */
    void shutdown() throws GWTSchedulerException;

    /**
     * Halts the <code>Scheduler</code>'s firing of <code>{@link Trigger}s</code>,
     * and cleans up all resources associated with the Scheduler.
     * 
     * <p>
     * The scheduler cannot be re-started.
     * </p>
     * 
     * @param waitForJobsToComplete
     *          if <code>true</code> the scheduler will not allow this method
     *          to return until all currently executing jobs have completed.
     * 
     * @see #shutdown
     */
    void shutdown(boolean waitForJobsToComplete)
        throws GWTSchedulerException;

    /**
     * Reports whether the <code>Scheduler</code> has been shutdown.
     */
    boolean isShutdown() throws GWTSchedulerException;

   

    

    
    ///////////////////////////////////////////////////////////////////////////
    ///
    /// Scheduling-related Methods
    ///
    ///////////////////////////////////////////////////////////////////////////
   
    /**
     * Remove the indicated <code>{@link Trigger}</code> from the scheduler.
     * 
     * <p>If the related job does not have any other triggers, and the job is
     * not durable, then the job will also be deleted.</p>
     */
    boolean unscheduleJob(GWTKey triggerKey)
        throws GWTSchedulerException;

    /**
     * Remove all of the indicated <code>{@link Trigger}</code>s from the scheduler.
     * 
     * <p>If the related job does not have any other triggers, and the job is
     * not durable, then the job will also be deleted.</p>
     * 
     * <p>Note that while this bulk operation is likely more efficient than
     * invoking <code>unscheduleJob(TriggerKey triggerKey)</code> several
     * times, it may have the adverse affect of holding data locks for a
     * single long duration of time (rather than lots of small durations
     * of time).</p> 
     */
    boolean unscheduleJobs(List<GWTKey> GWTKeys)
        throws GWTSchedulerException;
    
   
   
    /**
     * Delete the identified <code>Job</code> from the Scheduler - and any
     * associated <code>Trigger</code>s.
     * 
     * @return true if the Job was found and deleted.
     * @throws SchedulerException
     *           if there is an internal Scheduler error.
     */
    boolean deleteJob(GWTKey jobKey)
        throws GWTSchedulerException;

    /**
     * Delete the identified <code>Job</code>s from the Scheduler - and any
     * associated <code>Trigger</code>s.
     * 
     * <p>Note that while this bulk operation is likely more efficient than
     * invoking <code>deleteJob(JobKey jobKey)</code> several
     * times, it may have the adverse affect of holding data locks for a
     * single long duration of time (rather than lots of small durations
     * of time).</p>
     *  
     * @return true if all of the Jobs were found and deleted, false if 
     * one or more were not deleted.
     * @throws SchedulerException
     *           if there is an internal Scheduler error.
     */
    boolean deleteJobs(List<GWTKey> jobKeys)
        throws GWTSchedulerException;
    
    /**
     * Trigger the identified <code>{@link org.quartz.JobDetail}</code>
     * (execute it now).
     */
    void triggerJob(GWTKey jobKey)
        throws GWTSchedulerException;


    /**
     * Pause the <code>{@link org.quartz.JobDetail}</code> with the given
     * key - by pausing all of its current <code>Trigger</code>s.
     * 
     * @see #resumeJob(JobKey)
     */
    void pauseJob(GWTKey jobKey)
        throws GWTSchedulerException;

   
    /**
     * Pause the <code>{@link Trigger}</code> with the given key.
     * 
     * @see #resumeTrigger(TriggerKey)
     */
    void pauseTrigger(GWTKey triggerKey)
        throws GWTSchedulerException;

    /**
     * <p>
     * Pause all of the <code>{@link Trigger}s</code> in the matching groups.
     * </p>
     *  
     */
    public void pauseTriggers(String groupName) throws GWTSchedulerException;
    
    /**
     * Resume (un-pause) the <code>{@link org.quartz.JobDetail}</code> with
     * the given key.
     * 
     * <p>
     * If any of the <code>Job</code>'s<code>Trigger</code> s missed one
     * or more fire-times, then the <code>Trigger</code>'s misfire
     * instruction will be applied.
     * </p>
     * 
     * @see #pauseJob(JobKey)
     */
    void resumeJob(GWTKey jobKey)
        throws GWTSchedulerException;


    /**
     * Resume (un-pause) the <code>{@link Trigger}</code> with the given
     * key.
     * 
     * <p>
     * If the <code>Trigger</code> missed one or more fire-times, then the
     * <code>Trigger</code>'s misfire instruction will be applied.
     * </p>
     * 
     * @see #pauseTrigger(TriggerKey)
     */
    void resumeTrigger(GWTKey triggerKey)
        throws GWTSchedulerException;

    void resumeTriggers(String groupName) throws GWTSchedulerException;
   
    /**
     * Pause all triggers - similar to calling <code>pauseTriggerGroup(group)</code>
     * on every group, however, after using this method <code>resumeAll()</code> 
     * must be called to clear the scheduler's state of 'remembering' that all 
     * new triggers will be paused as they are added. 
     * 
     * <p>
     * When <code>resumeAll()</code> is called (to un-pause), trigger misfire
     * instructions WILL be applied.
     * </p>
     * 
     * @see #resumeAll()
     * @see #pauseTriggers(org.quartz.impl.matchers.GroupMatcher)
     * @see #standby()
     */
    void pauseAll() throws GWTSchedulerException;

    /**
     * Resume (un-pause) all triggers - similar to calling 
     * <code>resumeTriggerGroup(group)</code> on every group.
     * 
     * <p>
     * If any <code>Trigger</code> missed one or more fire-times, then the
     * <code>Trigger</code>'s misfire instruction will be applied.
     * </p>
     * 
     * @see #pauseAll()
     */
    void resumeAll() throws GWTSchedulerException;

    /**
     * Get the names of all known <code>{@link org.quartz.JobDetail}</code>
     * groups.
     */
    ArrayList<String> getJobGroupNames() throws GWTSchedulerException;

    /**
     * Get the keys of all the <code>{@link org.quartz.JobDetail}s</code>
     * in the matching groups.
     * @param matcher Matcher to evaluate against known groups
     * @return Set of all keys matching
     * @throws SchedulerException On error
     */
    Set<GWTKey> getJobKeys(String groupName) throws GWTSchedulerException;
    
    /**
     * Get all <code>{@link Trigger}</code> s that are associated with the
     * identified <code>{@link org.quartz.JobDetail}</code>.
     * 
     * <p>The returned Trigger objects will be snap-shots of the actual stored
     * triggers.  If you wish to modify a trigger, you must re-store the
     * trigger afterward (e.g. see {@link #rescheduleJob(TriggerKey, Trigger)}).
     * </p>
     * 
     */
    List<? extends GWTTrigger> getTriggersOfJob(GWTKey jobKey)
        throws GWTSchedulerException;

    /**
     * Get the names of all known <code>{@link Trigger}</code> groups.
     */
    List<String> getTriggerGroupNames() throws GWTSchedulerException;

    /**
     * Get the names of all <code>{@link Trigger}</code> groups that are paused.
     */
    Set<String> getPausedTriggerGroups() throws GWTSchedulerException;
    
    /**
     * Get the <code>{@link JobDetail}</code> for the <code>Job</code>
     * instance with the given key.
     * 
     * <p>The returned JobDetail object will be a snap-shot of the actual stored
     * JobDetail.  If you wish to modify the JobDetail, you must re-store the
     * JobDetail afterward (e.g. see {@link #addJob(JobDetail, boolean)}).
     * </p>
     * 
     */
    GWTJobDetail getJobDetail(GWTKey jobKey)
        throws GWTSchedulerException;

    /**
     * Get the <code>{@link Trigger}</code> instance with the given key.
     * 
     * <p>The returned Trigger object will be a snap-shot of the actual stored
     * trigger.  If you wish to modify the trigger, you must re-store the
     * trigger afterward (e.g. see {@link #rescheduleJob(TriggerKey, Trigger)}).
     * </p>
     */
    GWTTrigger getTrigger(GWTKey triggerKey)
        throws GWTSchedulerException;

    /**
     * Get the current state of the identified <code>{@link Trigger}</code>.
     * 
     * @see Trigger.TriggerState
     */
    String getTriggerState(GWTKey triggerKey)
        throws GWTSchedulerException;


    /**
     * Request the interruption, within this Scheduler instance, of all 
     * currently executing instances of the identified <code>Job</code>, which 
     * must be an implementor of the <code>InterruptableJob</code> interface.
     * 
     * <p>
     * If more than one instance of the identified job is currently executing,
     * the <code>InterruptableJob#interrupt()</code> method will be called on
     * each instance.  However, there is a limitation that in the case that  
     * <code>interrupt()</code> on one instances throws an exception, all 
     * remaining  instances (that have not yet been interrupted) will not have 
     * their <code>interrupt()</code> method called.
     * </p>
     * 
     * <p>
     * This method is not cluster aware.  That is, it will only interrupt 
     * instances of the identified InterruptableJob currently executing in this 
     * Scheduler instance, not across the entire cluster.
     * </p>
     * 
     * @return true if at least one instance of the identified job was found
     * and interrupted.
     * @throws UnableToInterruptJobException if the job does not implement
     * <code>InterruptableJob</code>, or there is an exception while 
     * interrupting the job.
     * @see InterruptableJob#interrupt()
     * @see #getCurrentlyExecutingJobs()
     * @see #interrupt(String)
     */
    boolean interrupt(GWTKey jobKey) throws GWTSchedulerException;
    
    /**
     * Request the interruption, within this Scheduler instance, of the 
     * identified executing <code>Job</code> instance, which 
     * must be an implementor of the <code>InterruptableJob</code> interface.
     * 
     * <p>
     * This method is not cluster aware.  That is, it will only interrupt 
     * instances of the identified InterruptableJob currently executing in this 
     * Scheduler instance, not across the entire cluster.
     * </p>
     * 
     * @param fireInstanceId the unique identifier of the job instance to
     * be interrupted (see {@link JobExecutionContext#getFireInstanceId()}
     * @return true if the identified job instance was found and interrupted.
     * @throws UnableToInterruptJobException if the job does not implement
     * <code>InterruptableJob</code>, or there is an exception while 
     * interrupting the job.
     * @see InterruptableJob#interrupt()
     * @see #getCurrentlyExecutingJobs()
     * @see JobExecutionContext#getFireInstanceId()
     * @see #interrupt(JobKey)
     */
    boolean interrupt(String fireInstanceId) throws GWTSchedulerException;
    
       
    /**
     * Clears (deletes!) all scheduling data - all {@link Job}s, {@link Trigger}s
     * {@link Calendar}s.
     * 
     * @throws SchedulerException
     */
    void clear() throws GWTSchedulerException;
    
    ArrayList<GWTJobDetail> getJobDetails(String groupName) throws GWTSchedulerException;

    ArrayList<GWTJobDetail> getAllJobDetails() throws GWTSchedulerException;
    
    ArrayList<GWTTrigger> getTriggersByGroup(String groupName) throws GWTSchedulerException;
    
    ArrayList<GWTTrigger> getAllTriggers() throws GWTSchedulerException;
    
    Date scheduleJob(GWTCronTrigger GWTTrigger) throws GWTSchedulerException;
    
    ArrayList<GWTQuartzJobExecutionContext> getCurrentlyExecutingJobs() throws GWTSchedulerException;
    
    void addJob(GWTJobDetail gwtJobDetail, boolean replace) throws GWTSchedulerException;
    
    public class nopAsynCallback<T> implements AsyncCallback<T>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(T result) {
			// TODO Auto-generated method stub
			
		}
    	
    }
}
