package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.ShowPublicationDetailsEvent;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.ZaconDTO;

public class PublicationDescItemView extends Composite {

	private static PublicationDescItemViewUiBinder uiBinder = GWT
			.create(PublicationDescItemViewUiBinder.class);

	interface PublicationDescItemViewUiBinder extends
			UiBinder<Widget, PublicationDescItemView> {
	}

	//@UiField Label pub_date;
	@UiField Hyperlink pub_link;
	PublicationDTO_Light dto;
	public PublicationDescItemView(PublicationDTO_Light dto) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		String pub_date = dto.getDate().getDate()+"."+(dto.getDate().getMonth()+1)+"."+(dto.getDate().getYear()+1900);
		pub_date = "<span class=\"yellow italic\">"+pub_date+"</span>";
		//pub_link.setHTML(dto.getHeader().length()>60?pub_date+" "+dto.getHeader().substring(0, 60):pub_date+" "+dto.getHeader());
		pub_link.setHTML(dto.getHeader());
	}
	
	
	
	@UiHandler(value="pub_link")
	public void doGo(ClickEvent ev)
	{
		DatabankApp.get().getEventBus().fireEvent(new ShowPublicationDetailsEvent(dto.getId()));
	}

}
