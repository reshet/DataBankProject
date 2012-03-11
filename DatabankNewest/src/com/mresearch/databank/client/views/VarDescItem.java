package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Light;

public class VarDescItem extends TreeItem{
	private long var_id;
	public static int LABEL_SHORTAGE_NUMBER = 26;
	public VarDescItem(VarDTO_Light dto)
	{
		super();
		this.var_id = dto.getId();
		int end = dto.getLabel().length() > VarDescItem.LABEL_SHORTAGE_NUMBER? VarDescItem.LABEL_SHORTAGE_NUMBER:dto.getLabel().length();
		this.setText(dto.getCode()+": "+dto.getLabel().substring(0, end));
	}
	public long getVar_id() {
		return var_id;
	}

}
