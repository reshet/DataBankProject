package com.mresearch.databank.client.views.DBviewers;

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
import com.google.gwt.user.client.ui.HTML;
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

public class SimpleFieldNameViewer extends Composite{

	private static SimpleIntegerFieldUiBinder uiBinder = GWT
			.create(SimpleIntegerFieldUiBinder.class);

	interface SimpleIntegerFieldUiBinder extends
			UiBinder<Widget, SimpleFieldNameViewer> {
	}

	public SimpleFieldNameViewer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiField HTML field_name;
	//@UiField Label contents;
    private MetaUnitDTO dto;
    private JSON_Representation current_json;
    private String def_value;
	public SimpleFieldNameViewer(MetaUnitDTO dto,JSON_Representation filling,String def_value,int left_margin) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.current_json = filling;
		this.def_value = def_value;
		
		initFields(left_margin);
	}
	private void initFields(int left)
	{

		field_name.setHTML("<span style=\"padding-left:"+String.valueOf(left)+"px\">"+dto.getDesc()+"</span>");
		//this.field_name.s;
		
		
		//if(def_value!=null) this.contents.setText(def_value);
	}
}
