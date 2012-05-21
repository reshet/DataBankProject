package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.shared.ICatalogizationConcept;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;

public class SimpleResearchList extends TreeItem implements ICatalogizationConcept{
	private final CatalogServiceAsync catalogService = GWT
    .create(CatalogService.class);
	public SimpleResearchList()
	{
		super();
		setText("Простой список");
		addItem("Загрузка...");
	}
	
	@Override
	public void refreshContents() {
		catalogService.getResearchList(new AsyncCallback<ArrayList<SocioResearchDTO_Light>>() {
			@Override
			public void onSuccess(ArrayList<SocioResearchDTO_Light> result) {
				SimpleResearchList.this.removeItems();
				for(SocioResearchDTO_Light dto:result)
				{
					ResearchDescItem r_desc = new ResearchDescItem(dto);
					ResearchVarList research_node = new ResearchVarList(dto);
					//for(String )
					r_desc.addItem(research_node);
					SimpleResearchList.this.addItem(r_desc);
					//SimpleResearchList.this.addItem(research_node);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
			
				Window.alert("Failure on refresh contents of catalog");
			}
		});
	}

}
