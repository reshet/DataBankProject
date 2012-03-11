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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;

import org.omg.CORBA.DATA_CONVERSION;
import org.zenika.widget.client.datePicker.DatePicker;

public class SimpleDateField extends Composite implements MetaUnitFiller{

	private static SimpleDateFieldUiBinder uiBinder = GWT
			.create(SimpleDateFieldUiBinder.class);

	interface SimpleDateFieldUiBinder extends
			UiBinder<Widget, SimpleDateField> {
	}

	public SimpleDateField() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label field_name;
	@UiField DatePicker date_picker;
    private MetaUnitDateDTO dto;
    private JSON_Representation current_json;
    private String def_val;
	public SimpleDateField(MetaUnitDateDTO dto,JSON_Representation filling,String def_val) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.current_json = filling;
		this.def_val = def_val;
		initFields();
	}
	private void initFields()
	{
		this.field_name.setText(dto.getDesc());
		if(def_val!=null)date_picker.setText(def_val);
	}
	private void rebuildJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("name",new JSONString(dto.getUnique_name()));
		obj.put("description",new JSONString(dto.getDesc()));
		obj.put("type",new JSONString("MetaUnitDate"));
		obj.put("value", new JSONString(date_picker.getValue()));
		current_json = new JSON_Representation(obj);
	}
	@Override
	public MetaUnitDTO getDTO() {
		return dto;
	}
//	@Override
//	public String getText() {
//		return contents.getText();
//	}
	
	
	
//
//	@Override
//	public void setText(String text) {
//		this.contents.setValue(text);
//	}
	@Override
	public String getUniqueName() {
		return dto.getUnique_name();
	}
	@Override
	public JSON_Representation getJSON() {
		rebuildJSON();
		return current_json;
	}
	@Override
	public String getFilledValue() {
		return date_picker.getValue();
	}

}
