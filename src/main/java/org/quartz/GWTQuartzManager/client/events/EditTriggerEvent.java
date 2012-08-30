package com.nextag.quartz.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.nextag.quartz.share.GWTTrigger;

public class EditTriggerEvent extends GwtEvent<EditTriggerEvent.Handler> {
	public static final Type<Handler> TYPE = new Type<Handler>();
	
	public interface Handler extends EventHandler{
		void startEdit(GWTTrigger trigger);
	}

	GWTTrigger trigger;
	public EditTriggerEvent(GWTTrigger trigger){
		this.trigger = trigger;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.startEdit(trigger);
	}
}
