package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.views.DBviewers.MultiValuedFieldViewer;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.sun.org.apache.xpath.internal.operations.And;

public class ZaconDetailedView extends Composite {

	private static ZaconDetailedViewUiBinder uiBinder = GWT
			.create(ZaconDetailedViewUiBinder.class);

	interface ZaconDetailedViewUiBinder extends
			UiBinder<Widget, ZaconDetailedView> {
	}

	//@UiField Label _name,_abstract,_number;
	//keywords,authors,date,date_accept,date_decline;
	//@UiField Hyperlink _enc_link;
	@UiField HorizontalPanel link_panel;
	@UiField VerticalPanel elasticDBfields;
	public String arrToStr(ArrayList<String> arr)
	{
		String ans = "";
		for(String str:arr)
		{
			ans+=str+",";
		}
		if (ans.length() > 2)ans = ans.substring(0, ans.length()-2);
		return ans;
	}
	private ZaconDTO dto;
	private MetaUnitMultivaluedEntityDTO db;
	public ZaconDetailedView(ZaconDTO dto,MetaUnitMultivaluedEntityDTO dt) {
		initWidget(uiBinder.createAndBindUi(this));
		//this._name.setText(dto.getHeader());
		//this._abstract.setText(dto.getContents());
		//this._number.setText(dto.getNumber());
		this.dto =  dto;
		this.db = dt;
//		authors.setText(arrToStr(dto.getAuthors()));
//		keywords.setText(arrToStr(dto.getKey_words()));
//		if (dto.getDate() != null)date.setText(dto.getDate().toString());
//		if (dto.getAccept_date() != null)date_accept.setText(dto.getAccept_date().toString());
//		if (dto.getDecline_date() != null)date_decline.setText(dto.getDecline_date().toString());
//		
		String realPath = GWT.getModuleBaseURL();
		
		//_enc_link.setTargetHistoryToken();
		link_panel.add(new HTML("<a href=\""+realPath+"serve?blob-key="+dto.getEnclosure_key()+"\">Скачать документ </a>"));
		renderDBfillers();
		}
		
		private void renderDBfillers()
		{
			elasticDBfields.clear();
			if(dto.getFilling()== null)dto.setFilling(new HashMap<String, String>());
			MultiValuedFieldViewer mv = new MultiValuedFieldViewer(db,dto.getFilling(),"");
			elasticDBfields.add(mv);
		}
	
//	@UiHandler(value="_enc_link")
//	public void onEncLinkClick(ClickEvent e)
//	{
//		//_enc_link.set
//		Window.alert("Enc loaded!");
//	}

}
