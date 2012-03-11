package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.ZaconDTO;

public class ZaconsShortView extends Composite {

	private static ZaconsShortViewUiBinder uiBinder = GWT
			.create(ZaconsShortViewUiBinder.class);

	interface ZaconsShortViewUiBinder extends
			UiBinder<Widget, ZaconsShortView> {
	}

	@UiField VerticalPanel vertPanel;
	public ZaconsShortView(ArrayList<ZaconDTO> dtos) {
		initWidget(uiBinder.createAndBindUi(this));
		for(ZaconDTO dto:dtos)
		{
			vertPanel.add(new ZaconBarView(dto));
		}
	}

}
