package com.nextag.quartz.client.editors;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.editor.client.adapters.HasDataEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.nextag.quartz.share.GWTJobDetail;
import com.nextag.quartz.share.Pair;

public class GWTJobDetailEditor extends Composite implements Editor<GWTJobDetail>{
	
	private static final Binder binder = GWT.create(Binder.class);
	@UiField 
	@Path("key.name")
	TextBox nameEditor;
	@UiField 
	@Path("key.group")
	TextBox groupEditor;
	@UiField 
	TextBox descriptionEditor;
	@UiField 
	TextBox jobClassNameEditor;
	@UiField(provided=true) 
	DataGrid<Pair<String, String>> jobDataMapTable = new DataGrid<Pair<String, String>>();
	@UiField 
	CheckBox durableEditor;
	@UiField 
	CheckBox requestsRecoveryEditor;
	HasDataEditor<Pair<String, String>> jobDataMapEntryListEditor = HasDataEditor.of(jobDataMapTable);

	interface Binder extends UiBinder<Widget, GWTJobDetailEditor> {
	}

	public GWTJobDetailEditor() {
		initWidget(binder.createAndBindUi(this));
		Column<Pair<String, String>, String> cKey = new Column<Pair<String, String>, String>(new EditTextCell()) {
			@Override
			public String getValue(Pair<String, String> object) {
				return object.getKey();
			}
		};
		cKey.setFieldUpdater(new FieldUpdater<Pair<String,String>, String>() {

			@Override
			public void update(int index, Pair<String, String> object,
					String value) {
				object.setKey(value);
			}
		});
		jobDataMapTable.addColumn(cKey, "Key");
		
		Column<Pair<String, String>, String> cValue = new Column<Pair<String, String>, String>(new EditTextCell()) {
			@Override
			public String getValue(Pair<String,String> object) {
				return object.getValue();
			}
		};
		cValue.setFieldUpdater(new FieldUpdater<Pair<String,String>, String>() {

			@Override
			public void update(int index, Pair<String, String> object,
					String value) {
				object.setValue(value);
			}
		});
		jobDataMapTable.addColumn(cValue, "Value");

	}
}
