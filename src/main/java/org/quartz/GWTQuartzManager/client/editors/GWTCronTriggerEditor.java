package org.quartz.GWTQuartzManager.client.editors;

import org.quartz.GWTQuartzManager.share.GWTCronTrigger;
import org.quartz.GWTQuartzManager.share.Pair;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.adapters.HasDataEditor;
import com.google.gwt.editor.client.adapters.SimpleEditor;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.editor.ui.client.adapters.ValueBoxEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBox;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class GWTCronTriggerEditor extends Composite implements Editor<GWTCronTrigger> {
	@Path("key.name")
	TextBox nameEditor;
	@Path("key.group")
	TextBox groupEditor;
	@Path("jobKey.name")
	TextBox jobNameEditor;
	@Path("jobKey.group")
	TextBox jobGroupEditor;
	RichTextArea descriptionEditor;
	DateBox startTimeEditor;
	DateBox endTimeEditor;
	TextBox cronExpressionEditor;
	IntegerBox priorityEditor;
	HasDataEditor<Pair<String, String>> jobDataMapEntryList;
	
	
	public GWTCronTriggerEditor() {
		
		VerticalPanel mainPanel = new VerticalPanel();
		
		FlexTable flexTable = new FlexTable();
		mainPanel.add(flexTable);
		flexTable.setSize("127px", "32px");
		
		Label lblName = new Label("Name");
		lblName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.setWidget(0, 0, lblName);
		
		FlexTable flexTable_1 = new FlexTable();
		flexTable.setWidget(0, 1, flexTable_1);
		flexTable_1.setWidth("157px");
		
		nameEditor = new TextBox();
		flexTable_1.setWidget(0, 0, nameEditor);
		
		FlexTable flexTable_2 = new FlexTable();
		flexTable.setWidget(0, 2, flexTable_2);
		
		Label lblGroup = new Label("Group");
		lblGroup.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_2.setWidget(0, 0, lblGroup);
		lblGroup.setWidth("69px");
		
		FlexTable flexTable_3 = new FlexTable();
		flexTable.setWidget(0, 3, flexTable_3);
		
		groupEditor = new TextBox();
		flexTable_3.setWidget(0, 0, groupEditor);
		
		Label lblJobName = new Label("job Name");
		lblJobName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.setWidget(1, 0, lblJobName);
		
		jobNameEditor = new TextBox();
		flexTable.setWidget(1, 1, jobNameEditor);
		
		Label lblJobGroup = new Label("job group");
		lblJobGroup.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.setWidget(1, 2, lblJobGroup);
		lblJobGroup.setWidth("69px");
		
		jobGroupEditor = new TextBox();
		flexTable.setWidget(1, 3, jobGroupEditor);
		
		FlexTable flexTable_4 = new FlexTable();
		flexTable.setWidget(2, 0, flexTable_4);
		
		Label lblNewLabel = new Label("description");
		flexTable_4.setWidget(0, 0, lblNewLabel);
		lblNewLabel.setSize("69px", "15px");
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		mainPanel.setCellHeight(lblNewLabel, "15");
		
		FlexTable flexTable_5 = new FlexTable();
		flexTable.setWidget(2, 1, flexTable_5);
		
		descriptionEditor = new RichTextArea();
		flexTable_5.setWidget(0, 0, descriptionEditor);
		mainPanel.setCellHeight(descriptionEditor, "80px");
		descriptionEditor.setSize("433px", "70px");
		mainPanel.setCellWidth(descriptionEditor, "100%");
		flexTable.getFlexCellFormatter().setColSpan(2, 1, 3);
		
		FlexTable flexTable_6 = new FlexTable();
		flexTable.setWidget(3, 0, flexTable_6);
		
		Label lblPriority = new Label("priority");
		lblPriority.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_6.setWidget(0, 0, lblPriority);
		lblPriority.setSize("69px", "15px");
		
		priorityEditor = new IntegerBox();
		flexTable.setWidget(3, 1, priorityEditor);
		
		FlexTable flexTable_7 = new FlexTable();
		flexTable.setWidget(4, 0, flexTable_7);
		
		Label lblStartDate = new Label("Start Date");
		lblStartDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_7.setWidget(0, 0, lblStartDate);
		lblStartDate.setWidth("69px");
		
		startTimeEditor = new DateBox();
		flexTable.setWidget(4, 1, startTimeEditor);
		
		Label lblEndDate = new Label("End Date");
		lblEndDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.setWidget(4, 2, lblEndDate);
		lblEndDate.setWidth("69px");
		
		endTimeEditor = new DateBox();
		flexTable.setWidget(4, 3, endTimeEditor);
		
		Label lblCronExpression = new Label("Cron expression");
		lblCronExpression.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.setWidget(5, 0, lblCronExpression);
		
		cronExpressionEditor = new TextBox();
		flexTable.setWidget(5, 1, cronExpressionEditor);
		flexTable.getFlexCellFormatter().setColSpan(5, 1, 3);
		
		Label lblJobdata = new Label("jobData");
		lblJobdata.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.setWidget(6, 0, lblJobdata);
		
		CellTable<Pair<String, String>> ctJobDataList = new CellTable<Pair<String, String>>();
		jobDataMapEntryList = HasDataEditor.of(ctJobDataList);
		flexTable.setWidget(6, 1, ctJobDataList);
		
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
		ctJobDataList.addColumn(cKey, "Key");
		
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
		ctJobDataList.addColumn(cValue, "Value");
		
		flexTable.getFlexCellFormatter().setColSpan(6, 1, 3);
		flexTable.getCellFormatter().setVerticalAlignment(6, 0, HasVerticalAlignment.ALIGN_TOP);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		mainPanel.add(horizontalPanel);
		mainPanel.setCellHorizontalAlignment(horizontalPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		 
		initWidget(mainPanel);
	}

	
	
	
}
