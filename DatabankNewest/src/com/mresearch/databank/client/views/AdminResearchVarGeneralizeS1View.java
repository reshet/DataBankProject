package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.IPickableElement;


//There MVP pattern is ommited)
public class AdminResearchVarGeneralizeS1View extends Composite {

	private static AdminResearchVarGeneralizeS1ViewUiBinder uiBinder = GWT
			.create(AdminResearchVarGeneralizeS1ViewUiBinder.class);
	private UserSocioResearchServiceAsync service = GWT.create(UserSocioResearchService.class);
	private AdminSocioResearchServiceAsync service_admin = GWT.create(AdminSocioResearchService.class);
	interface AdminResearchVarGeneralizeS1ViewUiBinder extends
			UiBinder<Widget, AdminResearchVarGeneralizeS1View> {
	}
	private Long research_id;
	private HasWidgets cont;
	@UiField VerticalPanel panel;
	//@UiField Button process_btn;
	//@UiField HTMLPanel percents_type_pnl;
	public AdminResearchVarGeneralizeS1View(long research_id,HasWidgets cont) {
		initWidget(uiBinder.createAndBindUi(this));
		this.research_id = research_id;
		this.cont = cont;
		fetchResearchesList();
	}
	private void fetchResearchesList()
	{
		new RPCCall<ArrayList<SocioResearchDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting research summaries: "+caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<SocioResearchDTO> result) {
				ArrayList<IPickableElement> elems = new ArrayList<IPickableElement>();
				for(SocioResearchDTO dto:result) elems.add(dto);
				panel.add(new PickElementsTableView(elems,new ArrayList<Long>(), new IPickBinder() {
					
					@Override
					public void processPickChoice(final ArrayList<Long> selected_keys) {
						cont.clear();
						new RPCCall<ArrayList<SocioResearchDTO>>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Eror getting research summaries"+caught.getMessage());
							}

							@Override
							public void onSuccess(
									ArrayList<SocioResearchDTO> result) {
								ArrayList<String> names = new ArrayList<String>();
								for(SocioResearchDTO dto:result)names.add(dto.getName());
								cont.add(new AdminResearchVarGeneralizeS2View(research_id,selected_keys,names));
							}

							@Override
							protected void callService(
									AsyncCallback<ArrayList<SocioResearchDTO>> cb) {
								service.getResearchDTOs(selected_keys, cb);
							}
						}.retry(2);
					}
					
					@Override
					public String getTitle() {
						return "Выберите исследования для генерализации";
					}
					
					@Override
					public String getCommandName() {
						return "Выбрать исследования!";
					}
				}));
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<SocioResearchDTO>> cb) {
				service.getResearchSummaries(cb);
			}
		}.retry(2);
	}
	
}
