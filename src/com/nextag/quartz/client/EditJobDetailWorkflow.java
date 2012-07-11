package com.nextag.quartz.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.nextag.quartz.client.editors.GWTJobDetailEditor;
import com.nextag.quartz.share.GWTJobDetail;

public class EditJobDetailWorkflow {

	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, EditJobDetailWorkflow> {
	}

	@UiField
	HTMLPanel contents;
	
	@UiField
	DialogBox dialog;
	
	@UiField(provided=true)
	GWTJobDetailEditor editor;
	
	GWTQuartzSchedulerAsync scheduler;
	boolean create;
	
	interface Driver extends SimpleBeanEditorDriver<GWTJobDetail, GWTJobDetailEditor>{};
	Driver driver = GWT.create(Driver.class);
	
	
	public EditJobDetailWorkflow(GWTQuartzSchedulerAsync scheduler, boolean create) {
		this.scheduler = scheduler;
		this.create = create;
		editor = new GWTJobDetailEditor();
		driver.initialize(editor);
		binder.createAndBindUi(this);
	}

	void edit(GWTJobDetail jobDetail){	
		driver.edit(jobDetail);
		dialog.center();
	}
	
	@UiHandler("save")
	void onSave(ClickEvent event){
		GWTJobDetail edited = driver.flush();
		if (driver.hasErrors()){
			//TODO
		} else {
			scheduler.addJob(edited, !create, new GWTQuartzScheduler.nopAsynCallback<Void>());
			dialog.hide(true);
		}
		
	}
	
	 @UiHandler("cancel")
	  void onCancel(ClickEvent event) {
	    dialog.hide(true);
	  }
	
}
