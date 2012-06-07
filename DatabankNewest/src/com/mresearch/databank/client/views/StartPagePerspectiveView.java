package com.mresearch.databank.client.views;

import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.presenters.StartPagePerspectivePresenter;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.ZaconDTO_Light;

public class StartPagePerspectiveView extends Composite implements StartPagePerspectivePresenter.Display{

	private static StartPagePerspectiveViewUiBinder uiBinder = GWT
			.create(StartPagePerspectiveViewUiBinder.class);

	interface StartPagePerspectiveViewUiBinder extends
			UiBinder<Widget, StartPagePerspectiveView> {
	}
	
	

//	@UiField VerticalPanel latestNews;
//	@UiField HTML mainPageArticle;
//	@UiField FlexTable latestNewsTable;
//	@UiField Label loadingLabel;
//	
	
	//@UiField ScrollPanel scroller;
	@UiField VerticalPanel research_links,pub_links,law_links,cons_links;
	public StartPagePerspectiveView() {
		initWidget(uiBinder.createAndBindUi(this));
		cons_links.add(new JuryDescItemViewAll(new ZaconDTO_Light(0,"")));
	//	scroller.setAlwaysShowScrollBars(true);
	//	StartPagePerspectivePresenter
	}

	@Override
	public HasClickHandlers getNewsList() {
		//return latestNewsTable;
		return null;
	}

//	@Override
//	public HasClickHandlers getMainLink() {
//		return mainNav;
//	}
//
//	@Override
//	public HasClickHandlers getResearchLink() {
//		return researchNav;
//	}
//
//	@Override
//	public HasClickHandlers getLawLink() {
//		return lawNav;
//	}
//
//	@Override
//	public HasClickHandlers getConsultLink() {
//		return juryNav;
//	}
//
//	@Override
//	public HasClickHandlers getLoginLink() {
//		return loginNav;
//	}

	@Override
	public void setMainPageArticle(String data) {
		//mainPageArticle.setHTML(data);
	}

	
	@Override
	public void setNewsData(ArrayList<String> data) {
//		int i = 0;
//	    latestNewsTable.clear();
//	    if (data == null || data.size() == 0) {
//	      loadingLabel.setText("��� ��������.");
//	      return;
//	    }
//	    loadingLabel.setVisible(false);
//	    
//	    for (final String newsElem : data) {
//	        //CheckBox checkBoxName = new CheckBox(truncateLongName(friend));
//	        //checkBoxName.setValue(true);
////	        final Image propertyButton = new Image(GlobalResources.RESOURCE
////	            .propertyButton());
////	        propertyButton.setStyleName("pointer");
//	    	String [] arr = newsElem.split(":::");
//	    	Label newHeader = new Label(arr[0]);
//	    	Label newContents = new Label(arr[1]);
//	    	
//
//	        latestNewsTable.setWidget(i, 0, newHeader);
//	        latestNewsTable.setWidget(i+1, 1, newContents);
//	        latestNewsTable.getCellFormatter().addStyleName(i, 0,
//	            "friendNameInList");
//	        i+=2;
//	      }
	}
	@Override
	public Widget asWidget()
	{
		return this;
	}

	@Override
	public void setTopResearches(ArrayList<SocioResearchDTO_Light> arr) {
		for(SocioResearchDTO_Light dto:arr)
		{
			research_links.add(new ResearchDescItemView(dto));
		}
		research_links.add(new ResearchDescItemViewAll(new SocioResearchDTO_Light(new Long(0),"")));
	}

	@Override
	public void setTopLaws(ArrayList<ZaconDTO_Light> arr) {
		for(ZaconDTO_Light dto:arr)
		{
			law_links.add(new ZaconDescItemView(dto));
		}
		law_links.add(new ZaconDescItemViewAll(new ZaconDTO_Light(0, "")));
	}

	@Override
	public void setTopPubs(ArrayList<PublicationDTO_Light> arr) {
		for(PublicationDTO_Light dto:arr)
		{
			pub_links.add(new PublicationDescItemView(dto));
		}
		pub_links.add(new PublicationDescItemViewAll(new PublicationDTO_Light(0,"",new Date())));
	}
}
