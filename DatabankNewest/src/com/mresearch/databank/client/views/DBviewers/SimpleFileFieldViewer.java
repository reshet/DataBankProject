package com.mresearch.databank.client.views.DBviewers;

import java.util.ArrayList;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitFileDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;

public class SimpleFileFieldViewer extends Composite{

	private static SimpleStringFieldUiBinder uiBinder = GWT
			.create(SimpleStringFieldUiBinder.class);

	interface SimpleStringFieldUiBinder extends
			UiBinder<Widget, SimpleFileFieldViewer> {
	}
	
	
	
	

	public SimpleFileFieldViewer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	@UiField Label field_name;
	@UiField VerticalPanel download_panel;
    private MetaUnitFileDTO dto;
    private String value;
	public SimpleFileFieldViewer(MetaUnitFileDTO dto,JSON_Representation filling,String def_value) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.value = def_value;
		initFields();
	}
	private void initFields()
	{
		this.field_name.setText(dto.getDesc());
		final long file_id = dto.getFile_id();
		download_panel.add(new HTML("<a href=\""+"/databanknewest/serve?blob-key="+file_id+"\">Скачать</a>"));
		
	}
}
