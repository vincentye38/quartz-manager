package org.quartz.GWTQuartzManager.client;

import java.util.Date;

import org.quartz.GWTQuartzManager.client.editors.GWTCronTriggerEditor;
import org.quartz.GWTQuartzManager.share.GWTCronTrigger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;

public class EditCronTriggerWorkflow {
	interface Binder extends UiBinder<DialogBox, EditCronTriggerWorkflow> {
	    Binder BINDER = GWT.create(Binder.class);
	}
	
	
	@UiField
	HTMLPanel contents;
	
	@UiField
	DialogBox dialog;
	
	@UiField(provided=true)
	GWTCronTriggerEditor editor;
	
	GWTQuartzSchedulerAsync scheduler;
	
	interface Driver extends SimpleBeanEditorDriver<GWTCronTrigger, GWTCronTriggerEditor>{};
	Driver driver = GWT.create(Driver.class);
	
	public EditCronTriggerWorkflow(GWTQuartzSchedulerAsync scheduler){
		this.scheduler = scheduler;
		editor = new GWTCronTriggerEditor();
		driver.initialize(editor);
		Binder.BINDER.createAndBindUi(this);
	}
	
	void edit(GWTCronTrigger trigger){	
		driver.edit(trigger);
		dialog.center();
	}
	
	@UiHandler("save")
	void onSave(ClickEvent event){
		GWTCronTrigger edited = driver.flush();
		if (driver.hasErrors()){
			//TODO
		} else {
			scheduler.scheduleJob(edited, new AsyncCallback<Date>() {
				
				@Override
				public void onSuccess(Date result) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
			dialog.hide(true);
		}
		
	}
	
	 @UiHandler("cancel")
	  void onCancel(ClickEvent event) {
	    dialog.hide();
	  }
	

	
}
