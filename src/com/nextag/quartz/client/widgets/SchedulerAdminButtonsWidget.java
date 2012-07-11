package com.nextag.quartz.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ToggleButton;
import com.nextag.quartz.client.GWTQuartzScheduler;
import com.nextag.quartz.client.GWTQuartzScheduler.nopAsynCallback;
import com.nextag.quartz.client.GWTQuartzSchedulerAsync;
import com.nextag.quartz.share.GWTSchedulerException;

public class SchedulerAdminButtonsWidget extends Composite {

	private static final Binder binder = GWT.create(Binder.class);
	@UiField ToggleButton togglePauseResume;
	@UiField ToggleButton shutdown;

	interface Binder extends UiBinder<Widget, SchedulerAdminButtonsWidget> {
	}

	GWTQuartzSchedulerAsync scheduler;
	
	@UiHandler("togglePauseResume")
	void togglePauseResume(ValueChangeEvent<Boolean> event){
		if (event.getValue()){
			scheduler.standby(new GWTQuartzScheduler.nopAsynCallback<Void>());
		} else {
			scheduler.start(new GWTQuartzScheduler.nopAsynCallback<Void>());
		}
	}
	
	@UiHandler("shutdown")
	void shutdown(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			scheduler.shutdown(new nopAsynCallback<Void>());
			shutdown.setEnabled(false); //once the scheduler is shutdown explicitly, it can't be re-started again.
		}else{
			scheduler.start(new nopAsynCallback<Void>());
		}
		
		new Timer() {
			
			@Override
			public void run() {
				refresh();
			}
		}.scheduleRepeating(2000);
	}
	
	
	public SchedulerAdminButtonsWidget(final GWTQuartzSchedulerAsync scheduler) {
		this.scheduler = scheduler;
		initWidget(binder.createAndBindUi(this));
		refresh();		
	}
	
	public void refresh(){
		scheduler.isShutdown(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Boolean result) {
				togglePauseResume.setEnabled(!result);
				if (result){
					shutdown.setValue(true);
					
				} else {
					scheduler.isInStandbyMode(new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(Boolean result) {
							togglePauseResume.setValue(result);
						}
					});
				}
			}
		});
	}

}
