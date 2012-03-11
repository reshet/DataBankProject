package com.mresearch.databank.client.views.DBfillers;

import java.util.ArrayList;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import org.omg.CORBA.DATA_CONVERSION;
import org.zenika.widget.client.datePicker.DatePicker;

public class EntityItemEditor extends Composite {

	private static FieldEditorUiBinder uiBinder = GWT
			.create(FieldEditorUiBinder.class);

	interface FieldEditorUiBinder extends
			UiBinder<Widget, EntityItemEditor> {
	}

	public EntityItemEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private AdminSocioResearchServiceAsync service = AdminSocioResearchService.Util.getInstance();
	private MultiValuedField field;
	public MultiValuedField getField() {
		return field;
	}

	@UiField VerticalPanel host;
	@UiField TextBox name_field;
	private PopupPanel par;
	private Long entity_item_id;
	 private MultiValuedEntity entity;
	public EntityItemEditor(MultiValuedField field,MultiValuedEntity entity,Long entity_item_id,String name_value,PopupPanel parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.field = field;
		this.par = parent;
		this.entity_item_id = entity_item_id;
		this.name_field.setText(name_value);
		this.entity = entity;
		initFields();
	}
	private void initFields()
	{
		host.clear();
		host.add(field);
	}
	
	@UiHandler(value="doEdit") 
	public void doEdit(ClickEvent ev)
	{
		new RPCCall<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on structure updating!");
			}
			@Override
			public void onSuccess(Void result) {
				Window.alert("Structure updated sucessfullly!");
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				service.editEntityItem(entity_item_id, name_field.getText(), field.returnCollectedMap(), cb);
			}
		}.retry(2);
		par.hide();
	}
	@UiHandler(value="doCancel") 
	public void doCancel(ClickEvent ev)
	{
		par.hide();
	}
}
