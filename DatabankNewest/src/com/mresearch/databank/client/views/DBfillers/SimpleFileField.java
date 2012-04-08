package com.mresearch.databank.client.views.DBfillers;

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

public class SimpleFileField extends Composite implements MetaUnitFiller{

	private static SimpleStringFieldUiBinder uiBinder = GWT
			.create(SimpleStringFieldUiBinder.class);

	interface SimpleStringFieldUiBinder extends
			UiBinder<Widget, SimpleFileField> {
	}

	public SimpleFileField() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label field_name;
	@UiField VerticalPanel upload_panel;
    private MetaUnitFileDTO dto;
    private JSON_Representation current_json;
    private String value;
	private ArrayList<Long> uploaded_files = new ArrayList<Long>();
	private String base_name;
	public SimpleFileField(MetaUnitFileDTO dto,JSON_Representation filling,String def_value,String base_name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.current_json = filling;
		this.value = def_value;
		initFields();
	}
	private void initFields()
	{
		this.field_name.setText(dto.getDesc());
		//if(value!=null) this.contents.setText(def_value);
		
		final SingleUploader upm = new SingleUploader();
		//upm.setMaximumFiles(5);
		upm.setValidExtensions("sav");
		upm.addOnStartUploadHandler(new OnStartUploaderHandler() {
			@Override
			public void onStart(IUploader uploader) {
			}
		});
		upm.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			@Override
			public void onFinish(IUploader uploader) {
				String response = uploader.getServerResponse();
				processUploadResponse(response);
				upm.reuse();
				upm.reset();
			}
		});
		upload_panel.add(upm);
	}
	
	private void processUploadResponse(String response)
	{
		try
		{
			int start = response.indexOf("<RxStoreId>")+11;
			int end = response.indexOf("</RxStoreId>");
			String keyy = response.substring(start,end);
			int start2 = response.indexOf("<size>")+6;
			int end2 = response.indexOf("</size>");
			String len = response.substring(start2,end2);
			if(keyy != null)
			{
				Long id = Long.parseLong(keyy);
				if(!uploaded_files.contains(id))
				{
					uploaded_files.add(id);
					this.value = String.valueOf(id);
					int blob_length = Integer.parseInt(len);
				}
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void rebuildJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("name",new JSONString(dto.getUnique_name()));
		obj.put("description",new JSONString(dto.getDesc()));
		obj.put("type",new JSONString("MetaUnitFile"));
		obj.put("value", new JSONString(value));
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
		return "empty_file";
		//return contents.getText();
	}
	@Override
	public MetaUnitDTO getDTO() {
		return dto;
	}
}
