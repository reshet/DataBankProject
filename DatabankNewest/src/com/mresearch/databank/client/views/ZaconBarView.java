package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.ZaconDTO;

public class ZaconBarView extends Composite {

	private static ZaconBarViewUiBinder uiBinder = GWT
			.create(ZaconBarViewUiBinder.class);

	interface ZaconBarViewUiBinder extends UiBinder<Widget, ZaconBarView> {
	}

	//@UiField Label contents;
	@UiField DisclosurePanel discPanel;
	//@UiField HTML download;
	public ZaconBarView(ZaconDTO dto) {
		initWidget(uiBinder.createAndBindUi(this));
		discPanel.setAnimationEnabled(true);
		//discPanel.setTitle(dto.getHeader());
		discPanel.setHeader(new Label(dto.getHeader()));
		VerticalPanel content = new VerticalPanel();
		content.add(new Label(dto.getContents()));
		content.add(new HTML("<a href=\""+"/databank/serve?blob-key="+dto.getEnclosure_key()+"\" align=\"right\">Скачать документ...</a>"));
		discPanel.setContent(content);
		//label.setText(dto.getHeader());
		//com.google.gwt.user.client.ui.
	}

}
