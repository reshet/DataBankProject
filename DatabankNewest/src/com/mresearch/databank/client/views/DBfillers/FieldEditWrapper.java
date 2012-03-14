package com.mresearch.databank.client.views.DBfillers;

import javax.xml.crypto.Data;

import com.google.gwt.core.client.GWT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;

import org.omg.CORBA.DATA_CONVERSION;
import org.zenika.widget.client.datePicker.DatePicker;

public class FieldEditWrapper extends Composite {

	private static FieldEditWrapperUiBinder uiBinder = GWT
			.create(FieldEditWrapperUiBinder.class);

	interface FieldEditWrapperUiBinder extends
			UiBinder<Widget, FieldEditWrapper> {
	}

	public FieldEditWrapper() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField HorizontalPanel host;
	private Widget w;
	public Widget getW() {
		return w;
	}
	public void setW(Widget w) {
		this.w = w;
		host.clear();
		host.add(w);
	}

	private FieldEditor editor;
	private int position;
	public FieldEditWrapper(Widget c,FieldEditor editor,int pos) {
		initWidget(uiBinder.createAndBindUi(this));
		this.editor = editor;
		this.position = pos;
		setW(c);
		initFields();
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	private void initFields()
	{
		
	}
	
	
	@UiHandler(value="delete") public void deleteCmd(ClickEvent ev)
	{
		
	}
	@UiHandler(value="up") public void upCmd(ClickEvent ev)
	{
		host.clear();
		editor.doSwap(position, position--);
	}
	@UiHandler(value="down") public void downCmd(ClickEvent ev)
	{
		host.clear();
		editor.doSwap(position, position++);
	}
}
