package com.nextag.quartz.client.widgets;

import java.util.Date;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.nextag.quartz.client.events.EditTriggerEvent;
import com.nextag.quartz.client.events.ToggleTriggerStateEvent;
import com.nextag.quartz.share.GWTTrigger;
import com.nextag.quartz.share.GWTTrigger.TriggerState;

public class TriggersTable extends Composite {

	private static final Binder binder = GWT.create(Binder.class);
	@UiField SimplePager pager;
	@UiField(provided=true) DataGrid<GWTTrigger> table = new DataGrid<GWTTrigger>();

	interface Binder extends UiBinder<Widget, TriggersTable> {
	}

	final EventBus eventBus;
	public TriggersTable(final EventBus eventBus, ListDataProvider<GWTTrigger> dataProvider) {
		this.eventBus = eventBus;
		initWidget(binder.createAndBindUi(this));
		TextColumn<GWTTrigger> tcTriggerName = new TextColumn<GWTTrigger>() {
			@Override
			public String getValue(GWTTrigger object) {
				return object.getKey().getName();
			}
		};
		tcTriggerName.setSortable(true);
		table.addColumn(tcTriggerName, "name");
		
		TextColumn<GWTTrigger> tcTriggerGroup = new TextColumn<GWTTrigger>() {
			@Override
			public String getValue(GWTTrigger object) {
				return object.getKey().getGroup();
			}
		};
		tcTriggerGroup.setSortable(true);
		table.addColumn(tcTriggerGroup, "group");
		
		TextColumn<GWTTrigger> triggerState = new TextColumn<GWTTrigger>() {
			@Override
			public String getValue(GWTTrigger object) {
				return object.getState();
			}
		};
		table.addColumn(triggerState, "state");
		
		Column<GWTTrigger, String> pauseTrigger = new Column<GWTTrigger, String>(new ButtonCell()) {
			@Override
			public String getValue(GWTTrigger object) {
				return (String) null;
			}
		};
		pauseTrigger.setFieldUpdater(new FieldUpdater<GWTTrigger, String>() {

			@Override
			public void update(int index, GWTTrigger object, String value) {
				eventBus.fireEvent(new ToggleTriggerStateEvent(object));
			}
		});
		table.addColumn(pauseTrigger, "pause/resume");
		
		Column<GWTTrigger, Date> cStartTime = new Column<GWTTrigger, Date>(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM))) {
			@Override
			public Date getValue(GWTTrigger object) {
				return object.getStartTime();
			}
		};
		cStartTime.setSortable(true);
		table.addColumn(cStartTime, "start time");
		Column<GWTTrigger, Date> cEndTime = new Column<GWTTrigger, Date>(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM))) {
			@Override
			public Date getValue(GWTTrigger object) {
				return object.getEndTime();
			}
		};
		cEndTime.setSortable(true);
		table.addColumn(cEndTime, "end time");
		
		Column<GWTTrigger, Date> cNextFireTime = new Column<GWTTrigger, Date>(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM))) {
			@Override
			public Date getValue(GWTTrigger object) {
				return object.getNextFireTime();
			}
		};
		cNextFireTime.setSortable(true);
		table.addColumn(cNextFireTime, "next fire time");
		
		dataProvider.addDataDisplay(table);

		Column<GWTTrigger, String> btEdit = new Column<GWTTrigger, String>(new ButtonCell()) {
			
			@Override
			public String getValue(GWTTrigger object) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		btEdit.setFieldUpdater(new FieldUpdater<GWTTrigger, String>() {
			@Override
			public void update(int index, GWTTrigger object, String value) {
				eventBus.fireEvent(new EditTriggerEvent(object));
			}
		});
		table.addColumn(btEdit, "edit");
	}

}
