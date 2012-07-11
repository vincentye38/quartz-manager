package com.nextag.quartz.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.nextag.quartz.share.GWTJobDetail;

public class EditJobEvent extends GwtEvent<EditJobEvent.Handler>{
	public final static Type<Handler> TYPE = new Type<EditJobEvent.Handler>();
	
	public interface Handler extends EventHandler{
		void editJob(GWTJobDetail jobDetail, boolean create);
	}

	GWTJobDetail gwtJobDetail;
	boolean create;
	public EditJobEvent(GWTJobDetail jobDetail, boolean create){
		this.gwtJobDetail = jobDetail;
		this.create = create;
	}
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.editJob(gwtJobDetail, create);
	}
}
