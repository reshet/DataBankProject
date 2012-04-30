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

public class SimpleFieldValueViewer extends Composite{

	private static SimpleIntegerFieldUiBinder uiBinder = GWT
			.create(SimpleIntegerFieldUiBinder.class);

	interface SimpleIntegerFieldUiBinder extends
			UiBinder<Widget, SimpleFieldValueViewer> {
	}

	public SimpleFieldValueViewer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	//@UiField Label field_name;
	@UiField Label contents;
    private String def_value;
	public SimpleFieldValueViewer(String def_value) {
		initWidget(uiBinder.createAndBindUi(this));
		this.def_value = def_value;
		initFields();
	}
	private void initFields()
	{
		//this.field_name.setText(dto.getDesc());
		if(def_value!=null) this.contents.setText(def_value);
	}
}
