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
import com.mresearch.databank.client.event.ShowZaconDetailsEvent;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.ZaconDTO_Light;
import com.mresearch.databank.shared.ZaconDTO;

public class ZaconDescItemView extends Composite {

	private static ZaconDescItemViewUiBinder uiBinder = GWT
			.create(ZaconDescItemViewUiBinder.class);

	interface ZaconDescItemViewUiBinder extends
			UiBinder<Widget, ZaconDescItemView> {
	}

	//@UiField Label pub_date;
	@UiField Hyperlink pub_link;
	ZaconDTO_Light dto;
	public ZaconDescItemView(ZaconDTO_Light dto) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		//String pub_date = dto.getDate().getDate()+"."+(dto.getDate().getMonth()+1)+"."+(dto.getDate().getYear()+1900);
		//pub_date = "<span class=\"yellow italic\">"+pub_date+"</span>";
		//String txt = dto.getHeader().length()>60?dto.getHeader().substring(0, 60)+"...":dto.getHeader();
		String txt = dto.getHeader();
		
		pub_link.setHTML("<span class=\"dark-red\">"+txt+"</span>");
	}
	
	
	
	
	@UiHandler(value="pub_link")
	public void doGo(ClickEvent ev)
	{
		DatabankApp.get().getEventBus().fireEvent(new ShowZaconDetailsEvent(dto.getId()));
	}

}
