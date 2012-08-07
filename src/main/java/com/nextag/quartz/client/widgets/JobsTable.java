package com.nextag.quartz.client.widgets;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.nextag.quartz.client.EditJobDetailWorkflow;
import com.nextag.quartz.client.events.DeleteJobEvent;
import com.nextag.quartz.client.events.EditJobEvent;
import com.nextag.quartz.client.events.EditTriggerEvent;
import com.nextag.quartz.client.events.TriggerJobEvent;
import com.nextag.quartz.share.GWTCronTrigger;
import com.nextag.quartz.share.GWTJobDetail;
import com.nextag.quartz.share.GWTKey;
import com.nextag.quartz.share.Pair;

public class JobsTable extends Composite {

	@UiField(provided=true) 
	DataGrid<GWTJobDetail> table = new DataGrid<GWTJobDetail>();

	interface JobsTableBinder extends UiBinder<Widget, JobsTable> {
	}

	final EventBus eventBus;
	
	public JobsTable(ListDataProvider<GWTJobDetail> jobsProvider, final EventBus eventBus) {
		this.eventBus = eventBus;
	
		
				
		TextColumn<GWTJobDetail> tcName = new TextColumn<GWTJobDetail>() {
			@Override
			public String getValue(GWTJobDetail object) {
				return object.getKey().getName();
			}
		};
		table.addColumn(tcName, "Name");
		
		TextColumn<GWTJobDetail> tcGroup = new TextColumn<GWTJobDetail>() {
			@Override
			public String getValue(GWTJobDetail object) {
				return object.getKey().getGroup();
			}
		};
		table.addColumn(tcGroup, "Group");
		
		jobsProvider.addDataDisplay(table);
		
		TextColumn<GWTJobDetail> tcDescription = new TextColumn<GWTJobDetail>() {
			@Override
			public String getValue(GWTJobDetail object) {
				return object.getDescription();
			}
		};
		table.addColumn(tcDescription, "description");
		
		TextColumn<GWTJobDetail> tcJobClass = new TextColumn<GWTJobDetail>() {
			@Override
			public String getValue(GWTJobDetail object) {
				return object.getJobClassName();
			}
		};
		table.addColumn(tcJobClass, "jobClass name");
		
		TextColumn<GWTJobDetail> tcJobDataMap = new TextColumn<GWTJobDetail>() {
			@Override
			public String getValue(GWTJobDetail object) {
				return object.getJobDataMapEntryList().toString();
			}
		};
		table.addColumn(tcJobDataMap, "jobDataMap");
		
		Column<GWTJobDetail, String> cTrigger = new Column<GWTJobDetail, String>(new ButtonCell()) {
			@Override
			public String getValue(GWTJobDetail object) {
				
				return null;
			}
			
		};
		
		cTrigger.setFieldUpdater(new FieldUpdater<GWTJobDetail, String>() {
			
			@Override
			public void update(int index, GWTJobDetail object, String value) {
				eventBus.fireEvent(new TriggerJobEvent(object));
			}
		});
		
		table.addColumn(cTrigger, "trigger");
		
		Column<GWTJobDetail, String> cDelete = new Column<GWTJobDetail, String>(new ButtonCell()) {
			@Override
			public String getValue(GWTJobDetail object) {
				return null;
			}
		};
		cDelete.setFieldUpdater(new FieldUpdater<GWTJobDetail, String>() {

			@Override
			public void update(int index, GWTJobDetail object, String value) {
				eventBus.fireEvent(new DeleteJobEvent(object));
			}
		});
		table.addColumn(cDelete, "delete");
		
		Column<GWTJobDetail, String> newTrigger = new Column<GWTJobDetail, String>(new ButtonCell()) {
			@Override
			public String getValue(GWTJobDetail object) {
				return (String) null;
			}
		};
		newTrigger.setFieldUpdater(new FieldUpdater<GWTJobDetail, String>() {

			@Override
			public void update(int index, GWTJobDetail object, String value) {
				GWTCronTrigger trigger = new GWTCronTrigger();
				trigger.setKey(new GWTKey(object.getKey().getName(), object.getKey().getGroup()));
				trigger.setJobKey(new GWTKey(object.getKey().getName(), object.getKey().getGroup()));
				trigger.setStartTime(new Date());
				trigger.setJobDataMapEntryList((ArrayList<Pair<String,String>>)object.getJobDataMapEntryList().clone());
				eventBus.fireEvent(new EditTriggerEvent(trigger));
			}
		});
		table.addColumn(newTrigger, "new trigger");
		
		Column<GWTJobDetail, String> newJob = new Column<GWTJobDetail, String>(new ButtonCell()) {

			@Override
			public String getValue(GWTJobDetail object) {
				return null;
			}
		};
		newJob.setFieldUpdater(new FieldUpdater<GWTJobDetail, String>() {
			
			@Override
			public void update(int index, GWTJobDetail object, String value) {
				eventBus.fireEvent(new EditJobEvent(object, false));
			}
		});
		table.addColumn(newJob, "edit");
		
		initWidget(GWT.<JobsTableBinder>create(JobsTableBinder.class).createAndBindUi(this));
	}

}
