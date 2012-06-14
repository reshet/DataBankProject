package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.shared.ICatalogizable;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;


public class ConceptContentsItem extends TreeItem implements WrappedCustomLabel{
	private long contents_id;
	private Label l = new Label();
	public ConceptContentsItem(ICatalogizable dto)
	{
		super();
		this.contents_id = dto.getID();
		
		int end = dto.getTextRepresent().length() > VarDescItem.LABEL_SHORTAGE_NUMBER? VarDescItem.LABEL_SHORTAGE_NUMBER:dto.getTextRepresent().length();
		//this.setText(dto.getCode()+": "+dto.getLabel());
		l.setWordWrap(true);
		l.setWidth("200px");
		l.setText(dto.getTextRepresent().substring(0, end));
		this.setWidget(l);
		l.setStylePrimaryName("gwt-TreeItem");
		
		//l.add
		this.setTitle(dto.getTextRepresent());
	}
	public Label getLabel(){return l;}
	public long getContents_id() {
		return contents_id;
	}

}
