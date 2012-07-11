package com.nextag.quartz.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.nextag.quartz.share.GWTJobDetail;

public class TriggerJobEvent extends GwtEvent<TriggerJobEvent.Handler>{
	public final static Type<Handler> TYPE = new Type<TriggerJobEvent.Handler>();
	
	public interface Handler extends EventHandler{
		void fireTrigger(GWTJobDetail jobDetail);
	}
	
	GWTJobDetail jobDetail;
	public TriggerJobEvent(GWTJobDetail jobDetail){
		this.jobDetail = jobDetail;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.fireTrigger(jobDetail);
	}
}
