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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.nextag.quartz.client.events.DeleteJobEvent;
import com.nextag.quartz.client.events.EditJobEvent;
import com.nextag.quartz.client.events.EditTriggerEvent;
import com.nextag.quartz.client.events.ToggleTriggerStateEvent;
import com.nextag.quartz.client.events.TriggerJobEvent;
import com.nextag.quartz.client.widgets.JobsTable;
import com.nextag.quartz.client.widgets.SchedulerAdminButtonsWidget;
import com.nextag.quartz.client.widgets.TriggersTable;
import com.nextag.quartz.share.GWTCronTrigger;
import com.nextag.quartz.share.GWTJobDetail;
import com.nextag.quartz.share.GWTTrigger;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class QuartzManager implements EntryPoint {
	final static String ALL_GROUPS = "All Groups";
	
	private static final Logger log = Logger.getLogger(QuartzManager.class.getName());
	
	EventBus eventBus = new SimpleEventBus();
	
	ListDataProvider<GWTJobDetail> jobsProvider = new ListDataProvider<GWTJobDetail>();
	
	private ListBox cbGroupName;
	GWTQuartzSchedulerAsync scheduler = GWT.create(GWTQuartzScheduler.class);
	
	private ListBox cbTriggerGroup;
	ListDataProvider<GWTTrigger> triggersProvider = new ListDataProvider<GWTTrigger>();
	ListDataProvider<GWTQuartzJobExecutionContext> jobExecuationContextsProvider = new ListDataProvider<GWTQuartzJobExecutionContext>();
	private CellTable<GWTQuartzJobExecutionContext> executedJobsTable;
		
		
		protected void updateJobs() {
			
			String selectedGroup = cbGroupName.getItemText(cbGroupName.getSelectedIndex());
			if (ALL_GROUPS.equals(selectedGroup)) {
				scheduler.getAllJobDetails(new AsyncCallback<ArrayList<GWTJobDetail>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ArrayList<GWTJobDetail> result) {
						jobsProvider.setList(result);
					}
				});
			} else {
				scheduler.getJobDetails(selectedGroup, new AsyncCallback<ArrayList<GWTJobDetail>>() {

					@Override
					public void onFailure(Throwable caught) {
						
					}

					@Override
					public void onSuccess(ArrayList<GWTJobDetail> result) {
						jobsProvider.setList(result);
					}
				});
			}
			
		}
		
	
		protected void updateTriggers(){
			String seletedGroup = cbTriggerGroup.getItemText(cbTriggerGroup.getSelectedIndex());
			if (ALL_GROUPS.equals(seletedGroup)){
				scheduler.getAllTriggers(new AsyncCallback<ArrayList<GWTTrigger>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ArrayList<GWTTrigger> result) {
						triggersProvider.setList(result);		
					}
				});
			} else {
				scheduler.getTriggersByGroup(seletedGroup, new AsyncCallback<ArrayList<GWTTrigger>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ArrayList<GWTTrigger> result) {
						triggersProvider.setList(result);
					}
				});
			}
		}
	
		protected void updateJobExecutionContexts(){
			scheduler.getCurrentlyExecutingJobs(new AsyncCallback<ArrayList<GWTQuartzJobExecutionContext>>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(
						ArrayList<GWTQuartzJobExecutionContext> result) {
					jobExecuationContextsProvider.getList().addAll(result);
				}
			});
		}
		
	public void onModuleLoad() {
		//catch exception
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
		      public void onUncaughtException(Throwable e) {
		        log.log(Level.SEVERE, e.getMessage(), e);
		      }
		 });
		Logger.getLogger("").addHandler(new ErrorDialog().getHandler());
		
		eventBus.addHandler(TriggerJobEvent.TYPE, new TriggerJobEvent.Handler() {			
			@Override
			public void fireTrigger(GWTJobDetail jobDetail) {
				scheduler.triggerJob(jobDetail.getKey(), new GWTQuartzScheduler.nopAsynCallback<Void>());
			}
		});
		
		eventBus.addHandler(DeleteJobEvent.TYPE, new DeleteJobEvent.Handler() {
			
			@Override
			public void deleteJob(GWTJobDetail jobDetail) {
				scheduler.deleteJob(jobDetail.getKey(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Boolean result) {
						updateJobs();
					}
				});
			}
		});
		
		eventBus.addHandler(EditTriggerEvent.TYPE, new EditTriggerEvent.Handler() {
			
			@Override
			public void startEdit(GWTTrigger trigger) {
				//TODO in the future, there may be some subclasses of GWTTrigger other than WGTCronTrigger.
				//do instanceof check
				EditCronTriggerWorkflow newTriggerWorkflow = new EditCronTriggerWorkflow(scheduler);
				newTriggerWorkflow.edit((GWTCronTrigger)trigger);
			}
		});
		
		eventBus.addHandler(ToggleTriggerStateEvent.TYPE, new ToggleTriggerStateEvent.Handler() {
			
			@Override
			public void resume(GWTTrigger trigger) {
				scheduler.resumeTrigger(trigger.getKey(), new GWTQuartzScheduler.nopAsynCallback<Void>());
			}
			
			@Override
			public void pause(GWTTrigger trigger) {
				scheduler.pauseTrigger(trigger.getKey(), new GWTQuartzScheduler.nopAsynCallback<Void>());
			}
		});
		
		eventBus.addHandler(EditJobEvent.TYPE, new EditJobEvent.Handler() {
			
			@Override
			public void editJob(GWTJobDetail jobDetail, boolean create) {
				new EditJobDetailWorkflow(scheduler, create).edit(jobDetail);
			}
		});
		
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");

		DockPanel dockPanel = new DockPanel();
		rootPanel.add(dockPanel);
		dockPanel.setSize("", "100%");
		
		SimplePanel simplePanel = new SimplePanel();
		simplePanel.add(new SchedulerAdminButtonsWidget(scheduler));
		dockPanel.add(simplePanel, DockPanel.NORTH);
		
				SimplePanel simplePanel_1 = new SimplePanel();
				dockPanel.add(simplePanel_1, DockPanel.EAST);
				dockPanel.setCellWidth(simplePanel_1, "20%");
				simplePanel_1.setWidth("");
				
						executedJobsTable = new CellTable<GWTQuartzJobExecutionContext>();
						simplePanel_1.setWidget(executedJobsTable);
						executedJobsTable.setSize("100%", "100%");
						executedJobsTable.setTableLayoutFixed(false);
						
						
								TextColumn<GWTQuartzJobExecutionContext> ejJobName = new TextColumn<GWTQuartzJobExecutionContext>() {
									@Override
									public String getValue(GWTQuartzJobExecutionContext object) {
										return object.getJobKey().getName();
									}
								};
								executedJobsTable.addColumn(ejJobName, "job name");
								
										TextColumn<GWTQuartzJobExecutionContext> ejTriggerName = new TextColumn<GWTQuartzJobExecutionContext>() {
											@Override
											public String getValue(GWTQuartzJobExecutionContext object) {
												return object.getTriggerKey().getName();
											}
										};
										executedJobsTable.addColumn(ejTriggerName, "trigger name");
										
												Column<GWTQuartzJobExecutionContext, Number> ejRunTime = new Column<GWTQuartzJobExecutionContext, Number>(new NumberCell()) {
													@Override
													public Number getValue(GWTQuartzJobExecutionContext object) {
														return new Long(object.getJobRunTime());
													}
												};
												executedJobsTable.addColumn(ejRunTime, "run time");
												jobExecuationContextsProvider.addDataDisplay(executedJobsTable);

		
		final TabPanel tabPanel = new TabPanel();
		dockPanel.add(tabPanel, DockPanel.CENTER);
		dockPanel.setCellHeight(tabPanel, "100%");
		tabPanel.setSize("100%", "100%");
		VerticalPanel verticalPanel = new VerticalPanel();
		tabPanel.add(verticalPanel, "Jobs", false);
		verticalPanel.setSize("100%", "100%");
		tabPanel.selectTab(0);
						
								HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
								verticalPanel.add(horizontalPanel_1);
								verticalPanel.setCellHeight(horizontalPanel_1, "22");
								verticalPanel.setCellWidth(horizontalPanel_1, "100%");
								
										Label lblNewLabel = new Label("Job Group");
										horizontalPanel_1.add(lblNewLabel);
										lblNewLabel.setWidth("67px");
										
												cbGroupName = new ListBox();
												horizontalPanel_1.add(cbGroupName);
												
														cbGroupName.addItem(ALL_GROUPS);
														cbGroupName.addChangeHandler(new ChangeHandler() {

															@Override
															public void onChange(ChangeEvent event) {
																updateJobs();
															}
														});
				
						final SimplePanel jobPanel = new SimplePanel();
						verticalPanel.add(jobPanel);
						verticalPanel.setCellHeight(jobPanel, "100%");
						verticalPanel.setCellWidth(jobPanel, "100%");
						jobPanel.setSize("100%", "");
						jobPanel.setStyleName("boxed");
						final JobsTable jobsTable = new JobsTable(jobsProvider, eventBus);
						jobPanel.add(jobsTable);
						jobsTable.setSize("100%", "397px");
					



		VerticalPanel verticalPanel_1 = new VerticalPanel();
		tabPanel.add(verticalPanel_1, "triggers", false);
		verticalPanel_1.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel_2);
		verticalPanel_1.setCellWidth(horizontalPanel_2, "100%");
		verticalPanel_1.setCellHeight(horizontalPanel_2, "22");

		Label lblGroup = new Label("Trigger Group");
		horizontalPanel_2.add(lblGroup);

		cbTriggerGroup = new ListBox();
		cbTriggerGroup.addItem(ALL_GROUPS);
		cbTriggerGroup.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				updateTriggers();
			}
		});

		horizontalPanel_2.add(cbTriggerGroup);

		Button btnPauseAll = new Button("Pause All");
		btnPauseAll.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				scheduler.pauseAll(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});
		horizontalPanel_2.add(btnPauseAll);
		horizontalPanel_2.setCellHorizontalAlignment(btnPauseAll, HasHorizontalAlignment.ALIGN_RIGHT);

		Button btnResumeAll = new Button("Resume All");
		btnResumeAll.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				scheduler.resumeAll(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});
		horizontalPanel_2.add(btnResumeAll);


		Button btnPauseGroup = new Button("Pause Group");
		btnPauseGroup.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String selectedGroup = cbTriggerGroup.getItemText(cbTriggerGroup.getSelectedIndex());
				scheduler.pauseTriggers(selectedGroup, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});
		horizontalPanel_2.add(btnPauseGroup);

		Button btnResumeGroup = new Button("Resume Group");
		btnResumeGroup.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String selectedGroup = cbTriggerGroup.getItemText(cbTriggerGroup.getSelectedIndex());
				scheduler.resumeTriggers(selectedGroup, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});
		horizontalPanel_2.add(btnResumeGroup);

		SimplePanel triggerPanel = new SimplePanel();
		verticalPanel_1.add(triggerPanel);
		triggerPanel.setSize("100%", "390px");
		TriggersTable triggersTable = new TriggersTable(eventBus, triggersProvider);
		triggerPanel.add(triggersTable);
		triggersTable.setSize("100%", "100%");


		scheduler.getJobGroupNames(new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onSuccess(ArrayList<String> result) {
				for (String name : result){
					cbGroupName.addItem(name);
				}	
				updateJobs();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
				
		//polling jobs, triggers and job executions status
		new com.google.gwt.user.client.Timer(){
			@Override
			public void run() {
				
				updateJobs();

				updateTriggers();

				updateJobExecutionContexts();
				this.schedule(2000);
			}
		}.schedule(2000);
		
		scheduler.getTriggerGroupNames(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<String> result) {
				for (String group : result){
					cbTriggerGroup.addItem(group);
				}
				updateTriggers();
			}
		});
		
		//update trigger group combo box
		
		
				
				
	}
}
