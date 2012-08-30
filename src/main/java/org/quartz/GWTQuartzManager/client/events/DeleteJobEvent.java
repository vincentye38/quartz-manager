package com.nextag.quartz.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.nextag.quartz.share.GWTJobDetail;

public class DeleteJobEvent extends GwtEvent<DeleteJobEvent.Handler>{
	public final static Type<Handler> TYPE = new Type<DeleteJobEvent.Handler>();
	
	public interface Handler extends EventHandler{
		void deleteJob(GWTJobDetail jobDetail);
	}

	GWTJobDetail jobDetail;
	public DeleteJobEvent(GWTJobDetail jobDetail){
		this.jobDetail = jobDetail;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.deleteJob(jobDetail);
	}
}
