package com.mresearch.databank.client.views;

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
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.ArticleDTO;

public class ArticleDetailedView extends Composite {

	private static ArticleDetailedViewUiBinder uiBinder = GWT
			.create(ArticleDetailedViewUiBinder.class);

	interface ArticleDetailedViewUiBinder extends
			UiBinder<Widget, ArticleDetailedView> {
	}

	@UiField Label _name,_abstract;
	@UiField Hyperlink _enc_link;
	@UiField HorizontalPanel link_panel;
	public ArticleDetailedView(ArticleDTO dto) {
		initWidget(uiBinder.createAndBindUi(this));
		this._name.setText(dto.getHeader());
		this._abstract.setText(dto.getContents());
		//_enc_link.setTargetHistoryToken();
		link_panel.add(new HTML("<a href=\""+"/databank/serve?blob-key="+dto.getEnclosure_key()+"\">Скачать документ (реал)  </a>"));
	}
	@UiHandler(value="_enc_link")
	public void onEncLinkClick(ClickEvent e)
	{
		//_enc_link.set
		Window.alert("Enc loaded!");
	}

}
