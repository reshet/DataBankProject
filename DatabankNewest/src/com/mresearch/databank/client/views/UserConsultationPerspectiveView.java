package com.mresearch.databank.client.views;

import java.util.ArrayList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.plaf.basic.BasicTreeUI.TreeSelectionHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.presenters.UserLawPerspectivePresenter;
import com.mresearch.databank.client.presenters.UserJuryPerspectivePresenter;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.ConsultationDTO;
import com.mresearch.databank.shared.ConsultationDTO_Light;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.TopicDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.ConsultationDTO;
import com.smartgwt.client.widgets.HTMLPane;

public class UserConsultationPerspectiveView extends Composite implements UserJuryPerspectivePresenter.Display{

	private static UserConsultationPerspectiveViewUiBinder uiBinder = GWT
			.create(UserConsultationPerspectiveViewUiBinder.class);

	interface UserConsultationPerspectiveViewUiBinder extends
			UiBinder<Widget, UserConsultationPerspectiveView> {
	}
	@UiField VerticalPanel centerPanel;
	@UiField FlexTable topics;
	@UiField Button ask_btn;
	@UiField TextBox asker_name,asker_email;	
	@UiField TextArea question;
	@UiField Label done_lbl;
	private Long Consultation_to_find =null;
	private ArrayList<ConsultationDTO_Light> lastpubList;
	public UserConsultationPerspectiveView(SimpleEventBus bus) {
		initWidget(uiBinder.createAndBindUi(this));

		//rootDataLawConcepts = new RootConceptsList("law", "Рубрикатор");
		//tree.addItem(new RootFilterItemAdvanced(centerPanel,bus,"law","Фильтр"));
		
		
		KeyPressHandler h = new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent arg0) {
				ask_btn.setEnabled(checkCanAsk());
			}
		};
		question.addKeyPressHandler(h);
		asker_email.addKeyPressHandler(h);
		asker_name.addKeyPressHandler(h);

	}
	private boolean checkCanAsk()
	{
		return (
				!asker_name.getText().equals("")
				&& !asker_email.getText().equals("") && asker_email.getText().contains("@") && !question.getText().equals("")
				);
				
	}
	private void displayConsultationList()
	{
//		lastPubs.removeAllRows();
//		int i = 0;
//		for(ConsultationDTO_Light dto:lastpubList)
//		{
//			ConsultationDescItemView research_node = new ConsultationDescItemView(dto);
//			lastPubs.setWidget(i++, 0, research_node);
//		}
	}
	
	
	
	@Override
	public void setConsultationListData(ArrayList<ConsultationDTO_Light> data) {
		lastpubList = data;
		displayConsultationList();
	}
	
	@Override
	public void showLoadingLabel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showConsultationDetailes(final ConsultationDTO dto,final String path) {
		centerPanel.clear();
		
		new RPCCall<MetaUnitMultivaluedEntityDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
				ConsultationDetailedView view = new ConsultationDetailedView(dto,res,path);
				centerPanel.add(view);
			}

			@Override
			protected void callService(
					AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
				UserSocioResearchService.Util.getInstance().getDatabankStructure("consultation",cb);
			}
		}.retry(2);
		
	}
	
	@Override
	public VerticalPanel getCenterPanel() {
		return centerPanel;
	}
	
	@Override
	public void findInConsultationList(Long id) {
		
	}
	
	
	
	@Override
	public void showConsultationIndex(final ArrayList<ConsultationDTO> dtos, final String path,MetaUnitMultivaluedEntityDTO meta) {
		centerPanel.clear();
		
		centerPanel.add(new HTMLPanel("<a href=\"#\" class=\"blue\"><h1 class=\"header\">"+path+"</h1></a>"));
		
		for(ConsultationDTO dt:dtos)
		{	
			centerPanel.add(new ConsultationIndexedView(dt, meta));
		}
		
//		new RPCCall<MetaUnitMultivaluedEntityDTO>() {
//
//			@Override
//			public void onFailure(Throwable arg0) {
//			}
//
//			@Override
//			public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
//				
//				
//			}
//
//			@Override
//			protected void callService(
//					AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
//				UserSocioResearchService.Util.getInstance().getDatabankStructure("consultation",cb);
//			}
//		}.retry(2);
		
	}

	
	
	
	
	@Override
	public void showTopics(ArrayList<TopicDTO> tops) {
		topics.removeAllRows();
		int i = 0;
		for(TopicDTO dto:tops)
		{
			topics.setWidget(i++, 0, new JuryTopicItemView(dto));
		}
	}
	@Override
	public HasClickHandlers getAskBtn() {
		return ask_btn;
	}
	@Override
	public ConsultationDTO getQuestion() {
		ConsultationDTO dto = new ConsultationDTO();
		dto.setquestion(question.getText());
		return dto;
	}
	@Override
	public void showSuccessSend() {
		Timer t = new Timer() {
			@Override
			public void run() {
				done_lbl.setVisible(false);
			}
		};
		done_lbl.setVisible(true);
		t.schedule(2000);
	}

	
}
