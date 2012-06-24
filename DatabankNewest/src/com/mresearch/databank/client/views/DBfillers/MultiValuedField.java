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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitDoubleDTO;
import com.mresearch.databank.shared.MetaUnitFileDTO;
import com.mresearch.databank.shared.MetaUnitIntegerDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;

public class MultiValuedField extends Composite implements MetaUnitFiller,MetaUnitCollector, MetaUnitEntityItemRegistrator{

	private static MultiValuedFieldUiBinder uiBinder = GWT
			.create(MultiValuedFieldUiBinder.class);

	interface MultiValuedFieldUiBinder extends
			UiBinder<Widget, MultiValuedField> {
	}

	public MultiValuedField() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label field_name;
	@UiField FlexTable subunits_table;
	public MetaUnitMultivaluedDTO dto;
	private JSON_Representation current_json;
	private HashMap<String,String> filling;
	private String base_name;
	public MultiValuedField(MetaUnitMultivaluedDTO dto,JSON_Representation represent,HashMap<String,String> fill,String base_name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.filling = fill;
		this.base_name = base_name.equals("")?dto.getUnique_name():base_name+"_"+dto.getUnique_name();
		field_name.setText(dto.getDesc());
		renderSubUnits();
	}
	
	@UiHandler(value="edit") 
	public void editCmd(ClickEvent ev)
	{
		PopupPanel p = new PopupPanel();
		p.setTitle("Редактирование поля...");
		p.setModal(true);
		p.setPopupPosition(400, 400);
		p.setSize("800px", "800px");
		p.setWidget(new FieldEditor(this,p));
		p.show();	
	}
	private void renderSubUnits()
	{
		
		subunits_table.clear();
		Collection<MetaUnitDTO> base = dto.getSub_meta_units();
		int i = 0;
		if(base!=null)
		for(MetaUnitDTO dto:base)
		{
			if(dto instanceof MetaUnitStringDTO)
			{
				MetaUnitStringDTO dto_str = (MetaUnitStringDTO)dto;
				String def_val= null;
				if(filling.containsKey(base_name+"_"+dto.getUnique_name()))def_val = filling.get(base_name+"_"+dto.getUnique_name());
				subunits_table.setWidget(i++, 0, new SimpleStringField(dto_str,null,def_val,base_name));
			}else
			if(dto instanceof MetaUnitDateDTO)
			{
				MetaUnitDateDTO dto_str = (MetaUnitDateDTO)dto;
				String def_val= null;
				if(filling.containsKey(base_name+"_"+dto.getUnique_name()))def_val = filling.get(base_name+"_"+dto.getUnique_name());
				subunits_table.setWidget(i++, 0, new SimpleDateField(dto_str,null,def_val,base_name));
			}
			if(dto instanceof MetaUnitIntegerDTO)
			{
				MetaUnitIntegerDTO dto_str = (MetaUnitIntegerDTO)dto;
				String def_val= null;
				if(filling.containsKey(base_name+"_"+dto.getUnique_name()))def_val = filling.get(base_name+"_"+dto.getUnique_name());
				subunits_table.setWidget(i++, 0, new SimpleIntegerField(dto_str,null,def_val,base_name));
			}
			if(dto instanceof MetaUnitDoubleDTO)
			{
				MetaUnitDoubleDTO dto_str = (MetaUnitDoubleDTO)dto;
				String def_val= null;
				if(filling.containsKey(base_name+"_"+dto.getUnique_name()))def_val = filling.get(base_name+"_"+dto.getUnique_name());
				subunits_table.setWidget(i++, 0, new SimpleDoubleField(dto_str,null,def_val,base_name));
			}else
			if(dto instanceof MetaUnitFileDTO)
			{
				MetaUnitFileDTO dto_str = (MetaUnitFileDTO)dto;
				String def_val= null;
				if(filling.containsKey(base_name+"_"+dto.getUnique_name()))def_val = filling.get(base_name+"_"+dto.getUnique_name());
				subunits_table.setWidget(i++, 0, new SimpleFileField(dto_str,null,def_val,base_name));
			}else				
			if(dto instanceof MetaUnitMultivaluedStructureDTO)
			{
				MetaUnitMultivaluedStructureDTO dto_str = (MetaUnitMultivaluedStructureDTO)dto;
				subunits_table.setWidget(i++, 0, new MultiValuedField(dto_str,null,filling,base_name));
			}else
			if(dto instanceof MetaUnitMultivaluedEntityDTO)
			{
				MetaUnitMultivaluedEntityDTO dto_str = (MetaUnitMultivaluedEntityDTO)dto;
				if(dto_str.isIsMultiselected())
				{
					subunits_table.setWidget(i++, 0, new MultiValuedEntityMultiselected(dto_str,null,filling,base_name));
				}else
				{
					subunits_table.setWidget(i++, 0, new MultiValuedEntity(dto_str,null,filling,base_name));
				}
			}
			
		}
	}
	
	
	
	private void rebuildJSON()
	{
		JSONObject obj = new JSONObject();
	    for (int i = 0; i < this.subunits_table.getRowCount(); i++)
	    {
	      MetaUnitFiller filler = (MetaUnitFiller)this.subunits_table.getWidget(i, 0);
	      if(!(filler instanceof MultiValuedField))
	    	  this.filling.put(base_name +"_"+filler.getUniqueName(), filler.getFilledValue());
	      JSON_Representation cur_json = filler.getJSON();
	      for (String key : cur_json.getObj().keySet())
	      {
	        //obj.put(this.dto.getUnique_name() + "_" + key, cur_json.getObj().get(key));
	    	  obj.put(key, cur_json.getObj().get(key));
	 	  }

	    }
		current_json = new JSON_Representation(obj);
		//here to build JSON from children;
		
		
		
	}
	
	
	
	
	@Override
	public String getUniqueName() {
		return base_name;
	}
	@Override
	public JSON_Representation getJSON() {
		rebuildJSON();
		return current_json;
	}

	@Override
	public String getFilledValue() {
		rebuildJSON();
		return current_json.getRepresent();
	}

	@Override
	public HashMap<String, String> returnCollectedMap() {
		rebuildJSON();
		return filling;
	}
	@Override
	public MetaUnitDTO getDTO() {
		return dto;
	}
//	@Override
//	public FlexTable getUnitsTable() {
//		return subunits_table;
//	}

	@Override
	public void populateItemsLinksTo(Long paramLong, String paramString) {
		for (int i = 0; i < this.subunits_table.getRowCount(); i++)
	    {
	      Widget filler =  this.subunits_table.getWidget(i, 0);
	      if(filler instanceof MetaUnitEntityItemRegistrator)
	      {
	    	  MetaUnitEntityItemRegistrator reg = (MetaUnitEntityItemRegistrator)filler;
	    	  reg.populateItemsLinksTo(paramLong, paramString);
	      }
	    }
	}
}
