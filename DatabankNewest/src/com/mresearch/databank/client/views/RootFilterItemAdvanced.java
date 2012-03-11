package com.mresearch.databank.client.views;

import java.awt.Checkbox;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.FilterDateDiapasonDTO;
import com.mresearch.databank.shared.FilterDiapasonDTO;
import com.mresearch.databank.shared.FilterMatchDTO;
import com.mresearch.databank.shared.FilterMultiDTO;
import com.mresearch.databank.shared.SSE_DTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Light;

public class RootFilterItemAdvanced extends TreeItem{
	private VerticalPanel results_viewer;
	private Button doFilterBtn;
	private CheckBox doUseFilters;
	private UserSocioResearchServiceAsync service = GWT.create(UserSocioResearchService.class);
	private final ArrayList<IFilterProvider> allFiltersRegister = new ArrayList<IFilterProvider>();
	
}
