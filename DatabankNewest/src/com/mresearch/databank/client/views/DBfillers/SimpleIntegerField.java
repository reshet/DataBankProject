package com.mresearch.databank.client.views.DBfillers;

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
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitIntegerDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;

public class SimpleIntegerField extends Composite implements MetaUnitFiller{

	private static SimpleIntegerFieldUiBinder uiBinder = GWT
			.create(SimpleIntegerFieldUiBinder.class);

	interface SimpleIntegerFieldUiBinder extends
			UiBinder<Widget, SimpleIntegerField> {
	}

	public SimpleIntegerField() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label field_name;
	@UiField IntegerBox contents;
    private MetaUnitIntegerDTO dto;
    private JSON_Representation current_json;
    private String def_value;
	public SimpleIntegerField(MetaUnitIntegerDTO dto,JSON_Representation filling,String def_value) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.current_json = filling;
		this.def_value = def_value;
		initFields();
	}
	private void initFields()
	{
		this.field_name.setText(dto.getDesc());
		if(def_value!=null) this.contents.setText(def_value);
	}
	private void rebuildJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("name",new JSONString(dto.getUnique_name()));
		obj.put("description",new JSONString(dto.getDesc()));
		obj.put("type",new JSONString("MetaUnitInteger"));
		obj.put("value", new JSONString(contents.getText()));
		current_json = new JSON_Representation(obj);
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
		return contents.getText();
	}
	@Override
	public MetaUnitDTO getDTO() {
		return dto;
	}
}
