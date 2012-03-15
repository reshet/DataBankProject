package com.mresearch.databank.client.views.DBviewers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

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
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;

public class MultiValuedEntityViewer extends Composite{

	private static MultiValuedEntityUiBinder uiBinder = GWT
			.create(MultiValuedEntityUiBinder.class);

	interface MultiValuedEntityUiBinder extends
			UiBinder<Widget, MultiValuedEntityViewer> {
	}

	public MultiValuedEntityViewer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private AdminSocioResearchServiceAsync service = AdminSocioResearchService.Util.getInstance();
	@UiField Label entity_name;
	@UiField TextBox items_list;
	private MetaUnitMultivaluedEntityDTO dto;
	private HashMap<String,String> filling;
	public MultiValuedEntityViewer(MetaUnitMultivaluedEntityDTO dto,JSON_Representation represent,HashMap<String,String> filling) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		entity_name.setText(dto.getDesc());
		this.filling = filling;
		//renderSubUnits();
		refreshMembersList();
	}
	 
		  public void refreshMembersList() {
		    new RPCCall<MetaUnitMultivaluedEntityDTO>()
		    {
		      public void onFailure(Throwable caught)
		      {
		        Window.alert("Error on updating memebers list!" + caught.getMessage());
		      }

		      public void onSuccess(MetaUnitMultivaluedEntityDTO result)
		      {
		        MultiValuedEntityViewer.this.dto = result;
		        MultiValuedEntityViewer.this.renderSubUnits();
		      }

		      protected void callService(AsyncCallback<MetaUnitMultivaluedEntityDTO> cb)
		      {
		        MultiValuedEntityViewer.this.service.getMetaUnitMultivaluedEntityDTO_FlattenedItems(MultiValuedEntityViewer.this.dto.getId(), cb);
		      }
		    }
		    .retry(2);
		  }
	private void renderSubUnits()
	{
		items_list.setText("");
		ArrayList<String> base = dto.getItem_names();

		if(filling.containsKey(dto.getUnique_name()))
		{
			  String val = (String)this.filling.get(this.dto.getUnique_name());
		      if (val != null)
		      {
		    	  items_list.setText(val);
		      }
		}
	}
}
