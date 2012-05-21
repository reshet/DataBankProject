package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.shared.ICatalogizable;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;


public class ConceptContentsItem extends TreeItem{
	private long contents_id;
	public ConceptContentsItem(ICatalogizable dto)
	{
		super();
		this.contents_id = dto.getID();
		this.setText(dto.getTextRepresent());
	}
	public long getContents_id() {
		return contents_id;
	}

}
