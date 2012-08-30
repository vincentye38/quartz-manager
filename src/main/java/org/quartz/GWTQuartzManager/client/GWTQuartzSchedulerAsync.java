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
import com.nextag.quartz.share.GWTCronTrigger;
import com.nextag.quartz.share.GWTJobDetail;
import com.nextag.quartz.share.GWTKey;
import com.nextag.quartz.share.GWTTrigger;

public interface GWTQuartzSchedulerAsync {
	
	
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
    void getSchedulerName(AsyncCallback<String> callback);

    /**
     * Returns the instance Id of the <code>Scheduler</code>.
     */
    void getSchedulerInstanceId(AsyncCallback<String> callback);

    
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
    void start(AsyncCallback<Void> callback);

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
    void startDelayed(int seconds, AsyncCallback<Void> callback);

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
    void isStarted(AsyncCallback<Boolean> callback);
    
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
    void standby(AsyncCallback<Void> callback);

    /**
     * Reports whether the <code>Scheduler</code> is in stand-by mode.
     * 
     * @see #standby()
     * @see #start()
     */
    void isInStandbyMode(AsyncCallback<Boolean> callback);

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
    void shutdown(AsyncCallback<Void> callback);

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
    void shutdown(boolean waitForJobsToComplete, AsyncCallback<Void> callback);

    /**
     * Reports whether the <code>Scheduler</code> has been shutdown.
     */
    void isShutdown(AsyncCallback<Boolean> callback);

   

    

    
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
    void unscheduleJob(GWTKey triggerKey, AsyncCallback<Boolean> callback);

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
    void unscheduleJobs(List<GWTKey> GWTKeys, AsyncCallback<Boolean> callback);
    
   
   
    /**
     * Delete the identified <code>Job</code> from the Scheduler - and any
     * associated <code>Trigger</code>s.
     * 
     * @param callback the callback to return true if the Job was found and deleted.
     * @throws SchedulerException
     *           if there is an internal Scheduler error.
     */
    void deleteJob(GWTKey jobKey, AsyncCallback<Boolean> callback);

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
     * @param callback the callback to return true if all of the Jobs were found and deleted, false if 
     * one or more were not deleted.
     * @throws SchedulerException
     *           if there is an internal Scheduler error.
     */
    void deleteJobs(List<GWTKey> jobKeys, AsyncCallback<Boolean> callback);
    
    /**
     * Trigger the identified <code>{@link org.quartz.JobDetail}</code>
     * (execute it now).
     */
    void triggerJob(GWTKey jobKey, AsyncCallback<Void> callback);


    /**
     * Pause the <code>{@link org.quartz.JobDetail}</code> with the given
     * key - by pausing all of its current <code>Trigger</code>s.
     * 
     * @see #resumeJob(JobKey)
     */
    void pauseJob(GWTKey jobKey, AsyncCallback<Void> callback);

   
    /**
     * Pause the <code>{@link Trigger}</code> with the given key.
     * 
     * @see #resumeTrigger(TriggerKey)
     */
    void pauseTrigger(GWTKey triggerKey, AsyncCallback<Void> callback);

    /**
     * <p>
     * Pause all of the <code>{@link Trigger}s</code> in the matching groups.
     * </p>
     *  
     */
    public void pauseTriggers(String groupName, AsyncCallback<Void> callback);
    
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
    void resumeJob(GWTKey jobKey, AsyncCallback<Void> callback);


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
    void resumeTrigger(GWTKey triggerKey, AsyncCallback<Void> callback);

    void resumeTriggers(String groupName, AsyncCallback<Void> callback);
   
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
    void pauseAll(AsyncCallback<Void> callback);

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
    void resumeAll(AsyncCallback<Void> callback);

    /**
     * Get the names of all known <code>{@link org.quartz.JobDetail}</code>
     * groups.
     */
    void getJobGroupNames(AsyncCallback<ArrayList<String>> callback);

    /**
     * Get the keys of all the <code>{@link org.quartz.JobDetail}s</code>
     * in the matching groups.
     * @param matcher Matcher to evaluate against known groups
     * @param callback the callback to return Set of all keys matching
     * @throws SchedulerException On error
     */
    void getJobKeys(String groupName, AsyncCallback<Set<GWTKey>> callback);
    
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
    void getTriggersOfJob(GWTKey jobKey, AsyncCallback<List<? extends GWTTrigger>> callback);

    /**
     * Get the names of all known <code>{@link Trigger}</code> groups.
     */
    void getTriggerGroupNames(AsyncCallback<List<String>> callback);

    /**
     * Get the names of all <code>{@link Trigger}</code> groups that are paused.
     */
    void getPausedTriggerGroups(AsyncCallback<Set<String>> callback);
    
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
    void getJobDetail(GWTKey jobKey, AsyncCallback<GWTJobDetail> callback);

    /**
     * Get the <code>{@link Trigger}</code> instance with the given key.
     * 
     * <p>The returned Trigger object will be a snap-shot of the actual stored
     * trigger.  If you wish to modify the trigger, you must re-store the
     * trigger afterward (e.g. see {@link #rescheduleJob(TriggerKey, Trigger)}).
     * </p>
     */
    void getTrigger(GWTKey triggerKey, AsyncCallback<GWTTrigger> callback);

    /**
     * Get the current state of the identified <code>{@link Trigger}</code>.
     * 
     * @see Trigger.TriggerState
     */
    void getTriggerState(GWTKey triggerKey, AsyncCallback<String> callback);


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
     * @param callback the callback to return true if at least one instance of the identified job was found
     * and interrupted.
     * @throws UnableToInterruptJobException if the job does not implement
     * <code>InterruptableJob</code>, or there is an exception while 
     * interrupting the job.
     * @see InterruptableJob#interrupt()
     * @see #getCurrentlyExecutingJobs()
     * @see #interrupt(String)
     */
    void interrupt(GWTKey jobKey, AsyncCallback<Boolean> callback);
    
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
     * @param callback the callback to return true if the identified job instance was found and interrupted.
     * @throws UnableToInterruptJobException if the job does not implement
     * <code>InterruptableJob</code>, or there is an exception while 
     * interrupting the job.
     * @see InterruptableJob#interrupt()
     * @see #getCurrentlyExecutingJobs()
     * @see JobExecutionContext#getFireInstanceId()
     * @see #interrupt(JobKey)
     */
    void interrupt(String fireInstanceId, AsyncCallback<Boolean> callback);
    
       
    /**
     * Clears (deletes!) all scheduling data - all {@link Job}s, {@link Trigger}s
     * {@link Calendar}s.
     * 
     * @throws SchedulerException
     */
    void clear(AsyncCallback<Void> callback);
    
    void getJobDetails(String groupName, AsyncCallback<ArrayList<GWTJobDetail>> callback);

    void getAllJobDetails(AsyncCallback<ArrayList<GWTJobDetail>> callback);
    
    void getTriggersByGroup(String groupName, AsyncCallback<ArrayList<GWTTrigger>> callback);
    
    void getAllTriggers(AsyncCallback<ArrayList<GWTTrigger>> callback);
    
    void scheduleJob(GWTCronTrigger GWTTrigger, AsyncCallback<Date> callback);
    
    void getCurrentlyExecutingJobs(AsyncCallback<ArrayList<GWTQuartzJobExecutionContext>> callback);
    
    void addJob(GWTJobDetail gwtJobDetail, boolean replace, AsyncCallback<Void> callback);
}
