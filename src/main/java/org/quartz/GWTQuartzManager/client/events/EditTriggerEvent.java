package org.quartz.GWTQuartzManager.client.events;

import org.quartz.GWTQuartzManager.share.GWTTrigger;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

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
