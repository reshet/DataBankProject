package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Light;

public class VarDescItem extends TreeItem{
	private long var_id;
	public static int LABEL_SHORTAGE_NUMBER = 60;
	public VarDescItem(final VarDTO_Light dto)
	{
		super();
		this.var_id = dto.getId();
		int end = dto.getLabel().length() > VarDescItem.LABEL_SHORTAGE_NUMBER? VarDescItem.LABEL_SHORTAGE_NUMBER:dto.getLabel().length();
		//this.setText(dto.getCode()+": "+dto.getLabel());
		Label l = new Label();
		l.setWordWrap(true);
		l.setWidth("200px");
		l.setText(dto.getCode()+": "+dto.getLabel().substring(0, end));
		this.setWidget(l);
		
		this.setTitle(dto.getCode()+": "+dto.getLabel());
		
//		l.addMouseOverHandler(new MouseOverHandler() {
//			
//			@Override
//			public void onMouseOver(MouseOverEvent arg0) {
//				// TODO Auto-generated method stub
//				GWT.log("mouse over " + dto.getCode()+": "+dto.getLabel());
//				
//			}
//		});
//		
//        l.addMouseOutHandler(new MouseOutHandler() {
//
//            @Override
//            public void onMouseOut(MouseOutEvent event) {
//                GWT.log("mouse out " + dto.getCode()+": "+dto.getLabel()); // do something better here
//            }
//
//        });
				//.substring(0, end));
	}
	public long getVar_id() {
		return var_id;
	}

}

