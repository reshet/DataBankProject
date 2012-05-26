package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.ShowConsultationIndexEvent;
import com.mresearch.databank.client.event.ShowPublicationDetailsEvent;
import com.mresearch.databank.client.event.ShowPublicationIndexEvent;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.TopicDTO;
import com.mresearch.databank.shared.ZaconDTO;

public class JuryTopicItemView extends Composite {

	private static PublicationDescItemViewUiBinder uiBinder = GWT
			.create(PublicationDescItemViewUiBinder.class);

	interface PublicationDescItemViewUiBinder extends
			UiBinder<Widget, JuryTopicItemView> {
	}

	
	
	@UiField Hyperlink pub_link;
	TopicDTO dto;
	public JuryTopicItemView(TopicDTO dto) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		pub_link.setHTML("<span class=\"blue italic\">"+dto.getHeader()+"</span>");
	}
	@UiHandler(value="pub_link")
	public void doGo(ClickEvent ev)
	{
		DatabankApp.get().getEventBus().fireEvent(new ShowConsultationIndexEvent(dto.getId(), dto.getHeader(), dto.getItems()));
	}

}
