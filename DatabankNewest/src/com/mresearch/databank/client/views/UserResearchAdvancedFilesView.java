package com.mresearch.databank.client.views;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.presenters.AdminResearchDetailedPresenter;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.sun.java.swing.plaf.windows.resources.windows;

public class UserResearchAdvancedFilesView extends Composite implements AdminResearchDetailedPresenter.FilesEditDisplay{
	private static AdminResearchFilesEditViewUiBinder uiBinder = GWT
			.create(AdminResearchFilesEditViewUiBinder.class);

	interface AdminResearchFilesEditViewUiBinder extends
			UiBinder<Widget, UserResearchAdvancedFilesView> {
	}
	private final UserSocioResearchServiceAsync userResearchService = GWT
		      .create(UserSocioResearchService.class);
	
	private long research_id;
	@UiField VerticalPanel arrays_panel,quest_panel,quest_cards_panel,protocol_panel,technical_report_panel;
	@UiField VerticalPanel instructions_panel,analytic_report_panel,publications_panel;
	public UserResearchAdvancedFilesView(long research_id) {
		initWidget(uiBinder.createAndBindUi(this));
		this.research_id = research_id;
		doFetchFiles();
	}

	private void doFetchFiles()
	{
		new RPCCall<ResearchFilesDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on fetching files "+caught.getMessage());
			}

			@Override
			public void onSuccess(ResearchFilesDTO result) {
				SocioResearchFilesDTO arr_dto = new SocioResearchFilesDTO(result.getFileIds(ResearchFilesDTO.CG_arrays),result.getFileNames(ResearchFilesDTO.CG_arrays));
				UserResearchFilesView arr_view = new UserResearchFilesView(research_id, ResearchFilesDTO.CG_arrays, arr_dto);
				arrays_panel.add(arr_view);
		
				SocioResearchFilesDTO quest_dto = new SocioResearchFilesDTO(result.getFileIds(ResearchFilesDTO.CG_questionaries),result.getFileNames(ResearchFilesDTO.CG_questionaries));
				UserResearchFilesView quest_view = new UserResearchFilesView(research_id, ResearchFilesDTO.CG_questionaries, quest_dto);
				quest_panel.add(quest_view);
				
				SocioResearchFilesDTO qc_dto = new SocioResearchFilesDTO(result.getFileIds(ResearchFilesDTO.CG_questionary_cards),result.getFileNames(ResearchFilesDTO.CG_questionary_cards));
				UserResearchFilesView qc_view = new UserResearchFilesView(research_id, ResearchFilesDTO.CG_questionary_cards, qc_dto);
				quest_cards_panel.add(qc_view);
		
				SocioResearchFilesDTO pr_dto = new SocioResearchFilesDTO(result.getFileIds(ResearchFilesDTO.CG_protocols),result.getFileNames(ResearchFilesDTO.CG_protocols));
				UserResearchFilesView pr_view = new UserResearchFilesView(research_id, ResearchFilesDTO.CG_protocols,pr_dto);
				protocol_panel.add(pr_view);
		
				SocioResearchFilesDTO tr_dto = new SocioResearchFilesDTO(result.getFileIds(ResearchFilesDTO.CG_technical_reports),result.getFileNames(ResearchFilesDTO.CG_technical_reports));
				UserResearchFilesView tr_view = new UserResearchFilesView(research_id, ResearchFilesDTO.CG_technical_reports, tr_dto);
				technical_report_panel.add(tr_view);
				
				SocioResearchFilesDTO i_dto = new SocioResearchFilesDTO(result.getFileIds(ResearchFilesDTO.CG_instructions),result.getFileNames(ResearchFilesDTO.CG_instructions));
				UserResearchFilesView i_view = new UserResearchFilesView(research_id, ResearchFilesDTO.CG_instructions, i_dto);
				instructions_panel.add(i_view);
	
				SocioResearchFilesDTO ar_dto = new SocioResearchFilesDTO(result.getFileIds(ResearchFilesDTO.CG_analytic_reports),result.getFileNames(ResearchFilesDTO.CG_analytic_reports));
				UserResearchFilesView ar_view = new UserResearchFilesView(research_id, ResearchFilesDTO.CG_analytic_reports, ar_dto);
				analytic_report_panel.add(ar_view);
				
				SocioResearchFilesDTO p_dto = new SocioResearchFilesDTO(result.getFileIds(ResearchFilesDTO.CG_publications),result.getFileNames(ResearchFilesDTO.CG_publications));
				UserResearchFilesView p_view = new UserResearchFilesView(research_id, ResearchFilesDTO.CG_publications, p_dto);
				publications_panel.add(p_view);
			}

			@Override
			protected void callService(AsyncCallback<ResearchFilesDTO> cb) {
				userResearchService.getResearchFiles(research_id, cb);
			}
		}.retry(2);
	}
}
