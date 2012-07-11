package com.nextag.quartz.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.nextag.quartz.share.GWTTrigger;
import com.nextag.quartz.share.GWTTrigger.TriggerState;

public class ToggleTriggerStateEvent extends GwtEvent<ToggleTriggerStateEvent.Handler>{
	public static final Type<Handler> TYPE = new Type<ToggleTriggerStateEvent.Handler>();
	
	public interface Handler extends EventHandler{
		void pause(GWTTrigger trigger);
		void resume(GWTTrigger trigger);
	}

	GWTTrigger trigger;
	public ToggleTriggerStateEvent(GWTTrigger trigger){
		this.trigger = trigger;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (TriggerState.valueOf(TriggerState.class, trigger.getState()) == TriggerState.PAUSED ){
			handler.resume(trigger);
		} else {
			handler.pause(trigger);
		}
	}
	
	
}
