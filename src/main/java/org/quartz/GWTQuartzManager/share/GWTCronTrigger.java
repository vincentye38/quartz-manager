package org.quartz.GWTQuartzManager.share;

import javax.validation.constraints.NotNull;

public class GWTCronTrigger extends GWTTrigger {
	
	@NotNull
	String cronExpression;

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
}
