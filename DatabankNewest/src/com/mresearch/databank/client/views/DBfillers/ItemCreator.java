package com.mresearch.databank.client.views.DBfillers;

import java.util.HashMap;

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
import com.google.gwt.user.client.ui.ListBox;
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
import com.mresearch.databank.shared.MetaUnitDoubleDTO;
import com.mresearch.databank.shared.MetaUnitFileDTO;
import com.mresearch.databank.shared.MetaUnitIntegerDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;

import org.omg.CORBA.DATA_CONVERSION;
import org.zenika.widget.client.datePicker.DatePicker;

public class ItemCreator extends Composite {

	private static ItemCreatorUiBinder uiBinder = GWT
			.create(ItemCreatorUiBinder.class);

	interface ItemCreatorUiBinder extends
			UiBinder<Widget, ItemCreator> {
	}

	public ItemCreator() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private AdminSocioResearchServiceAsync service = AdminSocioResearchService.Util.getInstance();
	private MultiValuedField field;
	private PopupPanel par;
	 private MultiValuedEntity entity;
	@UiField VerticalPanel host;
	@UiField TextBox name_field;
	//private MetaUnitFiller new_field;
	  public ItemCreator(MultiValuedField field, MultiValuedEntity entity, PopupPanel parent)
	  {
	    initWidget((Widget)uiBinder.createAndBindUi(this));
	    this.field = field;
	    this.par = parent;
	    this.entity = entity;
	    initFields();
	  }
	private void initFields()
	{
		host.add(field);
	}
	@UiHandler(value="doAdd") 
	public void doAdd(ClickEvent ev)
	{
		String value = name_field.getText();
		HashMap<String, String> map = field.returnCollectedMap();
		addItemToDB(field.dto.getId(),value,map);
		//par.hide();
	}
	private void addItemToDB(final long id_entity,final String value,final HashMap<String,String> map)
	{
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on adding Entity Item!:"+caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Экземпляр успешно добавлен!");
				ItemCreator.this.entity.refreshMembersList();
			    ItemCreator.this.par.hide();
				par.hide();
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				service.addEntityItem(id_entity, value, map, cb);
			}
		}.retry(2);
	}
	@UiHandler(value="doCancel") 
	public void doCancel(ClickEvent ev)
	{
		par.hide();
	}
}
