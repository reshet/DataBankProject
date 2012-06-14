package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.shared.SocioResearchDTO_Light;

public class ResearchDescItemView extends Composite {

	private static SocioResearchDescItemViewUiBinder uiBinder = GWT
			.create(SocioResearchDescItemViewUiBinder.class);

	interface SocioResearchDescItemViewUiBinder extends
			UiBinder<Widget, ResearchDescItemView> {
	}

	//@UiField Label pub_date;
	@UiField Hyperlink pub_link;
	SocioResearchDTO_Light dto;
	public ResearchDescItemView(SocioResearchDTO_Light dto) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		//String txt = dto.getName().length()>60?dto.getName().substring(0, 60)+"...":dto.getName();
		String txt = dto.getName();
		
		pub_link.setHTML("<span class=\"dark-green\">"+txt+"</span>");
	}
	
	
	
	@UiHandler(value="pub_link")
	public void doGo(ClickEvent ev)
	{
		DatabankApp.get().getEventBus().fireEvent(new ShowResearchDetailsEvent(dto.getId()));
	}

}
