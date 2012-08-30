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
package org.quartz.GWTQuartzManager.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.GWTQuartzManager.client.GWTQuartzJobExecutionContext;
import org.quartz.GWTQuartzManager.client.GWTQuartzScheduler;
import org.quartz.GWTQuartzManager.share.GWTCronTrigger;
import org.quartz.GWTQuartzManager.share.GWTJobDetail;
import org.quartz.GWTQuartzManager.share.GWTKey;
import org.quartz.GWTQuartzManager.share.GWTSchedulerException;
import org.quartz.GWTQuartzManager.share.GWTTrigger;
import org.quartz.GWTQuartzManager.share.Pair;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.spi.MutableTrigger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service("GWTQuartzScheduler")
public class GWTQuartzSchedulerImpl implements GWTQuartzScheduler {
	
	Scheduler delegate;
	public Scheduler getDelegate() {
		return delegate;
	}

	public void setDelegate(Scheduler delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getSchedulerName() throws GWTSchedulerException {
		try {
			return delegate.getSchedulerName();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public String getSchedulerInstanceId() throws GWTSchedulerException {
		try {
			return delegate.getSchedulerInstanceId();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void start() throws GWTSchedulerException {
		try {
			delegate.start();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void startDelayed(int seconds) throws GWTSchedulerException {
		try {
			delegate.startDelayed(seconds);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public boolean isStarted() throws GWTSchedulerException {
		try {
			return delegate.isStarted();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void standby() throws GWTSchedulerException {
		try {
			delegate.standby();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public boolean isInStandbyMode() throws GWTSchedulerException {
		try {
			return delegate.isInStandbyMode();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void shutdown() throws GWTSchedulerException {
		try {
			delegate.shutdown();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void shutdown(boolean waitForJobsToComplete)
			throws GWTSchedulerException {
		try {
			delegate.shutdown();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public boolean isShutdown() throws GWTSchedulerException {
		try {
			return delegate.isShutdown();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}


	@Override
	public boolean unscheduleJob(GWTKey GWTtriggerKey)
			throws GWTSchedulerException {
		TriggerKey triggerKey = new TriggerKey(GWTtriggerKey.getName(), GWTtriggerKey.getGroup());
		try {
			return delegate.unscheduleJob(triggerKey);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public boolean unscheduleJobs(List<GWTKey> GWTKeys)
			throws GWTSchedulerException {
		ArrayList<TriggerKey> keys = new ArrayList<TriggerKey>();
		for (GWTKey gwtKey: GWTKeys){
			keys.add(new TriggerKey(gwtKey.getName(), gwtKey.getGroup()));
		}
		try {
			return delegate.unscheduleJobs(keys);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public boolean deleteJob(GWTKey jobKey) throws GWTSchedulerException {
		JobKey quartzJobKey = new JobKey(jobKey.getName(), jobKey.getGroup());
		try {
			return delegate.deleteJob(quartzJobKey);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public boolean deleteJobs(List<GWTKey> jobKeys)
			throws GWTSchedulerException {
		ArrayList<JobKey> keys = new ArrayList<JobKey>();
		for (GWTKey gwtKey: jobKeys){
			keys.add(new JobKey(gwtKey.getName(), gwtKey.getGroup()));
		}
		try {
			return delegate.deleteJobs(keys);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void triggerJob(GWTKey jobKey) throws GWTSchedulerException {
		JobKey key = new JobKey(jobKey.getName(), jobKey.getGroup());
		try {
			delegate.triggerJob(key);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void pauseJob(GWTKey jobKey) throws GWTSchedulerException {
		JobKey key = new JobKey(jobKey.getName(), jobKey.getGroup());
		try {
			delegate.pauseJob(key);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void pauseTrigger(GWTKey triggerKey) throws GWTSchedulerException {
		TriggerKey key = new TriggerKey(triggerKey.getName(),triggerKey.getGroup());
		try {
			delegate.pauseTrigger(key);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void resumeJob(GWTKey jobKey) throws GWTSchedulerException {
		JobKey key = new JobKey(jobKey.getName(), jobKey.getGroup());
		try {
			delegate.resumeJob(key);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void resumeTrigger(GWTKey triggerKey) throws GWTSchedulerException {
		TriggerKey key = new TriggerKey(triggerKey.getName(),triggerKey.getGroup());
		try {
			delegate.resumeTrigger(key);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	public void resumeTriggers(String groupName) throws GWTSchedulerException{
		try {
			delegate.resumeTriggers(GroupMatcher.triggerGroupEquals(groupName));
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}
	
	@Override
	public void pauseAll() throws GWTSchedulerException {
		try {
			delegate.pauseAll();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public void resumeAll() throws GWTSchedulerException {
		try {
			delegate.resumeAll();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public ArrayList<String> getJobGroupNames() throws GWTSchedulerException {
		try {
			return new ArrayList<String>(delegate.getJobGroupNames());
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	
	@Override
	public List<? extends GWTTrigger> getTriggersOfJob(GWTKey jobKey)
			throws GWTSchedulerException {
		List<? extends Trigger> triggers;
		JobKey key = new JobKey(jobKey.getName(), jobKey.getGroup());
		try {
			triggers = delegate.getTriggersOfJob(key);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
		List<GWTTrigger> result = new ArrayList<GWTTrigger>();
		for (Trigger trigger : triggers){
			result.add(convertToGWTTrigger(trigger));
		}
		return result;
	}

	@Override
	public List<String> getTriggerGroupNames() throws GWTSchedulerException {
		try {
			return delegate.getTriggerGroupNames();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public Set<String> getPausedTriggerGroups() throws GWTSchedulerException {
		try {
			return delegate.getPausedTriggerGroups();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public GWTJobDetail getJobDetail(GWTKey jobKey)
			throws GWTSchedulerException {
		JobDetail jobDetail;
		JobKey key = new JobKey(jobKey.getName(), jobKey.getGroup());
		try {
			jobDetail = delegate.getJobDetail(key);
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
		if (jobDetail == null) return null;
		else {
			GWTJobDetail result = new GWTJobDetail();
			BeanUtils.copyProperties(jobDetail, result, new String[]{"key","jobDataMap"});
			result.setKey(new GWTKey(jobDetail.getKey().getName(), jobDetail.getKey().getGroup()));
			for (Entry<String, Object> jobDataEntry : jobDetail.getJobDataMap().entrySet()){
				result.getJobDataMapEntryList().add(new Pair(jobDataEntry.getKey(), jobDataEntry.getValue().toString()));
			}
			return result;
		}
	}

	@Override
	public GWTTrigger getTrigger(GWTKey triggerKey)
			throws GWTSchedulerException {
		if (triggerKey == null) throw new GWTSchedulerException("paramter triggerKey can't be null");
		try{
		TriggerKey key = new TriggerKey(triggerKey.getName(), triggerKey.getGroup());
		Trigger trigger = delegate.getTrigger(key);
		if (trigger != null){
			GWTTrigger gwtTrigger = convertToGWTTrigger(trigger);
			return gwtTrigger;
		}
		} catch(SchedulerException ex){
			throw new GWTSchedulerException(ex.getMessage());
		}
		return null;
	}

	private GWTTrigger convertToGWTTrigger(Trigger trigger) {
		GWTTrigger gwtTrigger ;
		if (trigger instanceof CronTrigger){
			gwtTrigger = new GWTCronTrigger();
		} else {
			gwtTrigger = new GWTTrigger();
		}
		
		BeanUtils.copyProperties(trigger, gwtTrigger, new String[]{"key", "jobKey"});
		gwtTrigger.setKey(new GWTKey(trigger.getKey().getName(), trigger.getKey().getGroup()));
		gwtTrigger.setJobKey(new GWTKey(trigger.getJobKey().getName(), trigger.getJobKey().getGroup()));
		String state = GWTTrigger.TriggerState.NONE.name();
		try {
			state = delegate.getTriggerState(trigger.getKey()).name();
		} catch (SchedulerException e) {};
		
		for (Entry<String, Object> jobDataEntry : trigger.getJobDataMap().entrySet()){
			gwtTrigger.getJobDataMapEntryList().add(new Pair(jobDataEntry.getKey(), jobDataEntry.getValue().toString()));
		}		
		gwtTrigger.setState(state);
		return gwtTrigger;
	}

	
	@Override
	public String getTriggerState(GWTKey triggerKey)
			throws GWTSchedulerException {
		if (triggerKey == null) throw new GWTSchedulerException("parameter triggerKey can't be null.");
		try {
			TriggerState state = delegate.getTriggerState(new TriggerKey(triggerKey.getName(),triggerKey.getGroup()));
			return state.name();
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public boolean interrupt(GWTKey jobKey) throws GWTSchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean interrupt(String fireInstanceId)
			throws GWTSchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() throws GWTSchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<GWTKey> getJobKeys(String groupName) throws GWTSchedulerException {
		Set<JobKey> jobKeys;
		try {
			jobKeys = delegate.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
		Set<GWTKey> result = new TreeSet<GWTKey>();
		if (jobKeys != null){
			for (JobKey jobKey : jobKeys){
				result.add(new GWTKey(jobKey.getName(), jobKey.getGroup()));
			}
		}
		return result;
	}

	@Override
	public ArrayList<GWTJobDetail> getJobDetails(String groupName) throws GWTSchedulerException{
		ArrayList<GWTJobDetail> result = new ArrayList<GWTJobDetail>();

		Set<GWTKey> jobKeys = getJobKeys(groupName);
		for (GWTKey jobKey : jobKeys){
			GWTJobDetail jobDetail = getJobDetail(jobKey);
			if (jobDetail != null) result.add(jobDetail);
		}

		return result;
	}

	@Override
	public ArrayList<GWTJobDetail> getAllJobDetails() throws GWTSchedulerException {
		ArrayList<GWTJobDetail> result = new ArrayList<GWTJobDetail>();
		for (String groupName : getJobGroupNames()){
			result.addAll(getJobDetails(groupName));
		}
		return result;
	}

	@Override
	public ArrayList<GWTTrigger> getTriggersByGroup(String groupName)
			throws GWTSchedulerException {
		ArrayList<GWTTrigger> result = new ArrayList<GWTTrigger>();
		try {
			Set<TriggerKey> triggerKeys = delegate.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName));
			for (TriggerKey triggerKey : triggerKeys){
				try{
					GWTTrigger trigger = getTrigger(new GWTKey(triggerKey.getName(),triggerKey.getGroup()));
					if (trigger != null){
						result.add(trigger);
					}
				} catch(GWTSchedulerException ex){}
				
			}
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
		return result;
	}
	
	@Override
	public ArrayList<GWTTrigger> getAllTriggers() throws GWTSchedulerException {
		ArrayList<GWTTrigger> result = new ArrayList<GWTTrigger>();
		for (String groupName : getTriggerGroupNames()){
			result.addAll(getTriggersByGroup(groupName));
		}
		return result;
	}

	@Override
	public Date scheduleJob(GWTCronTrigger GWTTrigger) throws GWTSchedulerException {
		if (GWTTrigger.getKey() == null) throw new GWTSchedulerException("Key can't be null in Trigger");
		TriggerKey triggerKey = new TriggerKey(GWTTrigger.getKey().getName(), GWTTrigger.getKey().getGroup());
		
		if (GWTTrigger.getJobKey() == null) throw new GWTSchedulerException("JobKey can't be null in Trigger");
		JobKey jobKey = new JobKey(GWTTrigger.getJobKey().getName(), GWTTrigger.getJobKey().getGroup());
		//convert GWTTrigger to Trigger
		MutableTrigger trigger = new CronTriggerImpl();
		BeanUtils.copyProperties(GWTTrigger, trigger, new String[]{"key", "jobKey"});
		trigger.setKey(triggerKey);
		trigger.setJobKey(jobKey);
		if (GWTTrigger.getJobDataMapEntryList() != null){
			JobDataMap jobDataMap = new JobDataMap();
			for (Pair<String, String> entry : GWTTrigger.getJobDataMapEntryList()){
				jobDataMap.put(entry.getKey(), entry.getValue());
			}
			trigger.setJobDataMap(jobDataMap);
		}
		
		Date result;
		try {
			if (delegate.checkExists(triggerKey)){
				result = delegate.rescheduleJob(triggerKey, trigger);
			} else {
				result = delegate.scheduleJob(trigger);
			}
		} catch (SchedulerException ex){
			throw new GWTSchedulerException(ex.getMessage());
		}

		return result;
	}

	@Override
	public void pauseTriggers(String groupName) throws GWTSchedulerException {
		try {
			delegate.pauseTriggers(GroupMatcher.triggerGroupEquals(groupName));
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
	}

	@Override
	public ArrayList<GWTQuartzJobExecutionContext> getCurrentlyExecutingJobs()
			throws GWTSchedulerException {
		try {
			List<JobExecutionContext> jobContexts = delegate.getCurrentlyExecutingJobs();
			ArrayList<GWTQuartzJobExecutionContext> gwtJobContexts = new ArrayList<GWTQuartzJobExecutionContext>();
			for (JobExecutionContext context : jobContexts){
				GWTQuartzJobExecutionContext gwtContext = new GWTQuartzJobExecutionContext();
				gwtContext.setJobKey(new GWTKey(context.getJobDetail().getKey().getName(), context.getJobDetail().getKey().getGroup()));
				gwtContext.setTriggerKey(new GWTKey(context.getTrigger().getKey().getName(), context.getTrigger().getKey().getGroup()));
				gwtContext.setFireInstanceId(context.getFireInstanceId());
				gwtContext.setFireTime(context.getFireTime());
				gwtContext.setJobRunTime(context.getJobRunTime());
				gwtContext.setNextFireTime(context.getNextFireTime());
				gwtContext.setPreviousFireTime(context.getPreviousFireTime());
				gwtContext.setScheduledFireTime(context.getScheduledFireTime());
				gwtJobContexts.add(gwtContext);
			}
			return gwtJobContexts;
		} catch (SchedulerException e) {
			throw new GWTSchedulerException(e.getMessage());
		}
		
	}

	@Override
	public void addJob(GWTJobDetail gwtJobDetail, boolean replace) throws GWTSchedulerException {
		try {
			Class jobClass = Class.forName(gwtJobDetail.getJobClassName());
			if (!Job.class.isAssignableFrom(jobClass)) throw new GWTSchedulerException(gwtJobDetail.getJobClassName() + "is not subclass of Job");
			JobBuilder jobBuilder = JobBuilder.newJob((Class<Job>)jobClass);
			jobBuilder.withIdentity(gwtJobDetail.getKey().getName(), gwtJobDetail.getKey().getGroup());
			jobBuilder.withDescription(gwtJobDetail.getDescription());
			jobBuilder.requestRecovery(gwtJobDetail.isRequestsRecovery());
			jobBuilder.storeDurably(gwtJobDetail.isDurable());
			for (Pair<String, String> jobDataEntry : gwtJobDetail.getJobDataMapEntryList()){
				jobBuilder.usingJobData(jobDataEntry.getKey(), jobDataEntry.getValue());
			}
			delegate.addJob(jobBuilder.build(),replace);
		} catch (Exception e) {
			throw new GWTSchedulerException(e.getMessage());
		} 
	}
	
}
