package com.mresearch.databank.client.views;

import java.util.ArrayList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.plaf.basic.BasicTreeUI.TreeSelectionHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.presenters.UserLawPerspectivePresenter;
import com.mresearch.databank.client.presenters.UserPubPerspectivePresenter;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.PublicationDTO;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.TopicDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.PublicationDTO;
import com.smartgwt.client.widgets.HTMLPane;

public class UserPublicationPerspectiveView extends Composite implements UserPubPerspectivePresenter.Display{

	private static UserPublicationPerspectiveViewUiBinder uiBinder = GWT
			.create(UserPublicationPerspectiveViewUiBinder.class);

	interface UserPublicationPerspectiveViewUiBinder extends
			UiBinder<Widget, UserPublicationPerspectiveView> {
	}
	@UiField VerticalPanel centerPanel;
	@UiField FlexTable lastPubs,topics;
	private Long Publication_to_find =null;
	private ArrayList<PublicationDTO_Light> lastpubList;
	public UserPublicationPerspectiveView(SimpleEventBus bus) {
		initWidget(uiBinder.createAndBindUi(this));

		//rootDataLawConcepts = new RootConceptsList("law", "Рубрикатор");
		//tree.addItem(new RootFilterItemAdvanced(centerPanel,bus,"law","Фильтр"));

	}
	private void displayPublicationList()
	{
		lastPubs.removeAllRows();
		int i = 0;
		for(PublicationDTO_Light dto:lastpubList)
		{
			PublicationDescItemView research_node = new PublicationDescItemView(dto);
			lastPubs.setWidget(i++, 0, research_node);
		}
	}
	
	
	
	@Override
	public void setPublicationListData(ArrayList<PublicationDTO_Light> data) {
		lastpubList = data;
		displayPublicationList();
	}
	
	@Override
	public void showLoadingLabel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPublicationDetailes(final PublicationDTO dto,final String path) {
		centerPanel.clear();
		
		new RPCCall<MetaUnitMultivaluedEntityDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
				PublicationDetailedView view = new PublicationDetailedView(dto,res,path);
				centerPanel.add(view);
			}

			@Override
			protected void callService(
					AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
				UserSocioResearchService.Util.getInstance().getDatabankStructure("publication",cb);
			}
		}.retry(2);
		
	}
	
	@Override
	public VerticalPanel getCenterPanel() {
		return centerPanel;
	}
	
	@Override
	public void findInPublicationList(Long id) {
		
	}
	
	
	
	@Override
	public void showPublicationIndex(final ArrayList<PublicationDTO> dtos, final String path,MetaUnitMultivaluedEntityDTO meta) {
		centerPanel.clear();
		
		centerPanel.add(new HTMLPanel("<a href=\"#\" class=\"dark-yellow\"><h1 class=\"header\">"+path+"</h1></a>"));
		
		for(PublicationDTO dt:dtos)
		{	
			centerPanel.add(new PublicationIndexedView(dt,meta));
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
//				UserSocioResearchService.Util.getInstance().getDatabankStructure("publication",cb);
//			}
//		}.retry(2);
		
	}

	@Override
	public void showTopics(ArrayList<TopicDTO> tops) {
		topics.removeAllRows();
		int i = 0;
		for(TopicDTO dto:tops)
		{
			topics.setWidget(i++, 0, new TopicItemView(dto));
		}
	}

	
}
