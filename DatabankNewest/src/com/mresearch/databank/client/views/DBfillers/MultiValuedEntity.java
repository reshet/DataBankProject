package com.mresearch.databank.client.views.DBfillers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
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
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;

public class MultiValuedEntity extends Composite implements MetaUnitFiller{

	private static MultiValuedEntityUiBinder uiBinder = GWT
			.create(MultiValuedEntityUiBinder.class);

	interface MultiValuedEntityUiBinder extends
			UiBinder<Widget, MultiValuedEntity> {
	}

	public MultiValuedEntity() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private AdminSocioResearchServiceAsync service = AdminSocioResearchService.Util.getInstance();
	@UiField Label entity_name;
	@UiField ListBox items_list;
	private MetaUnitMultivaluedEntityDTO dto;
	private JSON_Representation current_json;
	private HashMap<String,String> filling;
	public MultiValuedEntity(MetaUnitMultivaluedEntityDTO dto,JSON_Representation represent,HashMap<String,String> filling) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		entity_name.setText(dto.getDesc());
		this.filling = filling;
		items_list.setMultipleSelect(dto.isIsMultiselected());
		items_list.setVisibleItemCount(1);
		renderSubUnits();
	}
	@UiHandler(value="add") 
	public void addCmd(ClickEvent ev)
	{
		PopupPanel p = new PopupPanel();
		p.setTitle("Добавление экземпляра сущности...");
		p.setModal(true);
		p.setPopupPosition(300, 300);
		p.setSize("800px", "800px");
		p.setWidget(new MultiValuedField(dto, null, new HashMap<String, String>()));
		p.show();	
	}
	@UiHandler(value="delete") 
	public void delCmd(ClickEvent ev)
	{
		int ind = items_list.getSelectedIndex();
		if(ind >=0)
		{
			final Long id = dto.getItem_ids().get(ind);
			new RPCCall<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error on deleting item!"+caught.getMessage());
				}
				@Override
				public void onSuccess(Void result) {
					Window.alert("Item sucessfully deleted!");
				}
				@Override
				protected void callService(AsyncCallback<Void> cb) {
					service.deleteEntityItem(id, cb);
				}
			}.retry(2);
		}
	}
	@UiHandler(value="edit") 
	public void editCmd(ClickEvent ev)
	{
		PopupPanel p = new PopupPanel();
		p.setTitle("Редактирование поля...");
		p.setModal(true);
		p.setPopupPosition(400, 400);
		p.setSize("800px", "800px");
		p.setWidget(new FieldEditor(new MultiValuedField(dto, null, filling),p));
		p.show();	
	}
	@UiHandler(value="edit_item") 
	public void editItemCmd(ClickEvent ev)
	{
		int ind = items_list.getSelectedIndex();
		if(ind >=0)
		{
			final Long id = dto.getItem_ids().get(ind);
			final String name = dto.getItem_names().get(ind);
			new RPCCall<HashMap<String, String>>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error on getting entity item info!"+caught.getMessage());
				}
				@Override
				public void onSuccess(HashMap<String, String> result) {
					PopupPanel p = new PopupPanel();
					p.setTitle("Редактирование объекта...");
					p.setModal(true);
					p.setPopupPosition(400, 400);
					p.setSize("800px", "800px");
					p.setWidget(new EntityItemEditor(new MultiValuedField(dto, null, result),id,name,p));
					p.show();	
				}

				@Override
				protected void callService(
						AsyncCallback<HashMap<String, String>> cb) {
					service.getEntityItem(id, cb);
				}
			}.retry(2);
		}
	}
	
	private void renderSubUnits()
	{
		items_list.clear();
		ArrayList<String> base = dto.getItem_names();
		int i = 0;
		if(base!=null)
		for(String dto_name:base)
		{
//			if(dto instanceof MetaUnitStringDTO)
//			{
//				MetaUnitStringDTO dto_str = (MetaUnitStringDTO)dto;
//				subunits_table.setWidget(i++, 0, new SimpleStringField(dto_str,null));
//			}else
//			if(dto instanceof MetaUnitDateDTO)
//			{
//				MetaUnitDateDTO dto_str = (MetaUnitDateDTO)dto;
//				subunits_table.setWidget(i++, 0, new SimpleDateField(dto_str,null));
//			}else
//			if(dto instanceof MetaUnitMultivaluedDTO)
//			{
//				MetaUnitMultivaluedDTO dto_str = (MetaUnitMultivaluedDTO)dto;
//				items_list.addItem(dto_str.getDesc());
//				//subunits_table.setWidget(i++, 0, new MultiValuedEntity(dto_str,null));
//			}
			items_list.addItem(dto_name);
		}
		if(filling.containsKey(dto.getUnique_name()))
		{
			String val = filling.get(dto.getUnique_name());
			int index = dto.getItem_names().indexOf(val);
			items_list.setSelectedIndex(index);
		}
	}
	
	
	private void rebuildJSON()
	{
		
		JSONObject obj = new JSONObject();
		obj.put("name",new JSONString(dto.getUnique_name()));
		obj.put("description",new JSONString(dto.getDesc()));
		JSONArray arr = new JSONArray();
		for(int i = 0;i < items_list.getItemCount();i++)
		{
			if(items_list.isItemSelected(i))
			{
				String val = items_list.getValue(i);
				//MetaUnitFiller filler = new MultiValuedField((MetaUnitMultivaluedDTO)dto.getSub_meta_units().get(i), null);
				MetaUnitFiller filler = new SimpleStringField(new MetaUnitStringDTO(new Long(0), "", dto.getUnique_name()),null,val);
				JSON_Representation cur_json = filler.getJSON();
				arr.set(i, cur_json.getObj());
			}
		}
		obj.put("meta_units",arr);
		current_json = new JSON_Representation(obj);
		//here to build JSON from children;
	}
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
		//TODO NEED TO EXTEND FOR MULTIPLE SELECT!!!
		int index = 0;
		if(items_list.getItemCount()<=0)return null;
		if(items_list.getSelectedIndex()!= -1)index = items_list.getSelectedIndex();
		String val = items_list.getItemText(index);
		return val;
	}
	@Override
	public MetaUnitDTO getDTO() {
		return dto;
	}
}
